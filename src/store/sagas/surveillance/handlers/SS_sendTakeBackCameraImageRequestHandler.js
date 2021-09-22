import {call, put} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../actions/AppActions';
import Services from '../../../../services/Services';

const SS_sendTakeBackCameraImageRequestHandler = ({channel}) => {
  const actionsChannel = channel;

  const handler = function* (action) {
    const {receiverDeviceName} = action.payload;

    SystemEventsHandler.onInfo({
      info: 'SS_sendTakeBackCameraImageRequestHandler: ' + receiverDeviceName,
    });

    yield put(
      AppActions.surveillanceTakeBackCameraImageRequest.actions.sendTakeBackCameraImageRequestBegin(),
    );

    try {
      const surveillanceService = Services.services().surveillanceService;

      const request = surveillanceService.requests.takeBackCameraImage({
        receiverDeviceName,
      });

      const onComplete = (data) => {
        const {image} = surveillanceService.responses.takeBackCameraImage(data);

        actionsChannel.put(
          AppActions.surveillanceTakeBackCameraImageRequest.actions.sendTakeBackCameraImageRequestCompleted(
            {image},
          ),
        );
      };

      const onCancel = () => {
        actionsChannel.put(
          AppActions.surveillanceTakeBackCameraImageRequest.actions.sendTakeBackCameraImageRequestCancelled(),
        );
      };

      const onError = ({code, message}) => {
        actionsChannel.put(
          AppActions.surveillanceTakeBackCameraImageRequest.actions.sendTakeBackCameraImageRequestError(
            {code, message},
          ),
        );
      };

      const requestId = yield call(surveillanceService.sendRequest, {
        request,
        onComplete,
        onCancel,
        onError,
      });

      yield put(
        AppActions.surveillanceTakeBackCameraImageRequest.actions.sendTakeBackCameraImageRequestSended(
          {requestId},
        ),
      );
    } catch (e) {
      SystemEventsHandler.onError({
        err:
          'SS_sendTakeBackCameraImageRequestHandler()->ERROR: ' + e.toString(),
      });

      const {code, message} = e;

      yield put(
        AppActions.surveillanceTakeBackCameraImageRequest.actions.sendTakeBackCameraImageRequestError(
          {
            code,
            message,
          },
        ),
      );
    }
  };

  return {
    handler,
  };
};

export default SS_sendTakeBackCameraImageRequestHandler;
