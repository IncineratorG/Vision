const SelectedDeviceLocalActions = () => {
  const types = {
    SET_DEVICE_DATA: 'SDLA_SET_DEVICE_DATA',
  };

  const setDeviceData = ({device}) => {
    return {
      type: types.SET_DEVICE_DATA,
      payload: {...device},
    };
  };

  return {
    types,
    actions: {
      setDeviceData,
    },
  };
};

export default SelectedDeviceLocalActions();
