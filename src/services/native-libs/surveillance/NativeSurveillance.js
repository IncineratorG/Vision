import NativeSurveillanceLib from './lib/NativeSurveillanceLib';
import NativeSurveillanceActions from './actions/NativeSurveillanceActions';
import NativeSurveillanceEvents from './events/NativeSurveillanceEvents';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import NativeSurveillanceRequests from './requests/NativeSurveillanceRequests';
import NativeSurveillanceResponses from './responses/NativeSurveillanceResponses';

const NativeSurveillance = () => {
  const nativeService = NativeSurveillanceLib;
  const nativeServiceEventEmitter = NativeSurveillanceEvents.eventEmitter;

  const requests = NativeSurveillanceRequests;
  const responses = NativeSurveillanceResponses;

  const requestCallbacksMap = new Map();

  nativeServiceEventEmitter.addListener(
    NativeSurveillanceEvents.types.RESPONSE_RECEIVED,
    (data) => {
      SystemEventsHandler.onInfo({
        info: 'NativeSurveillance->onResponseReceived()',
      });
      const {requestId, payload} =
        NativeSurveillanceEvents.payloads.responseReceivedEventPayload(data);

      if (requestCallbacksMap.has(requestId)) {
        const {onComplete, onCancel, onError} =
          requestCallbacksMap.get(requestId);

        onComplete(payload);

        requestCallbacksMap.delete(requestId);
      } else {
        SystemEventsHandler.onInfo({
          info:
            'NativeSurveillance->onResponseReceived->NO_SUCH_REQUEST_CALLBACKS: ' +
            JSON.stringify(data),
        });
      }
    },
  );

  nativeServiceEventEmitter.addListener(
    NativeSurveillanceEvents.types.REQUEST_ERROR,
    (data) => {
      SystemEventsHandler.onInfo({
        info: 'NativeSurveillance->onError: ' + JSON.stringify(data),
      });
      const {requestId, code, message} =
        NativeSurveillanceEvents.payloads.requestErrorEventPayload(data);

      if (requestCallbacksMap.has(requestId)) {
        const {onComplete, onCancel, onError} =
          requestCallbacksMap.get(requestId);

        onError({code, message});

        requestCallbacksMap.delete(requestId);
      } else {
        SystemEventsHandler.onInfo({
          info:
            'NativeSurveillance->onError->NO_SUCH_REQUEST_CALLBACKS: ' +
            JSON.stringify(data),
        });
      }
    },
  );

  const isServiceRunning = async () => {
    const action = NativeSurveillanceActions.isRunningAction();
    return await nativeService.execute(action);
  };

  const startService = async () => {
    const action = NativeSurveillanceActions.startServiceAction();
    return await nativeService.execute(action);
  };

  const stopService = async () => {
    const action = NativeSurveillanceActions.stopServiceAction();
    return await nativeService.execute(action);
  };

  const testRequest = async () => {
    const action = NativeSurveillanceActions.testRequestAction();
    return await nativeService.execute(action);
  };

  const getDevicesInGroup = async ({groupName, groupPassword, deviceName}) => {
    const action = NativeSurveillanceActions.getDevicesInGroupAction({
      groupName,
      groupPassword,
      deviceName,
    });
    return await nativeService.execute(action);
  };

  const sendRequest = async ({request, onComplete, onCancel, onError}) => {
    const action = NativeSurveillanceActions.sendRequest(request);
    const requestId = await nativeService.execute(action);
    requestCallbacksMap.set(requestId, {onComplete, onCancel, onError});
    return requestId;
  };

  const cancelRequest = async ({requestId}) => {
    SystemEventsHandler.onInfo({
      info: 'NativeSurveillance->cancelRequest(): ' + requestId,
    });

    const action = NativeSurveillanceActions.cancelRequestAction({requestId});
    const {successful} = await nativeService.execute(action);
    if (successful) {
      if (requestCallbacksMap.has(requestId)) {
        const {onComplete, onCancel, onError} =
          requestCallbacksMap.get(requestId);
        onCancel();
      }
    }
    return successful;
  };

  return {
    requests,
    responses,
    isServiceRunning,
    startService,
    stopService,
    testRequest,
    getDevicesInGroup,
    sendRequest,
    cancelRequest,
  };
};

export default NativeSurveillance;
