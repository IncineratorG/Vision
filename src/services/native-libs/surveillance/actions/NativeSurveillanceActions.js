import NativeSurveillanceConstants from '../constants/NativeSurveillanceConstants';

const NativeSurveillanceActions = () => {
  const {
    actionTypes: {IS_RUNNING, START_SERVICE, STOP_SERVICE, TEST_REQUEST},
  } = NativeSurveillanceConstants;

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

  return {
    isRunningAction,
    startServiceAction,
    stopServiceAction,
    testRequestAction,
  };
};

export default NativeSurveillanceActions();
