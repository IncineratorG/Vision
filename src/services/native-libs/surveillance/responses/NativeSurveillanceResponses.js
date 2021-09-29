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

  return {
    testRequestWithPayloadResponse,
    isDeviceAlive,
    takeBackCameraImage,
    takeFrontCameraImage,
  };
};

export default NativeSurveillanceResponses();
