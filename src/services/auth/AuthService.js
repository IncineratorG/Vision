import NativeAuth from '../native-libs/auth/NativeAuth';
import {SystemEventsHandler} from '../../utils/common/system-events-handler/SystemEventsHandler';

const AuthService = () => {
  const nativeAuthService = NativeAuth();

  const registerDevice = async ({groupLogin, groupPassword, deviceName}) => {
    SystemEventsHandler.onInfo({info: 'AuthService->registerDevice()'});

    return await nativeAuthService.registerDeviceInGroup({
      groupName: groupLogin,
      groupPassword,
      deviceName,
    });
  };

  const loginDevice = async ({groupLogin, groupPassword, deviceName}) => {
    SystemEventsHandler.onInfo({info: 'AuthService->loginDevice()'});

    return await nativeAuthService.loginDeviceInGroup({
      groupName: groupLogin,
      groupPassword,
      deviceName,
    });
  };

  const createDeviceGroup = async ({groupLogin, groupPassword}) => {
    SystemEventsHandler.onInfo({info: 'AuthService->createDeviceGroup()'});

    return await nativeAuthService.createDeviceGroup({
      groupName: groupLogin,
      groupPassword,
    });
  };

  const isDeviceNameAvailable = async ({
    groupLogin,
    groupPassword,
    deviceName,
  }) => {
    SystemEventsHandler.onInfo({info: 'AuthService->isDeviceNameAvailable()'});

    return await nativeAuthService.isDeviceNameAvailable({
      groupName: groupLogin,
      groupPassword,
      deviceName,
    });
  };

  const registerDeviceInGroup = async ({
    groupLogin,
    groupPassword,
    deviceName,
  }) => {
    SystemEventsHandler.onInfo({info: 'AuthService->registerDeviceInGroup()'});

    return await nativeAuthService.registerDeviceInGroup({
      groupName: groupLogin,
      groupPassword,
      deviceName,
    });
  };

  return {
    registerDevice,
    loginDevice,
    createDeviceGroup,
    isDeviceNameAvailable,
    registerDeviceInGroup,
  };
};

export default AuthService;
