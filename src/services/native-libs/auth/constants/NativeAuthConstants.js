import NativeAuthLib from '../lib/NativeAuthLib';

const NativeAuthConstants = () => {
  const {
    actionTypes: {
      IS_DEVICE_GROUP_EXIST,
      IS_DEVICE_NAME_AVAILABLE,
      CREATE_GROUP_WITH_DEVICE,
      REGISTER_DEVICE_IN_GROUP,
      LOGIN_DEVICE_IN_GROUP,
    },
  } = NativeAuthLib.getConstants();

  return {
    actionTypes: {
      IS_DEVICE_GROUP_EXIST,
      IS_DEVICE_NAME_AVAILABLE,
      CREATE_GROUP_WITH_DEVICE,
      REGISTER_DEVICE_IN_GROUP,
      LOGIN_DEVICE_IN_GROUP,
    },
  };
};

export default NativeAuthConstants();
