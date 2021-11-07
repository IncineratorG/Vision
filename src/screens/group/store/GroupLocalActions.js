const GroupLocalActions = () => {
  const types = {
    SET_DEVICE_REQUESTS_DIALOG_VISIBILITY:
      'GLA_SET_DEVICE_REQUESTS_DIALOG_VISIBILITY',
    SET_DEVICE_REQUESTS_DIALOG_DATA: 'GLA_SET_DEVICE_REQUESTS_DIALOG_DATA',

    SET_SELECTED_DEVICE_ERROR_DIALOG_VISIBILITY:
      'GLA_SET_SELECTED_DEVICE_ERROR_DIALOG_VISIBILITY',
    SET_SELECTED_DEVICE_ERROR_DIALOG_ERROR_MESSAGE:
      'GLA_SET_SELECTED_DEVICE_ERROR_DIALOG_ERROR_MESSAGE',

    SET_REQUEST_IN_PROGRESS_DIALOG_VISIBILITY:
      'GLA_SET_REQUEST_IN_PROGRESS_DIALOG_VISIBILITY',
    SET_REQUEST_IN_PROGRESS_DIALOG_DATA:
      'GLA_SET_REQUEST_IN_PROGRESS_DIALOG_DATA',

    SET_REQUEST_STATUS_DIALOG_VISIBILITY:
      'GLA_SET_REQUEST_STATUS_DIALOG_VISIBILITY',
    SET_REQUEST_STATUS_DIALOG_RESPONSE_DATA:
      'GLA_SET_REQUEST_STATUS_DIALOG_RESPONSE_DATA',
    CLEAR_REQUEST_STATUS_DIALOG_DATA: 'GLA_CLEAR_REQUEST_STATUS_DIALOG_DATA',

    SET_CURRENT_REQUEST_STATUS_DIALOG_DATA:
      'GLA_SET_CURRENT_REQUEST_STATUS_DIALOG_DATA',
    CLEAR_CURRENT_REQUEST_STATUS_DIALOG_DATA:
      'GLA_CLEAR_CURRENT_REQUEST_STATUS_DIALOG_DATA',

    OPEN_IMAGE_VIEWER: 'GLA_OPEN_IMAGE_VIEWER',
    CLOSE_IMAGE_VIEWER: 'GLA_CLOSE_IMAGE_VIEWER',

    SET_CAMERA_RECOGNIZE_PERSON_SETTINGS_DIALOG_DATA:
      'GLA_SET_CAMERA_RECOGNIZE_PERSON_SETTINGS_DIALOG',
    CLEAR_CAMERA_RECOGNIZE_PERSON_SETTINGS_DIALOG_DATA:
      'GLA_CLEAR_CURRENT_RECOGNIZE_PERSON_SETTINGS_DIALOG_DATA',
  };

  const setDeviceRequestsDialogVisibility = ({visible}) => {
    return {
      type: types.SET_DEVICE_REQUESTS_DIALOG_VISIBILITY,
      payload: {visible},
    };
  };

  const setDeviceRequestsDialogData = ({device}) => {
    return {
      type: types.SET_DEVICE_REQUESTS_DIALOG_DATA,
      payload: {device},
    };
  };

  const setSelectedDeviceErrorDialogVisibility = ({visible}) => {
    return {
      type: types.SET_SELECTED_DEVICE_ERROR_DIALOG_VISIBILITY,
      payload: {visible},
    };
  };

  const setSelectedDeviceErrorDialogErrorMessage = ({message}) => {
    return {
      type: types.SET_SELECTED_DEVICE_ERROR_DIALOG_ERROR_MESSAGE,
      payload: {message},
    };
  };

  const setRequestInProgressDialogVisibility = ({visible}) => {
    return {
      type: types.SET_REQUEST_IN_PROGRESS_DIALOG_VISIBILITY,
      payload: {visible},
    };
  };

  const setRequestInProgressDialogData = ({
    requestId,
    cancelRequestCallback,
  }) => {
    return {
      type: types.SET_REQUEST_IN_PROGRESS_DIALOG_DATA,
      payload: {requestId, cancelRequestCallback},
    };
  };

  const setRequestStatusDialogVisibility = ({visible}) => {
    return {
      type: types.SET_REQUEST_STATUS_DIALOG_VISIBILITY,
      payload: {visible},
    };
  };

  const setRequestStatusDialogResponseData = ({
    data,
    canViewResponse,
    responseViewerCallback,
  }) => {
    return {
      type: types.SET_REQUEST_STATUS_DIALOG_RESPONSE_DATA,
      payload: {data, canViewResponse, responseViewerCallback},
    };
  };

  const clearRequestStatusDialogData = () => {
    return {
      type: types.CLEAR_REQUEST_STATUS_DIALOG_DATA,
    };
  };

  // ===
  const setCurrentRequestStatusDialogData = ({
    visible,
    statusText,
    leftButtonVisible,
    leftButtonText,
    leftButtonPressHandler,
    rightButtonVisible,
    rightButtonText,
    rightButtonPressHandler,
    onCancel,
  }) => {
    return {
      type: types.SET_CURRENT_REQUEST_STATUS_DIALOG_DATA,
      payload: {
        visible,
        statusText,
        leftButtonVisible,
        leftButtonText,
        leftButtonPressHandler,
        rightButtonVisible,
        rightButtonText,
        rightButtonPressHandler,
        onCancel,
      },
    };
  };

  const clearCurrentRequestStatusDialogData = () => {
    return {
      type: types.CLEAR_CURRENT_REQUEST_STATUS_DIALOG_DATA,
    };
  };
  // ===

  const openImageViewer = ({image}) => {
    return {
      type: types.OPEN_IMAGE_VIEWER,
      payload: {image},
    };
  };

  const closeImageViewer = () => {
    return {
      type: types.CLOSE_IMAGE_VIEWER,
    };
  };

  const setCameraRecognizePersonSettingsDialogData = ({
    visible,
    image,
    confirmSettingsButtonPressHandler,
    cancelButtonPressHandler,
    onCancel,
  }) => {
    return {
      type: types.SET_CAMERA_RECOGNIZE_PERSON_SETTINGS_DIALOG_DATA,
      payload: {
        visible,
        image,
        confirmSettingsButtonPressHandler,
        cancelButtonPressHandler,
        onCancel,
      },
    };
  };

  const clearCameraRecognizePersonSettingsDialogData = () => {
    return {
      type: types.CLEAR_CAMERA_RECOGNIZE_PERSON_SETTINGS_DIALOG_DATA,
    };
  };

  return {
    types,
    actions: {
      setDeviceRequestsDialogVisibility,
      setDeviceRequestsDialogData,
      setSelectedDeviceErrorDialogVisibility,
      setSelectedDeviceErrorDialogErrorMessage,
      setRequestInProgressDialogVisibility,
      setRequestInProgressDialogData,
      setRequestStatusDialogVisibility,
      setRequestStatusDialogResponseData,
      clearRequestStatusDialogData,
      setCurrentRequestStatusDialogData,
      clearCurrentRequestStatusDialogData,
      openImageViewer,
      closeImageViewer,
      setCameraRecognizePersonSettingsDialogData,
      clearCameraRecognizePersonSettingsDialogData,
    },
  };
};

export default GroupLocalActions();
