const SurveillanceIsDeviceAliveRequestActions = () => {
  const types = {
    SEND_IS_ALIVE_REQUEST: 'SIARA_SEND_IS_ALIVE_REQUEST',
    SEND_IS_ALIVE_REQUEST_BEGIN: 'SIARA_SEND_IS_ALIVE_REQUEST_BEGIN',
    SEND_IS_ALIVE_REQUEST_SENDED: 'SIARA_SEND_IS_ALIVE_REQUEST_SENDED',
    SEND_IS_ALIVE_REQUEST_COMPLETED: 'SIARA_SEND_IS_ALIVE_REQUEST_COMPLETED',
    SEND_IS_ALIVE_REQUEST_ERROR: 'SIARA_SEND_IS_ALIVE_REQUEST_ERROR',
    CANCEL_SEND_IS_ALIVE_REQUEST: 'SIARA_CANCEL_SEND_IS_ALIVE_REQUEST',
    SEND_IS_ALIVE_REQUEST_CANCELLED: 'SIARA_SEND_IS_ALIVE_REQUEST_CANCELLED',
  };

  const sendIsAliveRequest = ({receiverDeviceName}) => {
    return {
      type: types.SEND_IS_ALIVE_REQUEST,
      payload: {receiverDeviceName},
    };
  };

  const sendIsAliveRequestBegin = () => {
    return {
      type: types.SEND_IS_ALIVE_REQUEST_BEGIN,
    };
  };

  const sendIsAliveRequestSended = ({requestId}) => {
    return {
      type: types.SEND_IS_ALIVE_REQUEST_SENDED,
      payload: {requestId},
    };
  };

  const sendIsAliveRequestCompleted = ({isAlive}) => {
    return {
      type: types.SEND_IS_ALIVE_REQUEST_COMPLETED,
      payload: {isAlive},
    };
  };

  const sendIsAliveRequestError = ({code, message}) => {
    return {
      type: types.SEND_IS_ALIVE_REQUEST_ERROR,
      payload: {code, message},
    };
  };

  const cancelSendIsAliveRequest = () => {
    return {
      type: types.CANCEL_SEND_IS_ALIVE_REQUEST,
    };
  };

  const sendIsAliveRequestCancelled = () => {
    return {
      type: types.SEND_IS_ALIVE_REQUEST_CANCELLED,
    };
  };

  return {
    types,
    actions: {
      sendIsAliveRequest,
      sendIsAliveRequestBegin,
      sendIsAliveRequestSended,
      sendIsAliveRequestCompleted,
      sendIsAliveRequestError,
      cancelSendIsAliveRequest,
      sendIsAliveRequestCancelled,
    },
  };
};

export default SurveillanceIsDeviceAliveRequestActions;
