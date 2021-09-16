const AuthActions = () => {
  const types = {
    REGISTER_DEVICE_IN_GROUP: 'AA_REGISTER_DEVICE_IN_GROUP',
    REGISTER_DEVICE_IN_GROUP_BEGIN: 'AA_REGISTER_DEVICE_IN_GROUP_BEGIN',
    REGISTER_DEVICE_IN_GROUP_FINISHED: 'AA_REGISTER_DEVICE_IN_GROUP_FINISHED',
    REGISTER_DEVICE_IN_GROUP_ERROR: 'AA_REGISTER_DEVICE_IN_GROUP_ERROR',

    CREATE_GROUP_WITH_DEVICE: 'AA_CREATE_GROUP_WITH_DEVICE',
    CREATE_GROUP_WITH_DEVICE_BEGIN: 'AA_CREATE_GROUP_WITH_DEVICE_BEGIN',
    CREATE_GROUP_WITH_DEVICE_FINISHED: 'AA_CREATE_GROUP_WITH_DEVICE_FINISHED',
    CREATE_GROUP_WITH_DEVICE_ERROR: 'AA_CREATE_GROUP_WITH_DEVICE_ERROR',

    LOGIN_DEVICE: 'AA_LOGIN_DEVICE',
    LOGIN_DEVICE_BEGIN: 'AA_LOGIN_DEVICE_BEGIN',
    LOGIN_DEVICE_FINISHED: 'AA_LOGIN_DEVICE_FINISHED',
    LOGIN_DEVICE_ERROR: 'AA_LOGIN_DEVICE_ERROR',

    LOGOUT_DEVICE: 'AA_LOGOUT_DEVICE',
    LOGOUT_DEVICE_BEGIN: 'AA_LOGOUT_DEVICE_BEGIN',
    LOGOUT_DEVICE_FINISHED: 'AA_LOGOUT_DEVICE_FINISHED',
    LOGOUT_DEVICE_ERROR: 'AA_LOGOUT_DEVICE_ERROR',
  };

  const registerDeviceInGroup = ({groupName, groupPassword, deviceName}) => {
    return {
      type: types.REGISTER_DEVICE_IN_GROUP,
      payload: {groupName, groupPassword, deviceName},
    };
  };

  const registerDeviceInGroupBegin = () => {
    return {
      type: types.REGISTER_DEVICE_IN_GROUP_BEGIN,
    };
  };

  const registerDeviceInGroupFinished = ({
    groupName,
    groupPassword,
    deviceName,
  }) => {
    return {
      type: types.REGISTER_DEVICE_IN_GROUP_FINISHED,
      payload: {groupName, groupPassword, deviceName},
    };
  };

  const registerDeviceInGroupError = ({code, message}) => {
    return {
      type: types.REGISTER_DEVICE_IN_GROUP_ERROR,
      payload: {code, message},
    };
  };

  const createGroupWithDevice = ({groupName, groupPassword, deviceName}) => {
    return {
      type: types.CREATE_GROUP_WITH_DEVICE,
      payload: {groupName, groupPassword, deviceName},
    };
  };

  const createGroupWithDeviceBegin = () => {
    return {
      type: types.CREATE_GROUP_WITH_DEVICE_BEGIN,
    };
  };

  const createGroupWithDeviceFinished = ({
    groupName,
    groupPassword,
    deviceName,
  }) => {
    return {
      type: types.CREATE_GROUP_WITH_DEVICE_FINISHED,
      payload: {groupName, groupPassword, deviceName},
    };
  };

  const createGroupWithDeviceError = ({code, message}) => {
    return {
      type: types.CREATE_GROUP_WITH_DEVICE_ERROR,
      payload: {code, message},
    };
  };

  const loginDevice = ({groupName, groupPassword, deviceName}) => {
    return {
      type: types.LOGIN_DEVICE,
      payload: {groupName, groupPassword, deviceName},
    };
  };

  const loginDeviceBegin = () => {
    return {
      type: types.LOGIN_DEVICE_BEGIN,
    };
  };

  const loginDeviceFinished = ({groupName, groupPassword, deviceName}) => {
    return {
      type: types.LOGIN_DEVICE_FINISHED,
      payload: {groupName, groupPassword, deviceName},
    };
  };

  const loginDeviceError = ({code, message}) => {
    return {
      type: types.LOGIN_DEVICE_ERROR,
      payload: {code, message},
    };
  };

  const logoutDevice = () => {
    return {
      type: types.LOGOUT_DEVICE,
    };
  };

  const logoutDeviceBegin = () => {
    return {
      type: types.LOGOUT_DEVICE_BEGIN,
    };
  };

  const logoutDeviceFinished = () => {
    return {
      type: types.LOGOUT_DEVICE_FINISHED,
    };
  };

  const logoutDeviceError = ({code, message}) => {
    return {
      type: types.LOGOUT_DEVICE_ERROR,
      payload: {code, message},
    };
  };

  return {
    types,
    actions: {
      registerDeviceInGroup,
      registerDeviceInGroupBegin,
      registerDeviceInGroupFinished,
      registerDeviceInGroupError,
      createGroupWithDevice,
      createGroupWithDeviceBegin,
      createGroupWithDeviceFinished,
      createGroupWithDeviceError,
      loginDevice,
      loginDeviceBegin,
      loginDeviceFinished,
      loginDeviceError,
      logoutDevice,
      logoutDeviceBegin,
      logoutDeviceFinished,
      logoutDeviceError,
    },
  };
};

export default AuthActions;
