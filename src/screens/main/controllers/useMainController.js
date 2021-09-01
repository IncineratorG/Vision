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
      MainLocalActions.actions.setRegisterUserGroupVisibility({visible: true}),
    );
  }, [localDispatch]);

  const registerUserGroupDialogCancelHandler = useCallback(() => {
    localDispatch(
      MainLocalActions.actions.setRegisterUserGroupVisibility({
        visible: false,
      }),
    );
  }, [localDispatch]);

  return {
    callback1,
    callback2,
    openRegisterUserGroupDialog,
    registerUserGroupDialogCancelHandler,
  };
};

export default useMainController;
