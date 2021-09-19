const NativeSurveillanceResponses = () => {
  const testRequestWithPayloadResponse = (data) => {
    const {resultOne} = data;

    return {
      resultOne,
    };
  };

  return {
    testRequestWithPayloadResponse,
  };
};

export default NativeSurveillanceResponses();
