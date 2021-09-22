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

  const setRequestInProgressDialogData = ({requestId}) => {
    return {
      type: types.SET_REQUEST_IN_PROGRESS_DIALOG_DATA,
      payload: {requestId},
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
    },
  };
};

export default GroupLocalActions();
