const NativeSurveillanceResponses = () => {
  const testRequestWithPayloadResponse = (data) => {
    const {resultOne} = data;

    return {
      resultOne,
    };
  };

  const getDeviceAvailableActions = (data) => {
    const {actionsArray} = data;

    return {
      actionsArray,
    };
  };

  return {
    testRequestWithPayloadResponse,
    getDeviceAvailableActions,
  };
};

export default NativeSurveillanceResponses();
