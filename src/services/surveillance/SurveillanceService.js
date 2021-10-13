import NativeSurveillance from '../native-libs/surveillance/NativeSurveillance';
import {SystemEventsHandler} from '../../utils/common/system-events-handler/SystemEventsHandler';

const SurveillanceService = () => {
  const nativeService = NativeSurveillance();

  const getAppPermissions = async () => {
    return nativeService.getAppPermissions();
  };

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

  const sendRequest = async ({
    request,
    onDelivered,
    onComplete,
    onCancel,
    onError,
  }) => {
    SystemEventsHandler.onInfo({info: 'SurveillanceService->sendRequest()'});

    return await nativeService.sendRequest({
      request,
      onDelivered,
      onComplete,
      onCancel,
      onError,
    });
  };

  const cancelRequest = async ({requestId}) => {
    SystemEventsHandler.onInfo({
      info: 'SurveillanceService->cancelRequest(): ' + requestId,
    });

    return await nativeService.cancelRequest({requestId});
  };

  // ===
  const sendTestNotification = async () => {
    return await nativeService.sendTestNotification();
  };

  const testMotionSensor = async () => {
    return await nativeService.testMotionSensor();
  };

  const testCameraMotionDetection = async () => {
    return await nativeService.testCameraMotionDetection();
  };

  const testStartCameraPreview = async () => {
    return await nativeService.testStartCameraPreview();
  };

  const testStopCameraPreview = async () => {
    return await nativeService.testStopCameraPreview();
  };

  const testTakeCameraPreviewPicture = async () => {
    return await nativeService.testTakeCameraPreviewPicture();
  };
  // ===

  return {
    requests: nativeService.requests,
    responses: nativeService.responses,
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
    testCameraMotionDetection,
    nativeService,
    testStartCameraPreview,
    testStopCameraPreview,
    testTakeCameraPreviewPicture,
    // ===
  };
};

export default SurveillanceService;
