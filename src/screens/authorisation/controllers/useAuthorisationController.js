import {useCallback} from 'react';
import {BackHandler} from 'react-native';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../store/actions/AppActions';

const useAuthorisationController = (model) => {
  const {
    data: {
      authorisationModes,
      currentAuthorisationMode,
      authorisationStatus,
      groupName,
      groupPassword,
      deviceName,
    },
    setters: {
      setCurrentAuthorisationMode,
      setGroupName,
      setGroupPassword,
      setDeviceName,
      setForceGroupNameFieldFocus,
      setForceGroupPasswordFieldFocus,
      setForceDeviceNameFieldFocus,
      setNeedCreateGroupDialogVisible,
    },
    dispatch,
  } = model;

  const backButtonPressHandler = useCallback(() => {
    BackHandler.exitApp();

    return true;
  }, []);

  const loginButtonPressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'useAuthorisationController()->loginButtonPressHandler()',
    });
    dispatch(
      AppActions.auth.actions.loginDevice({
        groupName,
        groupPassword,
        deviceName,
      }),
    );
  }, [groupName, groupPassword, deviceName, dispatch]);

  const loginTextPressHandler = useCallback(() => {
    setCurrentAuthorisationMode(authorisationModes.login);
    setGroupName('');
    setGroupPassword('');
    setDeviceName('');
  }, [
    authorisationModes.login,
    setCurrentAuthorisationMode,
    setGroupName,
    setGroupPassword,
    setDeviceName,
  ]);

  const registerButtonPressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'useAuthorisationController()->registerButtonPressHandler()',
    });
    dispatch(
      AppActions.auth.actions.registerDeviceInGroup({
        groupName,
        groupPassword,
        deviceName,
      }),
    );
  }, [groupName, groupPassword, deviceName, dispatch]);

  const registerTextPressHandler = useCallback(() => {
    setCurrentAuthorisationMode(authorisationModes.register);
    setGroupName('');
    setGroupPassword('');
    setDeviceName('');
  }, [
    authorisationModes.register,
    setCurrentAuthorisationMode,
    setGroupName,
    setGroupPassword,
    setDeviceName,
  ]);

  const groupNameChangeHandler = useCallback(
    (text) => {
      setGroupName(text);
    },
    [setGroupName],
  );

  const groupPasswordChangeHandler = useCallback(
    (text) => {
      setGroupPassword(text);
    },
    [setGroupPassword],
  );

  const deviceNameChangeHandler = useCallback(
    (text) => {
      setDeviceName(text);
    },
    [setDeviceName],
  );

  const groupNameSubmitEditingPressHandler = useCallback(() => {
    setForceGroupNameFieldFocus(false);
    setForceGroupPasswordFieldFocus(true);
    setForceDeviceNameFieldFocus(false);
  }, [
    setForceGroupNameFieldFocus,
    setForceGroupPasswordFieldFocus,
    setForceDeviceNameFieldFocus,
  ]);

  const groupPasswordSubmitEditingPressHandler = useCallback(() => {
    setForceGroupNameFieldFocus(false);
    setForceGroupPasswordFieldFocus(false);
    setForceDeviceNameFieldFocus(true);
  }, [
    setForceGroupNameFieldFocus,
    setForceGroupPasswordFieldFocus,
    setForceDeviceNameFieldFocus,
  ]);

  const deviceNameSubmitEditingPressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info:
        'useAuthorisationController()->deviceNameSubmitEditingPressHandler(): ' +
        groupName +
        ' - ' +
        groupPassword +
        ' - ' +
        deviceName,
    });
    setForceGroupNameFieldFocus(false);
    setForceGroupPasswordFieldFocus(false);
    setForceDeviceNameFieldFocus(false);

    if (currentAuthorisationMode === authorisationModes.login) {
      dispatch(
        AppActions.auth.actions.loginDevice({
          groupName,
          groupPassword,
          deviceName,
        }),
      );
    } else if (currentAuthorisationMode === authorisationModes.register) {
      dispatch(
        AppActions.auth.actions.registerDeviceInGroup({
          groupName,
          groupPassword,
          deviceName,
        }),
      );
    } else {
      SystemEventsHandler.onInfo({
        info:
          'useAuthorisationController()->deviceNameSubmitEditingPressHandler()->UNKNOWN_AUTHORISATION_MODE: ' +
          currentAuthorisationMode,
      });
    }
  }, [
    authorisationModes,
    currentAuthorisationMode,
    groupName,
    groupPassword,
    deviceName,
    setForceGroupNameFieldFocus,
    setForceGroupPasswordFieldFocus,
    setForceDeviceNameFieldFocus,
    dispatch,
  ]);

  const needCreateGroupDialogCreatePressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info:
        'useAuthorisationController()->needCreateGroupDialogCreatePressHandler(): ' +
        groupName +
        ' - ' +
        groupPassword +
        ' - ' +
        deviceName,
    });
    setNeedCreateGroupDialogVisible(false);

    dispatch(
      AppActions.auth.actions.createGroupWithDevice({
        groupName,
        groupPassword,
        deviceName,
      }),
    );
  }, [
    groupName,
    groupPassword,
    deviceName,
    setNeedCreateGroupDialogVisible,
    dispatch,
  ]);

  const needCreateGroupDialogCancelPressHandler = useCallback(() => {
    setNeedCreateGroupDialogVisible(false);
  }, [setNeedCreateGroupDialogVisible]);

  return {
    backButtonPressHandler,
    loginButtonPressHandler,
    loginTextPressHandler,
    registerButtonPressHandler,
    registerTextPressHandler,
    groupNameChangeHandler,
    groupPasswordChangeHandler,
    deviceNameChangeHandler,
    groupNameSubmitEditingPressHandler,
    groupPasswordSubmitEditingPressHandler,
    deviceNameSubmitEditingPressHandler,
    needCreateGroupDialogCreatePressHandler,
    needCreateGroupDialogCancelPressHandler,
  };
};

export default useAuthorisationController;
