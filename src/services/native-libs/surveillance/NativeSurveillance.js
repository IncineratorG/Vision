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
    NativeSurveillanceEvents.types.REQUEST_DELIVERED,
    (data) => {
      SystemEventsHandler.onInfo({
        info: 'NativeSurveillance->onRequestDelivered()',
      });
      const {requestId} =
        NativeSurveillanceEvents.payloads.requestDeliveredEventPayload(data);

      if (requestCallbacksMap.has(requestId)) {
        const {onDelivered, onComplete, onCancel, onError} =
          requestCallbacksMap.get(requestId);

        if (onDelivered) {
          onDelivered();
        }
      }
    },
  );

  nativeServiceEventEmitter.addListener(
    NativeSurveillanceEvents.types.RESPONSE_RECEIVED,
    (data) => {
      SystemEventsHandler.onInfo({
        info: 'NativeSurveillance->onResponseReceived()',
      });
      const {requestId, payload} =
        NativeSurveillanceEvents.payloads.responseReceivedEventPayload(data);

      if (requestCallbacksMap.has(requestId)) {
        const {onDelivered, onComplete, onCancel, onError} =
          requestCallbacksMap.get(requestId);

        if (onComplete) {
          onComplete(payload);
        }

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
        const {onDelivered, onComplete, onCancel, onError} =
          requestCallbacksMap.get(requestId);

        if (onError) {
          onError({code, message});
        }

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

  const getAppPermissions = async () => {
    const action = NativeSurveillanceActions.getAppPermissionsAction();
    return await nativeService.execute(action);
  };

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

  const sendRequest = async ({
    request,
    onDelivered,
    onComplete,
    onCancel,
    onError,
  }) => {
    const action = NativeSurveillanceActions.sendRequest(request);
    const requestId = await nativeService.execute(action);
    requestCallbacksMap.set(requestId, {
      onDelivered,
      onComplete,
      onCancel,
      onError,
    });
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
        const {onDelivered, onComplete, onCancel, onError} =
          requestCallbacksMap.get(requestId);

        if (onCancel) {
          onCancel();
        }
      }
    }
    return successful;
  };

  // ===
  const sendTestNotification = async () => {
    const action = NativeSurveillanceActions.sendTestNotification();
    return await nativeService.execute(action);
  };

  const testMotionSensor = async () => {
    const action = NativeSurveillanceActions.testMotionSensor();
    return await nativeService.execute(action);
  };
  // ===

  return {
    requests,
    responses,
    getAppPermissions,
    isServiceRunning,
    startService,
    stopService,
    testRequest,
    getDevicesInGroup,
    sendRequest,
    cancelRequest,
    // ===
    sendTestNotification,
    testMotionSensor,
    // ===
  };
};

export default NativeSurveillance;
