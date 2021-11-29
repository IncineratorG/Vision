import NativeAuth from '../native-libs/auth/NativeAuth';
import {SystemEventsHandler} from '../../utils/common/system-events-handler/SystemEventsHandler';
import wait from '../../utils/common/wait/wait';

const AuthService = () => {
  const nativeAuthService = NativeAuth();

  const registerDeviceInGroup = async ({
    groupName,
    groupPassword,
    deviceName,
  }) => {
    SystemEventsHandler.onInfo({
      info:
        'AuthService->registerDeviceInGroup(): ' +
        groupName +
        ' - ' +
        groupPassword +
        ' - ' +
        deviceName,
    });

    await wait(1000);

    return nativeAuthService.registerDeviceInGroup({
      groupName,
      groupPassword,
      deviceName,
    });
  };

  const createGroupWithDevice = async ({
    groupName,
    groupPassword,
    deviceName,
  }) => {
    SystemEventsHandler.onInfo({
      info:
        'AuthService->createGroupWithDevice(): ' +
        groupName +
        ' - ' +
        groupPassword +
        ' - ' +
        deviceName,
    });

    await wait(1000);

    return nativeAuthService.createGroupWithDevice({
      groupName,
      groupPassword,
      deviceName,
    });
  };

  const isLoggedIn = async () => {
    return await nativeAuthService.isLoggedIn();
  };

  const loginInGroup = async ({groupName, groupPassword, deviceName}) => {
    SystemEventsHandler.onInfo({
      info:
        'AuthService->loginInGroup(): ' +
        groupName +
        ' - ' +
        groupPassword +
        ' - ' +
        deviceName,
    });

    return nativeAuthService.loginDeviceInGroup({
      groupName,
      groupPassword,
      deviceName,
    });
  };

  const logoutFromGroup = async () => {
    SystemEventsHandler.onInfo({
      info: 'AuthService->logoutFromGroup()',
    });

    return nativeAuthService.logoutDeviceFromGroup();
  };

  const getCurrentAuthenticationData = async () => {
    return await nativeAuthService.getCurrentAuthenticationData();
  };

  return {
    registerDeviceInGroup,
    createGroupWithDevice,
    isLoggedIn,
    loginInGroup,
    logoutFromGroup,
    getCurrentAuthenticationData,
  };
};

export default AuthService;
