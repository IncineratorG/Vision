import {useCallback} from 'react';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import Services from '../../../services/Services';
import MainLocalActions from '../store/MainLocalActions';
import AppActions from '../../../store/actions/AppActions';

const useMainController = (model) => {
  const {
    localDispatch,
    dispatch,
    data: {
      localState: {
        needCreateGroupDialog: {
          groupName: registerDeviceInGroupDialogGroupName,
          groupPassword: registerDeviceInGroupDialogGroupPassword,
          deviceName: registerDeviceInGroupDialogDeviceName,
        },
      },
    },
  } = model;

  const startServicePressHandler = useCallback(async () => {
    SystemEventsHandler.onInfo({info: 'startServicePressHandler()'});

    await Services.services().surveillanceForegroundService.startService();
  }, []);

  const stopServicePressHandler = useCallback(async () => {
    SystemEventsHandler.onInfo({info: 'stopServicePressHandler()'});

    await Services.services().surveillanceForegroundService.stopService();
  }, []);

  const isServiceRunningPressHandler = useCallback(async () => {
    SystemEventsHandler.onInfo({info: 'isServiceRunningPressHandler'});

    await Services.services().surveillanceForegroundService.isServiceRunning();
  }, []);

  const testRequestPressHandler = useCallback(async () => {
    SystemEventsHandler.onInfo({info: 'testRequestPressHandler'});

    await Services.services().surveillanceForegroundService.testRequest();
  }, []);

  const openRegisterDeviceInGroupDialog = useCallback(() => {
    localDispatch(
      MainLocalActions.actions.setRegisterDeviceInGroupDialogVisibility({
        visible: true,
      }),
    );
  }, [localDispatch]);

  const registerDeviceInGroupDialogCancelHandler = useCallback(() => {
    localDispatch(
      MainLocalActions.actions.setRegisterDeviceInGroupDialogVisibility({
        visible: false,
      }),
    );
  }, [localDispatch]);

  const registerDeviceInGroupRegisterPressHandler = useCallback(
    ({groupName, groupPassword, deviceName}) => {
      SystemEventsHandler.onInfo({
        info:
          'registerDeviceInGroupRegisterPressHandler(): ' +
          groupName +
          ' - ' +
          groupPassword +
          ' - ' +
          deviceName,
      });

      localDispatch(
        MainLocalActions.actions.setRegisterDeviceInGroupDialogData({
          groupName,
          groupPassword,
          deviceName,
        }),
      );
      localDispatch(
        MainLocalActions.actions.setRegisterDeviceInGroupDialogVisibility({
          visible: false,
        }),
      );
      dispatch(
        AppActions.auth.actions.registerDeviceInGroup({
          groupName,
          groupPassword,
          deviceName,
        }),
      );
    },
    [localDispatch, dispatch],
  );

  const registeringDeviceInGroupDialogCancelHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'registeringDeviceInGroupDialogCancelHandler()',
    });
  }, []);

  const needCreateGroupDialogCreatePressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info:
        'needCreateGroupDialogCreatePressHandler(): ' +
        registerDeviceInGroupDialogGroupName +
        ' - ' +
        registerDeviceInGroupDialogGroupPassword +
        ' - ' +
        registerDeviceInGroupDialogDeviceName,
    });

    localDispatch(
      MainLocalActions.actions.setNeedCreateGroupDialogVisibility({
        visible: false,
      }),
    );
    dispatch(
      AppActions.auth.actions.createGroupWithDevice({
        groupName: registerDeviceInGroupDialogGroupName,
        groupPassword: registerDeviceInGroupDialogGroupPassword,
        deviceName: registerDeviceInGroupDialogDeviceName,
      }),
    );
  }, [
    registerDeviceInGroupDialogGroupName,
    registerDeviceInGroupDialogGroupPassword,
    registerDeviceInGroupDialogDeviceName,
    localDispatch,
    dispatch,
  ]);

  const needCreateGroupDialogCancelPressHandler = useCallback(() => {
    localDispatch(
      MainLocalActions.actions.setNeedCreateGroupDialogVisibility({
        visible: false,
      }),
    );
  }, [localDispatch]);

  const creatingGroupWithDeviceDialogCancelPressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'creatingGroupWithDeviceDialogCancelPressHandler()',
    });
  }, []);

  const openLoginDeviceInGroupDialog = useCallback(() => {
    localDispatch(
      MainLocalActions.actions.setLoginDeviceInGroupDialogVisibility({
        visible: true,
      }),
    );
  }, [localDispatch]);

  const loginDeviceInGroupDialogCancelHandler = useCallback(() => {
    localDispatch(
      MainLocalActions.actions.setLoginDeviceInGroupDialogVisibility({
        visible: false,
      }),
    );
  }, [localDispatch]);

  const loginDeviceInGroupDialogLoginPressHandler = useCallback(
    ({groupName, groupPassword, deviceName}) => {
      SystemEventsHandler.onInfo({
        info:
          'loginDeviceInGroupDialogLoginPressHandler()' +
          groupName +
          ' - ' +
          groupPassword +
          ' - ' +
          deviceName,
      });

      localDispatch(
        MainLocalActions.actions.setLoginDeviceInGroupDialogVisibility({
          visible: false,
        }),
      );
      dispatch(
        AppActions.auth.actions.loginDevice({
          groupName,
          groupPassword,
          deviceName,
        }),
      );
    },
    [localDispatch, dispatch],
  );

  const loggingDeviceInGroupDialogCancelHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'loggingDeviceInGroupDialogCancelHandler()',
    });
  }, []);

  return {
    startServicePressHandler,
    stopServicePressHandler,
    isServiceRunningPressHandler,
    testRequestPressHandler,
    openRegisterDeviceInGroupDialog,
    registerDeviceInGroupDialogCancelHandler,
    registerDeviceInGroupRegisterPressHandler,
    registeringDeviceInGroupDialogCancelHandler,
    needCreateGroupDialogCreatePressHandler,
    needCreateGroupDialogCancelPressHandler,
    creatingGroupWithDeviceDialogCancelPressHandler,
    openLoginDeviceInGroupDialog,
    loginDeviceInGroupDialogCancelHandler,
    loginDeviceInGroupDialogLoginPressHandler,
    loggingDeviceInGroupDialogCancelHandler,
  };
};

export default useMainController;

// const callback1 = useCallback(async () => {
//   SystemEventsHandler.onInfo({info: 'callback1'});
//
//   Services.services().firebaseService.test();
// }, []);
//
// const callback2 = useCallback(async () => {
//   SystemEventsHandler.onInfo({info: 'callback2'});
// }, []);
//
// const openRegisterUserGroupDialog = useCallback(() => {
//   localDispatch(
//     MainLocalActions.actions.setRegisterUserGroupDialogVisibility({
//       visible: true,
//     }),
//   );
// }, [localDispatch]);
//
// const registerUserGroupDialogCancelHandler = useCallback(() => {
//   localDispatch(
//     MainLocalActions.actions.setRegisterUserGroupDialogVisibility({
//       visible: false,
//     }),
//   );
// }, [localDispatch]);
//
// const registerUserGroupCreatePressHandler = useCallback(
//   ({login, password, deviceName}) => {
//     SystemEventsHandler.onInfo({
//       info:
//         'useMainController->registerUserGroupCreatePressHandler(): ' +
//         login +
//         ' - ' +
//         password +
//         ' - ' +
//         deviceName,
//     });
//
//     // dispatch(
//     //   AppActions.auth.actions.registerDevice({
//     //     groupLogin: login,
//     //     groupPassword: password,
//     //     deviceName,
//     //   }),
//     // );
//
//     localDispatch(
//       MainLocalActions.actions.setRegisterUserGroupDialogVisibility({
//         visible: false,
//       }),
//     );
//   },
//   [localDispatch],
// );
//
// const openLoginIntoUserGroupDialog = useCallback(() => {
//   localDispatch(
//     MainLocalActions.actions.setLoginUserGroupDialogVisibility({
//       visible: true,
//     }),
//   );
// }, [localDispatch]);
//
// const loginIntoUserGroupDialogCancelHandler = useCallback(() => {
//   localDispatch(
//     MainLocalActions.actions.setLoginUserGroupDialogVisibility({
//       visible: false,
//     }),
//   );
// }, [localDispatch]);
//
// const loginIntoUserGroupDialogLoginHandler = useCallback(
//   ({login, password, deviceName}) => {
//     SystemEventsHandler.onInfo({
//       info:
//         'useMainController->loginIntoUserGroupDialogLoginHandler(): ' +
//         login +
//         ' - ' +
//         password +
//         ' - ' +
//         deviceName,
//     });
//
//     // dispatch(
//     //   AppActions.auth.actions.loginDevice({
//     //     groupLogin: login,
//     //     groupPassword: password,
//     //     deviceName,
//     //   }),
//     // );
//
//     localDispatch(
//       MainLocalActions.actions.setLoginUserGroupDialogVisibility({
//         visible: false,
//       }),
//     );
//   },
//   [localDispatch],
// );
//
// const loginIntoUserGroupDialogRegisterHandler = useCallback(() => {
//   localDispatch(
//     MainLocalActions.actions.setLoginUserGroupDialogVisibility({
//       visible: false,
//     }),
//   );
//   localDispatch(
//     MainLocalActions.actions.setRegisterUserGroupDialogVisibility({
//       visible: true,
//     }),
//   );
// }, [localDispatch]);
