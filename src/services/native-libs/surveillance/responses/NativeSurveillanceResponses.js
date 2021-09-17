const NativeSurveillanceResponses = () => {
  const testRequestWithPayloadResponse = (data) => {
    const {requestId, resultOne} = data;

    return {
      requestId,
      resultOne,
    };
  };

  return {
    testRequestWithPayloadResponse,
  };
};

export default NativeSurveillanceResponses();
