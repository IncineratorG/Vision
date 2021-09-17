import NativeSurveillanceConstants from '../constants/NativeSurveillanceConstants';

const NativeSurveillanceRequests = () => {
  const {
    requestTypes: {TEST_REQUEST_WITH_PAYLOAD},
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

  return {
    testRequestWithPayload,
  };
};

export default NativeSurveillanceRequests();
