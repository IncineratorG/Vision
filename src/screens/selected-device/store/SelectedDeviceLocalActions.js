const SelectedDeviceLocalActions = () => {
  const types = {
    SET_DEVICE_DATA: 'SDLA_SET_DEVICE_DATA',

    SET_CURRENT_REQUEST_STATUS_DIALOG_DATA:
      'SDLA_SET_CURRENT_REQUEST_STATUS_DIALOG_DATA',
    CLEAR_CURRENT_REQUEST_STATUS_DIALOG_DATA:
      'SDLA_CLEAR_CURRENT_REQUEST_STATUS_DIALOG_DATA',

    OPEN_IMAGE_VIEWER: 'SDLA_OPEN_IMAGE_VIEWER',
    CLOSE_IMAGE_VIEWER: 'SDLA_CLOSE_IMAGE_VIEWER',

    SET_CAMERA_RECOGNIZE_PERSON_SETTINGS_DIALOG_DATA:
      'SDLA_SET_CAMERA_RECOGNIZE_PERSON_SETTINGS_DIALOG',
    CLEAR_CAMERA_RECOGNIZE_PERSON_SETTINGS_DIALOG_DATA:
      'SDLA_CLEAR_CURRENT_RECOGNIZE_PERSON_SETTINGS_DIALOG_DATA',
  };

  const setDeviceData = ({device}) => {
    return {
      type: types.SET_DEVICE_DATA,
      payload: {device: {...device}},
    };
  };

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
      setDeviceData,
      setCurrentRequestStatusDialogData,
      clearCurrentRequestStatusDialogData,
      openImageViewer,
      closeImageViewer,
      setCameraRecognizePersonSettingsDialogData,
      clearCameraRecognizePersonSettingsDialogData,
    },
  };
};

export default SelectedDeviceLocalActions();
