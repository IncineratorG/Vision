const GroupLocalActions = () => {
  const types = {
    SET_DEVICE_REQUESTS_DIALOG_VISIBILITY:
      'GLA_SET_DEVICE_REQUESTS_DIALOG_VISIBILITY',
    SET_DEVICE_REQUESTS_DIALOG_DATA: 'GLA_SET_DEVICE_REQUESTS_DIALOG_DATA',
  };

  const setDeviceRequestsDialogVisibility = ({visible}) => {
    return {
      type: types.SET_DEVICE_REQUESTS_DIALOG_VISIBILITY,
      payload: {visible},
    };
  };

  const setDeviceRequestsDialogData = ({selectedDeviceName}) => {
    return {
      type: types.SET_DEVICE_REQUESTS_DIALOG_DATA,
      payload: {selectedDeviceName},
    };
  };

  return {
    types,
    actions: {
      setDeviceRequestsDialogVisibility,
      setDeviceRequestsDialogData,
    },
  };
};

export default GroupLocalActions();
