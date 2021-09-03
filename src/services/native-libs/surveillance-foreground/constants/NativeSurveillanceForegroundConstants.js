import NativeSurveillanceLib from '../lib/NativeSurveillanceForegroundLib';

const NativeSurveillanceForegroundConstants = () => {
  const {
    actionTypes: {IS_RUNNING, START_SERVICE, STOP_SERVICE},
  } = NativeSurveillanceLib.getConstants();

  return {
    actionTypes: {
      IS_RUNNING,
      START_SERVICE,
      STOP_SERVICE,
    },
  };
};

export default NativeSurveillanceForegroundConstants();
