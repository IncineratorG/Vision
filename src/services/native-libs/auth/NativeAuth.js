import NativeAuthLib from './lib/NativeAuthLib';
import NativeAuthActions from './actions/NativeAuthActions';

const NativeAuth = () => {
  const nativeAuthService = NativeAuthLib;

  const isDeviceGroupExist = async ({groupName}) => {
    const action = NativeAuthActions.isDeviceGroupExistAction({groupName});
    return await nativeAuthService.execute(action);
  };

  const createDeviceGroup = async ({groupName, groupPassword}) => {
    const action = NativeAuthActions.createDeviceGroupAction({
      groupName,
      groupPassword,
    });
    return await nativeAuthService.execute(action);
  };

  const isDeviceNameAvailable = async ({
    groupName,
    groupPassword,
    deviceName,
  }) => {
    const action = NativeAuthActions.isDeviceNameAvailable({
      groupName,
      groupPassword,
      deviceName,
    });
    return await nativeAuthService.execute(action);
  };

  const registerDeviceInGroup = async ({
    groupName,
    groupPassword,
    deviceName,
  }) => {
    const action = NativeAuthActions.registerDeviceInGroupAction({
      groupName,
      groupPassword,
      deviceName,
    });
    return await nativeAuthService.execute(action);
  };

  const loginDeviceInGroup = async ({groupName, groupPassword, deviceName}) => {
    const action = NativeAuthActions.loginDeviceInGroupAction({
      groupName,
      groupPassword,
      deviceName,
    });
    return await nativeAuthService.execute(action);
  };

  return {
    isDeviceGroupExist,
    createDeviceGroup,
    isDeviceNameAvailable,
    registerDeviceInGroup,
    loginDeviceInGroup,
  };
};

export default NativeAuth;
