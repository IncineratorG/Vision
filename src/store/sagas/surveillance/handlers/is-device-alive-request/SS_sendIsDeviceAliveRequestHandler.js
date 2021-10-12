import {SystemEventsHandler} from '../../../../../utils/common/system-events-handler/SystemEventsHandler';
import {call, put} from '@redux-saga/core/effects';
import AppActions from '../../../../actions/AppActions';
import Services from '../../../../../services/Services';

const SS_sendIsDeviceAliveRequestHandler = ({channel}) => {
  const actionsChannel = channel;

  const handler = function* (action) {
    const {receiverDeviceName} = action.payload;

    SystemEventsHandler.onInfo({
      info: 'SS_sendIsDeviceAliveRequestHandler: ' + receiverDeviceName,
    });

    yield put(
      AppActions.surveillanceIsDeviceAliveRequest.actions.sendIsAliveRequestBegin(),
    );

    try {
      const surveillanceService = Services.services().surveillanceService;

      const request = surveillanceService.requests.isDeviceAlive({
        receiverDeviceName,
      });

      const onComplete = (data) => {
        const {isAlive} = surveillanceService.responses.isDeviceAlive(data);

        actionsChannel.put(
          AppActions.surveillanceIsDeviceAliveRequest.actions.sendIsAliveRequestCompleted(
            {isAlive},
          ),
        );
      };

      const onCancel = () => {
        actionsChannel.put(
          AppActions.surveillanceIsDeviceAliveRequest.actions.sendIsAliveRequestCancelled(),
        );
      };

      const onError = ({code, message}) => {
        actionsChannel.put(
          AppActions.surveillanceIsDeviceAliveRequest.actions.sendIsAliveRequestError(
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
        AppActions.surveillanceIsDeviceAliveRequest.actions.sendIsAliveRequestSended(
          {requestId},
        ),
      );
    } catch (e) {
      SystemEventsHandler.onError({
        err: 'SS_sendIsDeviceAliveRequestHandler()->ERROR: ' + e.toString(),
      });

      const {code, message} = e;

      yield put(
        AppActions.surveillanceIsDeviceAliveRequest.actions.sendIsAliveRequestError(
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

export default SS_sendIsDeviceAliveRequestHandler;
