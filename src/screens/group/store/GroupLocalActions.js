const GroupLocalActions = () => {
  const types = {
    SET_DEVICE_REQUESTS_DIALOG_VISIBILITY:
      'GLA_SET_DEVICE_REQUESTS_DIALOG_VISIBILITY',
    SET_DEVICE_REQUESTS_DIALOG_DATA: 'GLA_SET_DEVICE_REQUESTS_DIALOG_DATA',

    SET_SELECTED_DEVICE_ERROR_DIALOG_VISIBILITY:
      'GLA_SET_SELECTED_DEVICE_ERROR_DIALOG_VISIBILITY',
    SET_SELECTED_DEVICE_ERROR_DIALOG_ERROR_MESSAGE:
      'GLA_SET_SELECTED_DEVICE_ERROR_DIALOG_ERROR_MESSAGE',
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

  return {
    types,
    actions: {
      setDeviceRequestsDialogVisibility,
      setDeviceRequestsDialogData,
      setSelectedDeviceErrorDialogVisibility,
      setSelectedDeviceErrorDialogErrorMessage,
    },
  };
};

export default GroupLocalActions();
