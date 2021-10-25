const NativeSurveillanceResponses = () => {
  const testRequestWithPayloadResponse = (data) => {
    const {resultOne} = data;

    return {
      resultOne,
    };
  };

  const isDeviceAlive = (data) => {
    const {isAlive} = data;

    return {
      isAlive,
    };
  };

  const takeBackCameraImage = (data) => {
    const {image} = data;

    return {
      image,
    };
  };

  const takeFrontCameraImage = (data) => {
    const {image} = data;

    return {
      image,
    };
  };

  const toggleDetectDeviceMovement = (data) => {
    const {detectDeviceMovementServiceRunning} = data;

    return {
      detectDeviceMovementServiceRunning,
    };
  };

  const toggleRecognizePerson = (data) => {
    const {
      frontCameraRecognizePersonServiceRunning,
      backCameraRecognizePersonServiceRunning,
    } = data;

    return {
      frontCameraRecognizePersonServiceRunning,
      backCameraRecognizePersonServiceRunning,
    };
  };

  return {
    testRequestWithPayloadResponse,
    isDeviceAlive,
    takeBackCameraImage,
    takeFrontCameraImage,
    toggleDetectDeviceMovement,
    toggleRecognizePerson,
  };
};

export default NativeSurveillanceResponses();
