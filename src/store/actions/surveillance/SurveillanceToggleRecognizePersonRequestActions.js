const SurveillanceToggleRecognizePersonRequestActions = () => {
  const types = {
    CLEAR: 'TOGGLE_RECOGNIZE_PERSON_REQUEST_CLEAR',

    SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST:
      'TOGGLE_RECOGNIZE_PERSON_REQUEST_CLEAR_SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST',
    SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_BEGIN:
      'TOGGLE_RECOGNIZE_PERSON_REQUEST_CLEAR_SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_BEGIN',
    SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_SENDED:
      'TOGGLE_RECOGNIZE_PERSON_REQUEST_CLEAR_SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_SENDED',
    SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_COMPLETED:
      'TOGGLE_RECOGNIZE_PERSON_REQUEST_CLEAR_SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_COMPLETED',
    SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_ERROR:
      'TOGGLE_RECOGNIZE_PERSON_REQUEST_CLEAR_SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_ERROR',
    CANCEL_SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST:
      'TOGGLE_RECOGNIZE_PERSON_REQUEST_CLEAR_CANCEL_SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST',
    SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_CANCELLED:
      'TOGGLE_RECOGNIZE_PERSON_REQUEST_CLEAR_SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_CANCELLED',
  };

  const clear = () => {
    return {
      type: types.CLEAR,
    };
  };

  const sendToggleRecognizePersonRequest = ({
    receiverDeviceName,
    cameraType,
  }) => {
    return {
      type: types.SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST,
      payload: {receiverDeviceName, cameraType},
    };
  };

  const sendToggleRecognizePersonRequestBegin = () => {
    return {
      type: types.SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_BEGIN,
    };
  };

  const sendToggleRecognizePersonRequestSended = ({requestId}) => {
    return {
      type: types.SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_SENDED,
      payload: {requestId},
    };
  };

  const sendToggleRecognizePersonRequestCompleted = ({
    frontCameraRecognizePersonServiceRunning,
    backCameraRecognizePersonServiceRunning,
  }) => {
    return {
      type: types.SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_COMPLETED,
      payload: {
        frontCameraRecognizePersonServiceRunning,
        backCameraRecognizePersonServiceRunning,
      },
    };
  };

  const sendToggleRecognizePersonRequestError = ({code, message}) => {
    return {
      type: types.SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_ERROR,
      payload: {code, message},
    };
  };

  const cancelSendToggleRecognizePersonRequest = () => {
    return {
      type: types.CANCEL_SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST,
    };
  };

  const sendToggleRecognizePersonRequestCancelled = () => {
    return {
      type: types.SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_CANCELLED,
    };
  };

  return {
    types,
    actions: {
      clear,
      sendToggleRecognizePersonRequest,
      sendToggleRecognizePersonRequestBegin,
      sendToggleRecognizePersonRequestSended,
      sendToggleRecognizePersonRequestCompleted,
      sendToggleRecognizePersonRequestError,
      cancelSendToggleRecognizePersonRequest,
      sendToggleRecognizePersonRequestCancelled,
    },
  };
};

export default SurveillanceToggleRecognizePersonRequestActions;
