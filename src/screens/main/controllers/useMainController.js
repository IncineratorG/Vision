import {useCallback} from 'react';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import Services from '../../../services/Services';
import MainLocalActions from '../store/MainLocalActions';

const useMainController = (model) => {
  const {localDispatch} = model;

  const callback1 = useCallback(async () => {
    SystemEventsHandler.onInfo({info: 'callback1'});

    Services.services().firebaseService.test();
  }, []);

  const callback2 = useCallback(async () => {
    SystemEventsHandler.onInfo({info: 'callback2'});
  }, []);

  const openRegisterUserGroupDialog = useCallback(() => {
    localDispatch(
      MainLocalActions.actions.setRegisterUserGroupDialogVisibility({
        visible: true,
      }),
    );
  }, [localDispatch]);

  const registerUserGroupDialogCancelHandler = useCallback(() => {
    localDispatch(
      MainLocalActions.actions.setRegisterUserGroupDialogVisibility({
        visible: false,
      }),
    );
  }, [localDispatch]);

  const registerUserGroupCreatePressHandler = useCallback(
    ({login, password}) => {
      SystemEventsHandler.onInfo({
        info:
          'useMainController->registerUserGroupCreatePressHandler(): ' +
          login +
          ' - ' +
          password,
      });

      localDispatch(
        MainLocalActions.actions.setRegisterUserGroupDialogVisibility({
          visible: false,
        }),
      );
    },
    [localDispatch],
  );

  const openLoginIntoUserGroupDialog = useCallback(() => {
    localDispatch(
      MainLocalActions.actions.setLoginUserGroupDialogVisibility({
        visible: true,
      }),
    );
  }, [localDispatch]);

  const loginIntoUserGroupDialogCancelHandler = useCallback(() => {
    localDispatch(
      MainLocalActions.actions.setLoginUserGroupDialogVisibility({
        visible: false,
      }),
    );
  }, [localDispatch]);

  const loginIntoUserGroupDialogLoginHandler = useCallback(
    ({login, password}) => {
      SystemEventsHandler.onInfo({
        info:
          'useMainController->loginIntoUserGroupDialogLoginHandler(): ' +
          login +
          ' - ' +
          password,
      });

      localDispatch(
        MainLocalActions.actions.setLoginUserGroupDialogVisibility({
          visible: false,
        }),
      );
    },
    [localDispatch],
  );

  const loginIntoUserGroupDialogRegisterHandler = useCallback(() => {
    localDispatch(
      MainLocalActions.actions.setLoginUserGroupDialogVisibility({
        visible: false,
      }),
    );
    localDispatch(
      MainLocalActions.actions.setRegisterUserGroupDialogVisibility({
        visible: true,
      }),
    );
  }, [localDispatch]);

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

  return {
    callback1,
    callback2,
    openRegisterUserGroupDialog,
    registerUserGroupDialogCancelHandler,
    registerUserGroupCreatePressHandler,
    openLoginIntoUserGroupDialog,
    loginIntoUserGroupDialogCancelHandler,
    loginIntoUserGroupDialogLoginHandler,
    loginIntoUserGroupDialogRegisterHandler,
    startServicePressHandler,
    stopServicePressHandler,
    isServiceRunningPressHandler,
    testRequestPressHandler,
  };
};

export default useMainController;
