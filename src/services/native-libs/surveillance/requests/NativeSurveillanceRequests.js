import NativeSurveillanceConstants from '../constants/NativeSurveillanceConstants';

const NativeSurveillanceRequests = () => {
  const {
    requestTypes: {TEST_REQUEST_WITH_PAYLOAD, GET_DEVICE_AVAILABLE_ACTIONS},
  } = NativeSurveillanceConstants;

  const testRequestWithPayload = ({receiverDeviceName, valueOne, valueTwo}) => {
    return {
      requestType: TEST_REQUEST_WITH_PAYLOAD,
      receiverDeviceName,
      requestPayload: {
        valueOne,
        valueTwo,
      },
    };
  };

  const getDeviceAvailableActions = ({receiverDeviceName}) => {
    return {
      requestType: GET_DEVICE_AVAILABLE_ACTIONS,
      receiverDeviceName,
    };
  };

  return {
    testRequestWithPayload,
    getDeviceAvailableActions,
  };
};

export default NativeSurveillanceRequests();
