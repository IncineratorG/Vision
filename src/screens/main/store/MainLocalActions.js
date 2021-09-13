const MainLocalActions = () => {
  const types = {
    SET_REGISTER_USER_GROUP_DIALOG_VISIBILITY:
      'MLA_SET_REGISTER_USER_GROUP_DIALOG_VISIBILITY',
    SET_LOGIN_USER_GROUP_DIALOG_VISIBILITY:
      'MLA_SET_LOGIN_USER_GROUP_DIALOG_VISIBILITY',

    SET_REGISTER_DEVICE_IN_GROUP_DIALOG_VISIBILITY:
      'MLA_SET_REGISTER_DEVICE_IN_GROUP_DIALOG_VISIBILITY',
    SET_REGISTER_DEVICE_IN_GROUP_DIALOG_DATA:
      'MLA_SET_REGISTER_DEVICE_IN_GROUP_DIALOG_DATA',
    SET_NEED_CREATE_GROUP_DIALOG_VISIBILITY:
      'MLA_SET_NEED_CREATE_GROUP_DIALOG_VISIBILITY',
    SET_NEED_CREATE_GROUP_DIALOG_DATA: 'MLA_SET_NEED_CREATE_GROUP_DIALOG_DATA',
  };

  const setRegisterUserGroupDialogVisibility = ({visible}) => {
    return {
      type: types.SET_REGISTER_USER_GROUP_DIALOG_VISIBILITY,
      payload: {visible},
    };
  };

  const setLoginUserGroupDialogVisibility = ({visible}) => {
    return {
      type: types.SET_LOGIN_USER_GROUP_DIALOG_VISIBILITY,
      payload: {visible},
    };
  };

  const setRegisterDeviceInGroupDialogData = ({
    groupName,
    groupPassword,
    deviceName,
  }) => {
    return {
      type: types.SET_REGISTER_DEVICE_IN_GROUP_DIALOG_DATA,
      payload: {groupName, groupPassword, deviceName},
    };
  };

  const setRegisterDeviceInGroupDialogVisibility = ({visible}) => {
    return {
      type: types.SET_REGISTER_DEVICE_IN_GROUP_DIALOG_VISIBILITY,
      payload: {visible},
    };
  };

  const setNeedCreateGroupDialogData = ({
    groupName,
    groupPassword,
    deviceName,
  }) => {
    return {
      type: types.SET_NEED_CREATE_GROUP_DIALOG_DATA,
      payload: {groupName, groupPassword, deviceName},
    };
  };

  const setNeedCreateGroupDialogVisibility = ({visible}) => {
    return {
      type: types.SET_NEED_CREATE_GROUP_DIALOG_VISIBILITY,
      payload: {visible},
    };
  };

  return {
    types,
    actions: {
      setRegisterUserGroupDialogVisibility,
      setLoginUserGroupDialogVisibility,
      setRegisterDeviceInGroupDialogData,
      setRegisterDeviceInGroupDialogVisibility,
      setNeedCreateGroupDialogData,
      setNeedCreateGroupDialogVisibility,
    },
  };
};

export default MainLocalActions();
