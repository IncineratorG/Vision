import {SystemEventsHandler} from '../../../../../utils/common/system-events-handler/SystemEventsHandler';
import {call, put} from '@redux-saga/core/effects';
import AppActions from '../../../../actions/AppActions';
import Services from '../../../../../services/Services';

const SS_sendToggleRecognizePersonRequestHandler = ({channel}) => {
  const actionsChannel = channel;

  const handler = function* (action) {
    const {receiverDeviceName, cameraType} = action.payload;

    SystemEventsHandler.onInfo({
      info: 'SS_sendToggleRecognizePersonRequestHandler: ' + receiverDeviceName,
    });

    yield put(
      AppActions.surveillanceToggleRecognizePersonRequest.actions.sendToggleRecognizePersonRequestBegin(),
    );

    try {
      const surveillanceService = Services.services().surveillanceService;

      const request = surveillanceService.requests.toggleRecognizePerson({
        receiverDeviceName,
        cameraType,
      });

      const onComplete = (data) => {
        const {
          frontCameraRecognizePersonServiceRunning,
          backCameraRecognizePersonServiceRunning,
        } = surveillanceService.responses.toggleRecognizePerson(data);

        actionsChannel.put(
          AppActions.surveillanceToggleRecognizePersonRequest.actions.sendToggleRecognizePersonRequestCompleted(
            {
              frontCameraRecognizePersonServiceRunning,
              backCameraRecognizePersonServiceRunning,
            },
          ),
        );
      };

      const onCancel = () => {
        actionsChannel.put(
          AppActions.surveillanceToggleRecognizePersonRequest.actions.sendToggleRecognizePersonRequestCancelled(),
        );
      };

      const onError = ({code, message}) => {
        actionsChannel.put(
          AppActions.surveillanceToggleRecognizePersonRequest.actions.sendToggleRecognizePersonRequestError(
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
        AppActions.surveillanceToggleRecognizePersonRequest.actions.sendToggleRecognizePersonRequestSended(
          {requestId},
        ),
      );
    } catch (e) {
      SystemEventsHandler.onError({
        err:
          'SS_sendToggleRecognizePersonRequestHandler()->ERROR: ' +
          e.toString(),
      });

      const {code, message} = e;

      yield put(
        AppActions.surveillanceToggleRecognizePersonRequest.actions.sendToggleRecognizePersonRequestError(
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

export default SS_sendToggleRecognizePersonRequestHandler;
