import NativeSurveillance from '../native-libs/surveillance/NativeSurveillance';
import {SystemEventsHandler} from '../../utils/common/system-events-handler/SystemEventsHandler';

const SurveillanceService = () => {
  const nativeService = NativeSurveillance();

  const isServiceRunning = async () => {
    return nativeService.isServiceRunning();
  };

  const startService = async () => {
    return await nativeService.startService();
  };

  const stopService = async () => {
    return await nativeService.stopService();
  };

  const testRequest = async () => {
    return await nativeService.testRequest();
  };

  const getDevicesInGroup = async ({groupName, groupPassword, deviceName}) => {
    return await nativeService.getDevicesInGroup({
      groupName,
      groupPassword,
      deviceName,
    });
  };

  // ===
  const map = new Map();

  const sendRequest = async ({
    request,
    // receiverDeviceName,
    // requestType,
    // requestPayload,
    onComplete,
    onCancel,
    onError,
  }) => {
    SystemEventsHandler.onInfo({info: 'SurveillanceService->sendRequest()'});

    const result = await nativeService.sendRequest(request);

    SystemEventsHandler.onInfo({
      info:
        'SurveillanceService->sendRequest()->RESULT: ' + JSON.stringify(result),
    });

    // const requestId = Date.now().toString();
    //
    // map.set(requestId, {
    //   request,
    //   onComplete,
    //   onCancel,
    //   onError,
    // });
    //
    // return requestId;
  };

  const cancelRequest = async ({requestId}) => {
    SystemEventsHandler.onInfo({
      info: 'SurveillanceService->cancelRequest(): ' + requestId,
    });

    // ===
    // nativeService.cancelRequest({requestId});
    // ===

    // const {request, onComplete, onCancel, onError} = map.get(requestId);
    // if (onCancel) {
    //   onCancel();
    // }
    // map.delete(requestId);
  };
  // ===

  return {
    isServiceRunning,
    startService,
    stopService,
    testRequest,
    getDevicesInGroup,
    // ===
    sendRequest,
    cancelRequest,
    // ===
  };
};

export default SurveillanceService;
