const SurveillanceGetCameraRecognizePersonSettingsRequestActions = () => {
  const REQUEST_ID = 'GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST';

  const types = {
    CLEAR: 'GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_CLEAR',

    SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST:
      REQUEST_ID + '_SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST',
    SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_BEGIN:
      REQUEST_ID + '_SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_BEGIN',
    SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_SENDED:
      REQUEST_ID + '_SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_SENDED',
    SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_COMPLETED:
      REQUEST_ID +
      '_SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_COMPLETED',
    SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_ERROR:
      REQUEST_ID + '_SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_ERROR',
    CANCEL_SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST:
      REQUEST_ID + '_CANCEL_SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST',
    SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_CANCELLED:
      REQUEST_ID +
      '_SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_CANCELLED',
  };

  const clear = () => {
    return {
      type: types.CLEAR,
    };
  };

  const sendGetCameraRecognizePersonSettingsRequest = ({
    receiverDeviceName,
    cameraType,
  }) => {
    return {
      type: types.SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST,
      payload: {receiverDeviceName, cameraType},
    };
  };

  const sendGetCameraRecognizePersonSettingsRequestBegin = ({cameraType}) => {
    return {
      type: types.SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_BEGIN,
      payload: {cameraType},
    };
  };

  const sendGetCameraRecognizePersonSettingsRequestSended = ({requestId}) => {
    return {
      type: types.SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_SENDED,
      payload: {requestId},
    };
  };

  const sendGetCameraRecognizePersonSettingsRequestCompleted = ({image}) => {
    return {
      type: types.SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_COMPLETED,
      payload: {image},
    };
  };

  const sendGetCameraRecognizePersonSettingsRequestError = ({
    code,
    message,
  }) => {
    return {
      type: types.SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_ERROR,
      payload: {code, message},
    };
  };

  const cancelSendGetCameraRecognizePersonSettingsRequest = () => {
    return {
      type: types.CANCEL_SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST,
    };
  };

  const sendGetCameraRecognizePersonSettingsRequestCancelled = () => {
    return {
      type: types.SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_CANCELLED,
    };
  };

  return {
    types,
    actions: {
      clear,
      sendGetCameraRecognizePersonSettingsRequest,
      sendGetCameraRecognizePersonSettingsRequestBegin,
      sendGetCameraRecognizePersonSettingsRequestSended,
      sendGetCameraRecognizePersonSettingsRequestCompleted,
      sendGetCameraRecognizePersonSettingsRequestError,
      cancelSendGetCameraRecognizePersonSettingsRequest,
      sendGetCameraRecognizePersonSettingsRequestCancelled,
    },
  };
};

export default SurveillanceGetCameraRecognizePersonSettingsRequestActions;
