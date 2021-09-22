import NativeSurveillanceConstants from '../constants/NativeSurveillanceConstants';

const NativeSurveillanceRequests = () => {
  const {
    requestTypes: {
      TEST_REQUEST_WITH_PAYLOAD,
      IS_DEVICE_ALIVE,
      TAKE_BACK_CAMERA_IMAGE,
    },
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

  const takeBackCameraImage = ({receiverDeviceName}) => {
    return {
      requestType: TAKE_BACK_CAMERA_IMAGE,
      receiverDeviceName,
    };
  };

  return {
    testRequestWithPayload,
    isDeviceAlive,
    takeBackCameraImage,
  };
};

export default NativeSurveillanceRequests();
