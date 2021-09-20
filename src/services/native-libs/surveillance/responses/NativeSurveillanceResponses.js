const NativeSurveillanceResponses = () => {
  const testRequestWithPayloadResponse = (data) => {
    const {resultOne} = data;

    return {
      resultOne,
    };
  };

  const isDeviceAlive = (data) => {
    return null;
  };

  return {
    testRequestWithPayloadResponse,
    isDeviceAlive,
  };
};

export default NativeSurveillanceResponses();
