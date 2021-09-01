const MainLocalActions = () => {
  const types = {
    SET_REGISTER_USER_GROUP_VISIBILITY_DIALOG:
      'MLA_SET_REGISTER_USER_GROUP_VISIBILITY_DIALOG',
  };

  const setRegisterUserGroupVisibility = ({visible}) => {
    return {
      type: types.SET_REGISTER_USER_GROUP_VISIBILITY_DIALOG,
      payload: {visible},
    };
  };

  return {
    types,
    actions: {
      setRegisterUserGroupVisibility,
    },
  };
};

export default MainLocalActions();
