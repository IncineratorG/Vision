import NativeSurveillanceForegroundConstants from '../constants/NativeSurveillanceForegroundConstants';

const NativeSurveillanceForegroundActions = () => {
  const {
    actionTypes: {IS_RUNNING, START_SERVICE, STOP_SERVICE},
  } = NativeSurveillanceForegroundConstants;

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

  return {
    isRunningAction,
    startServiceAction,
    stopServiceAction,
  };
};

export default NativeSurveillanceForegroundActions();
