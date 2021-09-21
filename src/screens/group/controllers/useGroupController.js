import {useCallback} from 'react';
import {BackHandler} from 'react-native';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../store/actions/AppActions';
import GroupLocalActions from '../store/GroupLocalActions';
import useTranslation from '../../../utils/common/localization';

const useGroupController = (model) => {
  const {t} = useTranslation();

  const {
    data: {
      currentGroupName,
      currentGroupPassword,
      currentDeviceName,
      loggedIn,
      localState: {
        deviceRequestsDialog: {
          visible: deviceRequestsDialogVisible,
          selectedDeviceName: deviceRequestsDialogSelectedDeviceName,
        },
      },
    },
    navigation,
    dispatch,
    localDispatch,
  } = model;

  const testRequest = useCallback(async () => {
    SystemEventsHandler.onInfo({
      info: 'useGroupController()->testRequest()',
    });

    dispatch(
      AppActions.surveillanceCommon.actions.sendTestRequestWithPayload({
        receiverDeviceName: 'a',
        valueOne: 'VAL_1',
        valueTwo: 'VAL_2',
      }),
    );
  }, [dispatch]);

  const startService = useCallback(async () => {
    SystemEventsHandler.onInfo({info: 'useGroupController()->startService()'});

    dispatch(AppActions.surveillanceCommon.actions.startService());
  }, [dispatch]);

  const stopService = useCallback(async () => {
    SystemEventsHandler.onInfo({info: 'useGroupController()->stopService()'});

    dispatch(AppActions.surveillanceCommon.actions.stopService());
  }, [dispatch]);

  const backButtonPressHandler = useCallback(() => {
    BackHandler.exitApp();

    return true;
  }, []);

  const updateDevicesInGroupData = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'useGroupController()->updateDevicesInGroupData()',
    });

    dispatch(
      AppActions.surveillanceCommon.actions.getDevicesInGroup({
        groupName: currentGroupName,
        groupPassword: currentGroupPassword,
        deviceName: currentDeviceName,
      }),
    );
  }, [currentGroupName, currentGroupPassword, currentDeviceName, dispatch]);

  const logout = useCallback(() => {
    SystemEventsHandler.onInfo({info: 'logout()'});

    dispatch(AppActions.auth.actions.logoutDevice());
  }, [dispatch]);

  const devicePressHandler = useCallback(
    ({device}) => {
      const {
        deviceName,
        deviceMode,
        lastLoginTimestamp,
        lastUpdateTimestamp,
        hasFrontCamera,
        hasBackCamera,
      } = device;

      SystemEventsHandler.onInfo({
        info: 'useGroupModel()->devicePressHandler(): ' + deviceName,
      });

      // ===
      localDispatch(
        GroupLocalActions.actions.setDeviceRequestsDialogData({device}),
      );
      localDispatch(
        GroupLocalActions.actions.setDeviceRequestsDialogVisibility({
          visible: true,
        }),
      );
      // ===

      // if (deviceMode !== 'service') {
      //   localDispatch(
      //     GroupLocalActions.actions.setSelectedDeviceErrorDialogErrorMessage({
      //       message: t('SelectedDeviceError_notInServiceMode'),
      //     }),
      //   );
      //   localDispatch(
      //     GroupLocalActions.actions.setSelectedDeviceErrorDialogVisibility({
      //       visible: true,
      //     }),
      //   );
      // } else if (Date.now() - lastUpdateTimestamp > 90000) {
      //   localDispatch(
      //     GroupLocalActions.actions.setSelectedDeviceErrorDialogErrorMessage({
      //       message: t('SelectedDeviceError_deviceNotRespond'),
      //     }),
      //   );
      //   localDispatch(
      //     GroupLocalActions.actions.setSelectedDeviceErrorDialogVisibility({
      //       visible: true,
      //     }),
      //   );
      // } else {
      //   localDispatch(
      //     GroupLocalActions.actions.setDeviceRequestsDialogData({device}),
      //   );
      //   localDispatch(
      //     GroupLocalActions.actions.setDeviceRequestsDialogVisibility({
      //       visible: true,
      //     }),
      //   );
      // }
    },
    [localDispatch, t],
  );

  const deviceRequestsDialogCancelHandler = useCallback(() => {
    localDispatch(
      GroupLocalActions.actions.setDeviceRequestsDialogVisibility({
        visible: false,
      }),
    );
  }, [localDispatch]);

  const deviceRequestsDialogGetFrontCameraImageRequestPressHandler =
    useCallback(
      ({selectedDevice}) => {
        SystemEventsHandler.onInfo({
          info:
            'useGroupController->deviceRequestsDialogGetFrontCameraImageRequestPressHandler(): ' +
            JSON.stringify(selectedDevice),
        });

        localDispatch(
          GroupLocalActions.actions.setDeviceRequestsDialogVisibility({
            visible: false,
          }),
        );
      },
      [localDispatch],
    );

  const deviceRequestsDialogGetBackCameraImageRequestPressHandler = useCallback(
    ({selectedDevice}) => {
      SystemEventsHandler.onInfo({
        info:
          'useGroupController->deviceRequestsDialogGetBackCameraImageRequestPressHandler(): ' +
          JSON.stringify(selectedDevice),
      });

      localDispatch(
        GroupLocalActions.actions.setDeviceRequestsDialogVisibility({
          visible: false,
        }),
      );
    },
    [localDispatch],
  );

  const selectedDeviceErrorDialogCancelHandler = useCallback(() => {
    localDispatch(
      GroupLocalActions.actions.setSelectedDeviceErrorDialogVisibility({
        visible: false,
      }),
    );
  }, [localDispatch]);

  return {
    testRequest,
    startService,
    stopService,
    backButtonPressHandler,
    updateDevicesInGroupData,
    logout,
    devicePressHandler,
    deviceRequestsDialogCancelHandler,
    deviceRequestsDialogGetFrontCameraImageRequestPressHandler,
    deviceRequestsDialogGetBackCameraImageRequestPressHandler,
    selectedDeviceErrorDialogCancelHandler,
  };
};

export default useGroupController;
