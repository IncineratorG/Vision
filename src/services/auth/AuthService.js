import NativeAuth from '../native-libs/auth/NativeAuth';

const AuthService = () => {
  const nativeAuthService = NativeAuth();

  const isUserGroupExist = async ({groupLogin, groupPassword}) => {
    return await nativeAuthService.isUserGroupExist({
      groupLogin,
      groupPassword,
    });
  };

  const registerUserGroup = async ({groupLogin, groupPassword}) => {
    return await nativeAuthService.registerUserGroup({
      groupLogin,
      groupPassword,
    });
  };

  const createUserInUserGroup = async ({
    userLogin,
    userPassword,
    groupLogin,
    groupPassword,
  }) => {
    return await nativeAuthService.createUserInUserGroup({
      userLogin,
      userPassword,
      groupLogin,
      groupPassword,
    });
  };

  const loginUserInUserGroup = async ({
    userLogin,
    userPassword,
    groupLogin,
    groupPassword,
  }) => {
    return await nativeAuthService.loginUserInUserGroup({
      userLogin,
      userPassword,
      groupLogin,
      groupPassword,
    });
  };

  return {
    isUserGroupExist,
    registerUserGroup,
    createUserInUserGroup,
    loginUserInUserGroup,
  };
};

export default AuthService;
