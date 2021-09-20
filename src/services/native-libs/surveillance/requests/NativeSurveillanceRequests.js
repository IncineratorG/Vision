import NativeSurveillanceConstants from '../constants/NativeSurveillanceConstants';

const NativeSurveillanceRequests = () => {
  const {
    requestTypes: {TEST_REQUEST_WITH_PAYLOAD, IS_DEVICE_ALIVE},
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

  const isDeviceAlive = ({receiverDeviceName}) => {
    return {
      requestType: IS_DEVICE_ALIVE,
      receiverDeviceName,
    };
  };

  return {
    testRequestWithPayload,
    isDeviceAlive,
  };
};

export default NativeSurveillanceRequests();
