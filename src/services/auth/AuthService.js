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

    return true;
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

    return true;
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

    await wait(1000);

    return true;
  };

  return {
    registerDeviceInGroup,
    createGroupWithDevice,
    loginInGroup,
  };
};

export default AuthService;
