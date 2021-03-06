const MainLocalActions = () => {
  const types = {
    SET_REGISTER_DEVICE_IN_GROUP_DIALOG_VISIBILITY:
      'MLA_SET_REGISTER_DEVICE_IN_GROUP_DIALOG_VISIBILITY',
    SET_REGISTER_DEVICE_IN_GROUP_DIALOG_DATA:
      'MLA_SET_REGISTER_DEVICE_IN_GROUP_DIALOG_DATA',
    SET_NEED_CREATE_GROUP_DIALOG_VISIBILITY:
      'MLA_SET_NEED_CREATE_GROUP_DIALOG_VISIBILITY',
    SET_NEED_CREATE_GROUP_DIALOG_DATA: 'MLA_SET_NEED_CREATE_GROUP_DIALOG_DATA',
    SET_LOGIN_DEVICE_IN_GROUP_DIALOG_VISIBILITY:
      'MLA_SET_LOGIN_DEVICE_IN_GROUP_DIALOG_VISIBILITY',
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

  const setLoginDeviceInGroupDialogVisibility = ({visible}) => {
    return {
      type: types.SET_LOGIN_DEVICE_IN_GROUP_DIALOG_VISIBILITY,
      payload: {visible},
    };
  };

  return {
    types,
    actions: {
      setRegisterDeviceInGroupDialogData,
      setRegisterDeviceInGroupDialogVisibility,
      setNeedCreateGroupDialogData,
      setNeedCreateGroupDialogVisibility,
      setLoginDeviceInGroupDialogVisibility,
    },
  };
};

export default MainLocalActions();
