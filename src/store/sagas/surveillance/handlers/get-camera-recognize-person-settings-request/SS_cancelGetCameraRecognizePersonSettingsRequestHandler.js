import {call, select} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../../../utils/common/system-events-handler/SystemEventsHandler';
import Services from '../../../../../services/Services';

const SS_cancelGetCameraRecognizePersonSettingsRequestHandler = ({channel}) => {
  const handler = function* (action) {
    const {requestId, inProgress} = yield select(
      (state) =>
        state.surveillanceGetCameraRecognizePersonSettingsRequest
          .getCameraRecognizePersonSettingsRequest,
    );

    try {
      if (inProgress && requestId) {
        const surveillanceService = Services.services().surveillanceService;
        yield call(surveillanceService.cancelRequest, {requestId});
      }

      // yield put(
      //   AppActions.surveillanceToggleDetectDeviceMovementRequest.actions.sendToggleDetectDeviceMovementRequestCancelled(),
      // );
    } catch (e) {
      SystemEventsHandler.onError({
        err:
          'SS_cancelGetCameraRecognizePersonSettingsRequestHandler->handler()->ERROR: ' +
          e.toString(),
      });
    }
  };

  return {
    handler,
  };
};

export default SS_cancelGetCameraRecognizePersonSettingsRequestHandler;
