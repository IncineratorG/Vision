import NativeSurveillanceLib from '../lib/NativeSurveillanceLib';

const NativeSurveillanceConstants = () => {
  const {
    actionTypes: {IS_RUNNING, START_SERVICE, STOP_SERVICE, TEST_REQUEST},
  } = NativeSurveillanceLib.getConstants();

  return {
    actionTypes: {
      IS_RUNNING,
      START_SERVICE,
      STOP_SERVICE,
      TEST_REQUEST,
    },
  };
};

export default NativeSurveillanceConstants();
