import NativeAuthConstants from '../constants/NativeAuthConstants';

const NativeFirebaseActions = () => {
  const {
    actionTypes: {
      IS_DEVICE_GROUP_EXIST,
      IS_DEVICE_NAME_AVAILABLE,
      CREATE_GROUP_WITH_DEVICE,
      REGISTER_DEVICE_IN_GROUP,
      IS_LOGGED_IN,
      LOGIN_DEVICE_IN_GROUP,
      LOGOUT_DEVICE_FROM_GROUP,
    },
  } = NativeAuthConstants;

  const isDeviceGroupExistAction = ({groupName}) => {
    return {
      type: IS_DEVICE_GROUP_EXIST,
      payload: {groupName},
    };
  };

  const createGroupWithDeviceAction = ({
    groupName,
    groupPassword,
    deviceName,
  }) => {
    return {
      type: CREATE_GROUP_WITH_DEVICE,
      payload: {groupName, groupPassword, deviceName},
    };
  };

  const isDeviceNameAvailable = ({groupName, groupPassword, deviceName}) => {
    return {
      type: IS_DEVICE_NAME_AVAILABLE,
      payload: {groupName, groupPassword, deviceName},
    };
  };

  const registerDeviceInGroupAction = ({
    groupName,
    groupPassword,
    deviceName,
  }) => {
    return {
      type: REGISTER_DEVICE_IN_GROUP,
      payload: {groupName, groupPassword, deviceName},
    };
  };

  const isLoggedInAction = () => {
    return {
      type: IS_LOGGED_IN,
    };
  };

  const loginDeviceInGroupAction = ({groupName, groupPassword, deviceName}) => {
    return {
      type: LOGIN_DEVICE_IN_GROUP,
      payload: {groupName, groupPassword, deviceName},
    };
  };

  const logoutDeviceFromGroupAction = ({
    groupName,
    groupPassword,
    deviceName,
  }) => {
    return {
      type: LOGOUT_DEVICE_FROM_GROUP,
      payload: {groupName, groupPassword, deviceName},
    };
  };

  return {
    isDeviceGroupExistAction,
    createGroupWithDeviceAction,
    isDeviceNameAvailable,
    registerDeviceInGroupAction,
    isLoggedInAction,
    loginDeviceInGroupAction,
    logoutDeviceFromGroupAction,
  };
};

export default NativeFirebaseActions();
