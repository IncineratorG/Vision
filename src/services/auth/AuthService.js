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

  const logoutFromGroup = async ({groupName, groupPassword, deviceName}) => {
    SystemEventsHandler.onInfo({
      info:
        'AuthService->logoutFromGroup(): ' +
        groupName +
        ' - ' +
        groupPassword +
        ' - ' +
        deviceName,
    });

    return nativeAuthService.logoutDeviceFromGroup({
      groupName,
      groupPassword,
      deviceName,
    });
  };

  return {
    registerDeviceInGroup,
    createGroupWithDevice,
    loginInGroup,
    logoutFromGroup,
  };
};

export default AuthService;
