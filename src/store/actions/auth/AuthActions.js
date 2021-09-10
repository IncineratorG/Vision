const AuthActions = () => {
  const types = {
    REGISTER_DEVICE: 'AA_REGISTER_DEVICE',
    REGISTER_DEVICE_BEGIN: 'AA_REGISTER_DEVICE_BEGIN',
    REGISTER_DEVICE_FINISHED: 'AA_REGISTER_DEVICE_FINISHED',
    REGISTER_DEVICE_ERROR: 'AA_REGISTER_DEVICE_ERROR',
    LOGIN_DEVICE: 'AA_LOGIN_DEVICE',
    LOGIN_DEVICE_BEGIN: 'AA_LOGIN_DEVICE_BEGIN',
    LOGIN_DEVICE_FINISHED: 'AA_LOGIN_DEVICE_FINISHED',
    LOGIN_DEVICE_ERROR: 'AA_LOGIN_DEVICE_ERROR',
  };

  const registerDevice = ({groupLogin, groupPassword, deviceName}) => {
    return {
      type: types.REGISTER_DEVICE,
      payload: {groupLogin, groupPassword, deviceName},
    };
  };

  const registerDeviceBegin = () => {
    return {
      type: types.REGISTER_DEVICE_BEGIN,
    };
  };

  const registerDeviceFinished = ({groupLogin, groupPassword, deviceName}) => {
    return {
      type: types.REGISTER_DEVICE_FINISHED,
      payload: {groupLogin, groupPassword, deviceName},
    };
  };

  const registerDeviceError = ({code, message}) => {
    return {
      type: types.REGISTER_DEVICE_ERROR,
      payload: {code, message},
    };
  };

  const loginDevice = ({groupLogin, groupPassword, deviceName}) => {
    return {
      type: types.LOGIN_DEVICE,
      payload: {groupLogin, groupPassword, deviceName},
    };
  };

  const loginDeviceBegin = () => {
    return {
      type: types.LOGIN_DEVICE_BEGIN,
    };
  };

  const loginDeviceFinished = ({groupLogin, groupPassword, deviceName}) => {
    return {
      type: types.LOGIN_DEVICE_FINISHED,
      payload: {groupLogin, groupPassword, deviceName},
    };
  };

  const loginDeviceError = ({code, message}) => {
    return {
      type: types.LOGIN_DEVICE_ERROR,
      payload: {code, message},
    };
  };

  return {
    types,
    actions: {
      registerDevice,
      registerDeviceBegin,
      registerDeviceFinished,
      registerDeviceError,
      loginDevice,
      loginDeviceBegin,
      loginDeviceFinished,
      loginDeviceError,
    },
  };
};

export default AuthActions;
