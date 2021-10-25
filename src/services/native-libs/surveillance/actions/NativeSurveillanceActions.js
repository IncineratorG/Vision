import NativeSurveillanceConstants from '../constants/NativeSurveillanceConstants';

const NativeSurveillanceActions = () => {
  const {
    actionTypes: {
      GET_APP_PERMISSIONS,
      IS_RUNNING,
      START_SERVICE,
      STOP_SERVICE,
      TEST_REQUEST,
      GET_DEVICES_IN_GROUP,
      SEND_REQUEST,
      CANCEL_REQUEST,
      // ===
      SEND_TEST_NOTIFICATION,
      TEST_MOTION_SENSOR,
      TEST_CAMERA_MOTION_DETECTION,
      TEST_START_CAMERA_PREVIEW,
      TEST_STOP_CAMERA_PREVIEW,
      TEST_TAKE_CAMERA_PREVIEW_PICTURE,

      TEST_START_BACK_CAMERA,
      TEST_START_FRONT_CAMERA,
      TEST_STOP_BACK_CAMERA,
      TEST_STOP_FRONT_CAMERA,
      TEST_TAKE_BACK_CAMERA_PICTURE,
      TEST_TAKE_FRONT_CAMERA_PICTURE,
      // ===
    },
  } = NativeSurveillanceConstants;

  const getAppPermissionsAction = () => {
    return {
      type: GET_APP_PERMISSIONS,
    };
  };

  const isRunningAction = () => {
    return {
      type: IS_RUNNING,
    };
  };

  const startServiceAction = () => {
    return {
      type: START_SERVICE,
    };
  };

  const stopServiceAction = () => {
    return {
      type: STOP_SERVICE,
    };
  };

  const testRequestAction = () => {
    return {
      type: TEST_REQUEST,
    };
  };

  const getDevicesInGroupAction = ({groupName, groupPassword, deviceName}) => {
    return {
      type: GET_DEVICES_IN_GROUP,
      payload: {groupName, groupPassword, deviceName},
    };
  };

  const sendRequest = ({receiverDeviceName, requestType, requestPayload}) => {
    return {
      type: SEND_REQUEST,
      payload: {receiverDeviceName, requestType, requestPayload},
    };
  };

  const cancelRequestAction = ({requestId}) => {
    return {
      type: CANCEL_REQUEST,
      payload: {requestId},
    };
  };

  // ===
  const sendTestNotification = () => {
    return {
      type: SEND_TEST_NOTIFICATION,
    };
  };

  const testMotionSensor = () => {
    return {
      type: TEST_MOTION_SENSOR,
    };
  };

  const testCameraMotionDetection = () => {
    return {
      type: TEST_CAMERA_MOTION_DETECTION,
    };
  };

  const testStartCameraPreviewAction = () => {
    return {
      type: TEST_START_CAMERA_PREVIEW,
    };
  };

  const testStopCameraPreviewAction = () => {
    return {
      type: TEST_STOP_CAMERA_PREVIEW,
    };
  };

  const testTakeCameraPreviewPictureAction = () => {
    return {
      type: TEST_TAKE_CAMERA_PREVIEW_PICTURE,
    };
  };

  const startBackCameraAction = () => {
    return {
      type: TEST_START_BACK_CAMERA,
    };
  };

  const startFrontCameraAction = () => {
    return {
      type: TEST_START_FRONT_CAMERA,
    };
  };

  const stopBackCameraAction = () => {
    return {
      type: TEST_STOP_BACK_CAMERA,
    };
  };

  const stopFrontCameraAction = () => {
    return {
      type: TEST_STOP_FRONT_CAMERA,
    };
  };

  const takeBackCameraPictureAction = () => {
    return {
      type: TEST_TAKE_BACK_CAMERA_PICTURE,
    };
  };

  const takeFrontCameraPictureAction = () => {
    return {
      type: TEST_TAKE_FRONT_CAMERA_PICTURE,
    };
  };
  // ===

  return {
    getAppPermissionsAction,
    isRunningAction,
    startServiceAction,
    stopServiceAction,
    testRequestAction,
    getDevicesInGroupAction,
    sendRequest,
    cancelRequestAction,
    // ===
    sendTestNotification,
    testMotionSensor,
    testCameraMotionDetection,
    testStartCameraPreviewAction,
    testStopCameraPreviewAction,
    testTakeCameraPreviewPictureAction,

    startBackCameraAction,
    startFrontCameraAction,
    stopBackCameraAction,
    stopFrontCameraAction,
    takeBackCameraPictureAction,
    takeFrontCameraPictureAction,
    // ===
  };
};

export default NativeSurveillanceActions();
