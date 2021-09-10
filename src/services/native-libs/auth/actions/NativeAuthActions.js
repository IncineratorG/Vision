import NativeAuthConstants from '../constants/NativeAuthConstants';

const NativeFirebaseActions = () => {
  const {
    actionTypes: {
      IS_DEVICE_GROUP_EXIST,
      IS_DEVICE_NAME_AVAILABLE,
      CREATE_DEVICE_GROUP,
      REGISTER_DEVICE_IN_GROUP,
      LOGIN_DEVICE_IN_GROUP,
    },
  } = NativeAuthConstants;

  const isDeviceGroupExistAction = ({groupName}) => {
    return {
      type: IS_DEVICE_GROUP_EXIST,
      payload: {groupName},
    };
  };

  const createDeviceGroupAction = ({groupName, groupPassword}) => {
    return {
      type: CREATE_DEVICE_GROUP,
      payload: {groupName, groupPassword},
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

  const loginDeviceInGroupAction = ({groupName, groupPassword, deviceName}) => {
    return {
      type: LOGIN_DEVICE_IN_GROUP,
      payload: {groupName, groupPassword, deviceName},
    };
  };

  return {
    isDeviceGroupExistAction,
    createDeviceGroupAction,
    isDeviceNameAvailable,
    registerDeviceInGroupAction,
    loginDeviceInGroupAction,
  };
};

export default NativeFirebaseActions();
