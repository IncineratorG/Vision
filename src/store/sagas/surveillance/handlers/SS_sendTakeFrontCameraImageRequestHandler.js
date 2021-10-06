import {call, put, select} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../actions/AppActions';
import Services from '../../../../services/Services';

const SS_sendTakeFrontCameraImageRequestHandler = ({channel}) => {
  const actionsChannel = channel;

  const handler = function* (action) {
    const {receiverDeviceName} = action.payload;

    const frontCameraImageQuality = yield select(
      (state) => state.appSettings.surveillance.frontCameraImage.quality,
    );

    SystemEventsHandler.onInfo({
      info: 'SS_sendTakeFrontCameraImageRequestHandler: ' + receiverDeviceName,
    });

    yield put(
      AppActions.surveillanceTakeFrontCameraImageRequest.actions.sendTakeFrontCameraImageRequestBegin(),
    );

    try {
      const surveillanceService = Services.services().surveillanceService;

      const request = surveillanceService.requests.takeFrontCameraImage({
        receiverDeviceName,
        imageQuality: frontCameraImageQuality,
      });

      const onComplete = (data) => {
        const {image} =
          surveillanceService.responses.takeFrontCameraImage(data);

        actionsChannel.put(
          AppActions.surveillanceTakeFrontCameraImageRequest.actions.sendTakeFrontCameraImageRequestCompleted(
            {image},
          ),
        );
      };

      const onCancel = () => {
        actionsChannel.put(
          AppActions.surveillanceTakeFrontCameraImageRequest.actions.sendTakeFrontCameraImageRequestCancelled(),
        );
      };

      const onError = ({code, message}) => {
        actionsChannel.put(
          AppActions.surveillanceTakeFrontCameraImageRequest.actions.sendTakeFrontCameraImageRequestError(
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
        AppActions.surveillanceTakeFrontCameraImageRequest.actions.sendTakeFrontCameraImageRequestSended(
          {requestId},
        ),
      );
    } catch (e) {
      SystemEventsHandler.onError({
        err:
          'SS_sendTakeFrontCameraImageRequestHandler()->ERROR: ' + e.toString(),
      });

      const {code, message} = e;

      yield put(
        AppActions.surveillanceTakeFrontCameraImageRequest.actions.sendTakeFrontCameraImageRequestError(
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

export default SS_sendTakeFrontCameraImageRequestHandler;
