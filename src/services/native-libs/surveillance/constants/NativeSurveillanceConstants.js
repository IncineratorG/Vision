import NativeSurveillanceLib from '../lib/NativeSurveillanceLib';

const NativeSurveillanceConstants = () => {
  const {
    actionTypes: {
      IS_RUNNING,
      START_SERVICE,
      STOP_SERVICE,
      TEST_REQUEST,
      GET_DEVICES_IN_GROUP,
    },
  } = NativeSurveillanceLib.getConstants();

  return {
    actionTypes: {
      IS_RUNNING,
      START_SERVICE,
      STOP_SERVICE,
      TEST_REQUEST,
      GET_DEVICES_IN_GROUP,
    },
  };
};

export default NativeSurveillanceConstants();
