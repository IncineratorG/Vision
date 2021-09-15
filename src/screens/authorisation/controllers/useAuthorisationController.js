import {useCallback} from 'react';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';

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
    },
  } = model;

  const loginButtonPressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'useAuthorisationController()->loginButtonPressHandler()',
    });
  }, []);

  const loginTextPressHandler = useCallback(() => {
    setCurrentAuthorisationMode(authorisationModes.login);
  }, [authorisationModes.login, setCurrentAuthorisationMode]);

  const registerButtonPressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'useAuthorisationController()->registerButtonPressHandler()',
    });
  }, []);

  const registerTextPressHandler = useCallback(() => {
    setCurrentAuthorisationMode(authorisationModes.register);
  }, [authorisationModes.register, setCurrentAuthorisationMode]);

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
  }, [
    groupName,
    groupPassword,
    deviceName,
    setForceGroupNameFieldFocus,
    setForceGroupPasswordFieldFocus,
    setForceDeviceNameFieldFocus,
  ]);

  return {
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
  };
};

export default useAuthorisationController;
