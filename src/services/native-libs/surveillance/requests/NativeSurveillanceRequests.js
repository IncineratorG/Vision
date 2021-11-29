import NativeSurveillanceConstants from '../constants/NativeSurveillanceConstants';

const NativeSurveillanceRequests = () => {
  const {
    requestTypes: {
      TEST_REQUEST_WITH_PAYLOAD,
      IS_DEVICE_ALIVE,
      TAKE_BACK_CAMERA_IMAGE,
      TAKE_FRONT_CAMERA_IMAGE,
      TOGGLE_DETECT_DEVICE_MOVEMENT,
      TOGGLE_RECOGNIZE_PERSON,
      GET_CAMERA_RECOGNIZE_PERSON_SETTINGS,
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

  const takeBackCameraImage = ({receiverDeviceName, imageQuality}) => {
    return {
      requestType: TAKE_BACK_CAMERA_IMAGE,
      receiverDeviceName,
      requestPayload: {
        imageQuality,
      },
    };
  };

  const takeFrontCameraImage = ({receiverDeviceName, imageQuality}) => {
    return {
      requestType: TAKE_FRONT_CAMERA_IMAGE,
      receiverDeviceName,
      requestPayload: {
        imageQuality,
      },
    };
  };

  const toggleDetectDeviceMovement = ({receiverDeviceName}) => {
    return {
      requestType: TOGGLE_DETECT_DEVICE_MOVEMENT,
      receiverDeviceName,
    };
  };

  const toggleRecognizePerson = ({
    receiverDeviceName,
    cameraType,
    imageRotationDeg,
  }) => {
    return {
      requestType: TOGGLE_RECOGNIZE_PERSON,
      receiverDeviceName,
      requestPayload: {
        cameraType,
        imageRotationDeg,
      },
    };
  };

  const getCameraRecognizePersonSettings = ({
    receiverDeviceName,
    cameraType,
  }) => {
    return {
      requestType: GET_CAMERA_RECOGNIZE_PERSON_SETTINGS,
      receiverDeviceName,
      requestPayload: {
        cameraType,
      },
    };
  };

  return {
    testRequestWithPayload,
    isDeviceAlive,
    takeBackCameraImage,
    takeFrontCameraImage,
    toggleDetectDeviceMovement,
    toggleRecognizePerson,
    getCameraRecognizePersonSettings,
  };
};

export default NativeSurveillanceRequests();
