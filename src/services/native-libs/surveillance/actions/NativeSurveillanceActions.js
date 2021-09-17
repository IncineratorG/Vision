import NativeSurveillanceConstants from '../constants/NativeSurveillanceConstants';

const NativeSurveillanceActions = () => {
  const {
    actionTypes: {
      IS_RUNNING,
      START_SERVICE,
      STOP_SERVICE,
      TEST_REQUEST,
      GET_DEVICES_IN_GROUP,
      SEND_REQUEST,
    },
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

  return {
    isRunningAction,
    startServiceAction,
    stopServiceAction,
    testRequestAction,
    getDevicesInGroupAction,
    sendRequest,
  };
};

export default NativeSurveillanceActions();
