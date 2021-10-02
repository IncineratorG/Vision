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
      // ===
    },
    requestTypes: {
      TEST_REQUEST_WITH_PAYLOAD,
      GET_DEVICE_AVAILABLE_ACTIONS,
      IS_DEVICE_ALIVE,
      TAKE_BACK_CAMERA_IMAGE,
      TAKE_FRONT_CAMERA_IMAGE,
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
      // ===
    },
    requestTypes: {
      TEST_REQUEST_WITH_PAYLOAD,
      GET_DEVICE_AVAILABLE_ACTIONS,
      IS_DEVICE_ALIVE,
      TAKE_BACK_CAMERA_IMAGE,
      TAKE_FRONT_CAMERA_IMAGE,
    },
    eventTypes: {REQUEST_DELIVERED, REQUEST_ERROR, RESPONSE_RECEIVED},
  };
};

export default NativeSurveillanceConstants();
