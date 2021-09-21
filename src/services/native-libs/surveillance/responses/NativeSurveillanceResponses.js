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

  return {
    testRequestWithPayloadResponse,
    isDeviceAlive,
  };
};

export default NativeSurveillanceResponses();
