import NativeSurveillanceLib from '../lib/NativeSurveillanceLib';

const NativeSurveillanceConstants = () => {
  const {
    actionTypes: {
      IS_RUNNING,
      START_SERVICE,
      STOP_SERVICE,
      TEST_REQUEST,
      GET_DEVICES_IN_GROUP,
      SEND_REQUEST,
    },
    requestTypes: {TEST_REQUEST_WITH_PAYLOAD, GET_DEVICE_AVAILABLE_ACTIONS},
    eventTypes: {REQUEST_ERROR, RESPONSE_RECEIVED},
  } = NativeSurveillanceLib.getConstants();

  return {
    actionTypes: {
      IS_RUNNING,
      START_SERVICE,
      STOP_SERVICE,
      TEST_REQUEST,
      GET_DEVICES_IN_GROUP,
      SEND_REQUEST,
    },
    requestTypes: {
      TEST_REQUEST_WITH_PAYLOAD,
      GET_DEVICE_AVAILABLE_ACTIONS,
    },
    eventTypes: {REQUEST_ERROR, RESPONSE_RECEIVED},
  };
};

export default NativeSurveillanceConstants();
