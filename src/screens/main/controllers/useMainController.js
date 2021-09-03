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

  const startServicePressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({info: 'startServicePressHandler()'});
  }, []);

  const stopServicePressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({info: 'stopServicePressHandler()'});
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
  };
};

export default useMainController;
