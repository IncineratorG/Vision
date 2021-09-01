import NativeAuthLib from './lib/NativeAuthLib';
import NativeAuthActions from './actions/NativeAuthActions';

const NativeAuth = () => {
  const nativeAuthService = NativeAuthLib;

  const isUserGroupExist = async ({groupLogin, groupPassword}) => {
    const action = NativeAuthActions.isUserGroupExistAction({
      groupLogin,
      groupPassword,
    });
    return await nativeAuthService.execute(action);
  };

  const registerUserGroup = async ({groupLogin, groupPassword}) => {
    const action = NativeAuthActions.registerUserGroupAction({
      groupLogin,
      groupPassword,
    });
    return await nativeAuthService.execute(action);
  };

  const createUserInUserGroup = async ({
    userLogin,
    userPassword,
    groupLogin,
    groupPassword,
  }) => {
    const action = NativeAuthActions.createUserInUserGroupAction({
      userLogin,
      userPassword,
      groupLogin,
      groupPassword,
    });
    return await nativeAuthService.execute(action);
  };

  const loginUserInUserGroup = async ({
    userLogin,
    userPassword,
    groupLogin,
    groupPassword,
  }) => {
    const action = NativeAuthActions.loginUserInUserGroupAction({
      userLogin,
      userPassword,
      groupLogin,
      groupPassword,
    });
    return await nativeAuthService.execute(action);
  };

  return {
    isUserGroupExist,
    registerUserGroup,
    createUserInUserGroup,
    loginUserInUserGroup,
  };
};

export default NativeAuth;
