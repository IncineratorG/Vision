import NativeAuthLib from '../lib/NativeAuthLib';

const NativeAuthConstants = () => {
  const {
    actionTypes: {
      IS_USER_GROUP_EXIST,
      REGISTER_USER_GROUP,
      CREATE_USER_IN_USER_GROUP,
      LOGIN_USER_IN_USER_GROUP,
    },
  } = NativeAuthLib.getConstants();

  return {
    actionTypes: {
      IS_USER_GROUP_EXIST,
      REGISTER_USER_GROUP,
      CREATE_USER_IN_USER_GROUP,
      LOGIN_USER_IN_USER_GROUP,
    },
  };
};

export default NativeAuthConstants();
