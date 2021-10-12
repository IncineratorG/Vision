import {SystemEventsHandler} from '../../../../../utils/common/system-events-handler/SystemEventsHandler';
import {call, put} from '@redux-saga/core/effects';
import AppActions from '../../../../actions/AppActions';
import Services from '../../../../../services/Services';

const SS_sendToggleDetectDeviceMovementRequestHandler = ({channel}) => {
  const actionsChannel = channel;

  const handler = function* (action) {
    const {receiverDeviceName} = action.payload;

    SystemEventsHandler.onInfo({
      info:
        'SS_sendToggleDetectDeviceMovementRequestHandler: ' +
        receiverDeviceName,
    });

    yield put(
      AppActions.surveillanceToggleDetectDeviceMovementRequest.actions.sendToggleDetectDeviceMovementRequestBegin(),
    );

    try {
      const surveillanceService = Services.services().surveillanceService;

      const request = surveillanceService.requests.toggleDetectDeviceMovement({
        receiverDeviceName,
      });

      const onComplete = (data) => {
        const {detectDeviceMovementServiceRunning} =
          surveillanceService.responses.toggleDetectDeviceMovement(data);

        actionsChannel.put(
          AppActions.surveillanceToggleDetectDeviceMovementRequest.actions.sendToggleDetectDeviceMovementRequestCompleted(
            {detectDeviceMovementServiceRunning},
          ),
        );
      };

      const onCancel = () => {
        actionsChannel.put(
          AppActions.surveillanceToggleDetectDeviceMovementRequest.actions.sendToggleDetectDeviceMovementRequestCancelled(),
        );
      };

      const onError = ({code, message}) => {
        actionsChannel.put(
          AppActions.surveillanceToggleDetectDeviceMovementRequest.actions.sendToggleDetectDeviceMovementRequestError(
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
        AppActions.surveillanceToggleDetectDeviceMovementRequest.actions.sendToggleDetectDeviceMovementRequestSended(
          {requestId},
        ),
      );
    } catch (e) {
      SystemEventsHandler.onError({
        err:
          'SS_sendToggleDetectDeviceMovementRequestHandler()->ERROR: ' +
          e.toString(),
      });

      const {code, message} = e;

      yield put(
        AppActions.surveillanceToggleDetectDeviceMovementRequest.actions.sendToggleDetectDeviceMovementRequestError(
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

export default SS_sendToggleDetectDeviceMovementRequestHandler;
