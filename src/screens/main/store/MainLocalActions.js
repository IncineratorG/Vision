const MainLocalActions = () => {
  const types = {
    SET_REGISTER_USER_GROUP_DIALOG_VISIBILITY:
      'MLA_SET_REGISTER_USER_GROUP_DIALOG_VISIBILITY',
    SET_LOGIN_USER_GROUP_DIALOG_VISIBILITY:
      'MLA_SET_LOGIN_USER_GROUP_DIALOG_VISIBILITY',
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

  return {
    types,
    actions: {
      setRegisterUserGroupDialogVisibility,
      setLoginUserGroupDialogVisibility,
    },
  };
};

export default MainLocalActions();
