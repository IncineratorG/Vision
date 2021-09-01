import NativeAuthConstants from '../constants/NativeAuthConstants';

const NativeFirebaseActions = () => {
  const {
    actionTypes: {
      IS_USER_GROUP_EXIST,
      REGISTER_USER_GROUP,
      CREATE_USER_IN_USER_GROUP,
      LOGIN_USER_IN_USER_GROUP,
    },
  } = NativeAuthConstants;

  const isUserGroupExistAction = ({groupLogin, groupPassword}) => {
    return {
      type: IS_USER_GROUP_EXIST,
      payload: {groupLogin, groupPassword},
    };
  };

  const registerUserGroupAction = ({groupLogin, groupPassword}) => {
    return {
      type: REGISTER_USER_GROUP,
      payload: {groupLogin, groupPassword},
    };
  };

  const createUserInUserGroupAction = ({
    userLogin,
    userPassword,
    groupLogin,
    groupPassword,
  }) => {
    return {
      type: CREATE_USER_IN_USER_GROUP,
      payload: {userLogin, userPassword, groupLogin, groupPassword},
    };
  };

  const loginUserInUserGroupAction = ({
    userLogin,
    userPassword,
    groupLogin,
    groupPassword,
  }) => {
    return {
      type: LOGIN_USER_IN_USER_GROUP,
      payload: {userLogin, userPassword, groupLogin, groupPassword},
    };
  };

  return {
    isUserGroupExistAction,
    registerUserGroupAction,
    createUserInUserGroupAction,
    loginUserInUserGroupAction,
  };
};

export default NativeFirebaseActions();
