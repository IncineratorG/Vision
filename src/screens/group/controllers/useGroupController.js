import {useCallback} from 'react';
import {BackHandler} from 'react-native';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../store/actions/AppActions';
import Services from '../../../services/Services';
import GroupLocalActions from '../store/GroupLocalActions';

const useGroupController = (model) => {
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
    dispatch,
    localDispatch,
  } = model;

  const testRequest = useCallback(async () => {
    SystemEventsHandler.onInfo({
      info: 'useGroupController()->testRequest()',
    });

    await Services.services().surveillanceService.testRequest();
  }, []);

  const backButtonPressHandler = useCallback(() => {
    BackHandler.exitApp();
  }, []);

  const updateDevicesInGroupData = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'useGroupController()->updateDevicesInGroupData()',
    });

    dispatch(
      AppActions.surveillance.actions.getDevicesInGroup({
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
    ({deviceName}) => {
      SystemEventsHandler.onInfo({
        info: 'useGroupModel()->devicePressHandler(): ' + deviceName,
      });

      localDispatch(
        GroupLocalActions.actions.setDeviceRequestsDialogData({
          selectedDeviceName: deviceName,
        }),
      );
      localDispatch(
        GroupLocalActions.actions.setDeviceRequestsDialogVisibility({
          visible: true,
        }),
      );
    },
    [localDispatch],
  );

  const deviceRequestsDialogCancelHandler = useCallback(() => {
    localDispatch(
      GroupLocalActions.actions.setDeviceRequestsDialogVisibility({
        visible: false,
      }),
    );
  }, [localDispatch]);

  return {
    testRequest,
    backButtonPressHandler,
    updateDevicesInGroupData,
    logout,
    devicePressHandler,
    deviceRequestsDialogCancelHandler,
  };
};

export default useGroupController;
