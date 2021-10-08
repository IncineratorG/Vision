const SurveillanceToggleDetectDeviceMovementRequestActions = () => {
  const types = {
    CLEAR: 'STDDMRA_CLEAR',

    SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST:
      'STDDMRA_SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST',
    SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_BEGIN:
      'STDDMRA_SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_BEGIN',
    SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_SENDED:
      'STDDMRA_SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_SENDED',
    SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_COMPLETED:
      'STDDMRA_SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_COMPLETED',
    SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_ERROR:
      'STDDMRA_SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_ERROR',
    CANCEL_SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST:
      'STDDMRA_CANCEL_SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST',
    SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_CANCELLED:
      'STDDMRA_SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_CANCELLED',
  };

  const clear = () => {
    return {
      type: types.CLEAR,
    };
  };

  const sendToggleDetectDeviceMovementRequest = ({receiverDeviceName}) => {
    return {
      type: types.SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST,
      payload: {receiverDeviceName},
    };
  };

  const sendToggleDetectDeviceMovementRequestBegin = () => {
    return {
      type: types.SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_BEGIN,
    };
  };

  const sendToggleDetectDeviceMovementRequestSended = ({requestId}) => {
    return {
      type: types.SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_SENDED,
      payload: {requestId},
    };
  };

  const sendToggleDetectDeviceMovementRequestCompleted = ({
    detectDeviceMovementServiceRunning,
  }) => {
    return {
      type: types.SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_COMPLETED,
      payload: {detectDeviceMovementServiceRunning},
    };
  };

  const sendToggleDetectDeviceMovementRequestError = ({code, message}) => {
    return {
      type: types.SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_ERROR,
      payload: {code, message},
    };
  };

  const cancelSendToggleDetectDeviceMovementRequest = () => {
    return {
      type: types.CANCEL_SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST,
    };
  };

  const sendToggleDetectDeviceMovementRequestCancelled = () => {
    return {
      type: types.SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_CANCELLED,
    };
  };

  return {
    types,
    actions: {
      clear,
      sendToggleDetectDeviceMovementRequest,
      sendToggleDetectDeviceMovementRequestBegin,
      sendToggleDetectDeviceMovementRequestSended,
      sendToggleDetectDeviceMovementRequestCompleted,
      sendToggleDetectDeviceMovementRequestError,
      cancelSendToggleDetectDeviceMovementRequest,
      sendToggleDetectDeviceMovementRequestCancelled,
    },
  };
};

export default SurveillanceToggleDetectDeviceMovementRequestActions;
