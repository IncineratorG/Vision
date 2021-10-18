import NativeSurveillanceLib from '../lib/NativeSurveillanceLib';

const NativeSurveillanceConstants = () => {
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
    requestTypes: {
      TEST_REQUEST_WITH_PAYLOAD,
      GET_DEVICE_AVAILABLE_ACTIONS,
      IS_DEVICE_ALIVE,
      TAKE_BACK_CAMERA_IMAGE,
      TAKE_FRONT_CAMERA_IMAGE,
      TOGGLE_DETECT_DEVICE_MOVEMENT,
      TOGGLE_RECOGNIZE_PERSON,
    },
    eventTypes: {REQUEST_DELIVERED, REQUEST_ERROR, RESPONSE_RECEIVED},
  } = NativeSurveillanceLib.getConstants();

  return {
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
    requestTypes: {
      TEST_REQUEST_WITH_PAYLOAD,
      GET_DEVICE_AVAILABLE_ACTIONS,
      IS_DEVICE_ALIVE,
      TAKE_BACK_CAMERA_IMAGE,
      TAKE_FRONT_CAMERA_IMAGE,
      TOGGLE_DETECT_DEVICE_MOVEMENT,
      TOGGLE_RECOGNIZE_PERSON,
    },
    eventTypes: {REQUEST_DELIVERED, REQUEST_ERROR, RESPONSE_RECEIVED},
  };
};

export default NativeSurveillanceConstants();
