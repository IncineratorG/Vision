import {useState, useCallback, useEffect, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import useTranslation from '../../../utils/common/localization';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppRoutes from '../../../data/common/routes/AppRoutes';
import useGainFocus from '../../../utils/common/hooks/common/useGainFocus';

const useAuthorisationModel = () => {
  // ===
  useGainFocus();
  // ===

  const navigation = useNavigation();

  const dispatch = useDispatch();

  const {t} = useTranslation();

  const authorisationModes = useMemo(() => {
    return {
      login: 'login',
      register: 'register',
    };
  }, []);

  const [currentAuthorisationMode, setCurrentAuthorisationMode] = useState(
    authorisationModes.login,
  );
  const [authorisationStatus, setAuthorisationStatus] = useState('');
  const [groupName, setGroupName] = useState('');
  const [groupPassword, setGroupPassword] = useState('');
  const [deviceName, setDeviceName] = useState('');
  const [forceGroupNameFieldFocus, setForceGroupNameFieldFocus] =
    useState(false);
  const [forceGroupPasswordFieldFocus, setForceGroupPasswordFieldFocus] =
    useState(false);
  const [forceDeviceNameFieldFocus, setForceDeviceNameFieldFocus] =
    useState(false);
  const [needCreateGroupDialogVisible, setNeedCreateGroupDialogVisible] =
    useState(false);

  const {
    groupName: currentGroupName,
    groupPassword: currentGroupPassword,
    deviceName: currentDeviceName,
    loggedIn,
  } = useSelector((store) => store.auth.authInfo);

  const {
    inProgress: registerDeviceInGroupInProgress,
    error: {
      hasError: registerDeviceInGroupHasError,
      code: registerDeviceInGroupErrorCode,
      message: registerDeviceInGroupErrorMessage,
    },
  } = useSelector((store) => store.auth.registerDeviceInGroup);

  const {
    inProgress: createGroupWithDeviceInProgress,
    error: {
      hasError: createGroupWithDeviceHasError,
      code: createGroupWithDeviceErrorCode,
      message: createGroupWithDeviceErrorMessage,
    },
  } = useSelector((store) => store.auth.createGroupWithDevice);

  const {
    inProgress: loginDeviceInGroupInProgress,
    error: {
      hasError: loginDeviceInGroupHasError,
      code: loginDeviceInGroupErrorCode,
      message: loginDeviceInGroupErrorMessage,
    },
  } = useSelector((store) => store.auth.loginDeviceInGroup);

  useEffect(() => {
    if (registerDeviceInGroupInProgress) {
      setAuthorisationStatus(
        t('AuthorisationStatus_registerDeviceInGroupInProgress'),
      );
    } else if (createGroupWithDeviceInProgress) {
      setAuthorisationStatus(
        t('AuthorisationStatus_createGroupWithDeviceInProgress'),
      );
    } else if (loginDeviceInGroupInProgress) {
      setAuthorisationStatus(
        t('AuthorisationStatus_loginDeviceInGroupInProgress'),
      );
    } else {
      setAuthorisationStatus('');
    }
  }, [
    registerDeviceInGroupInProgress,
    createGroupWithDeviceInProgress,
    loginDeviceInGroupInProgress,
    t,
  ]);

  useEffect(() => {
    if (
      registerDeviceInGroupErrorCode &&
      registerDeviceInGroupErrorCode === '8'
    ) {
      setNeedCreateGroupDialogVisible(true);
    }
  }, [registerDeviceInGroupErrorCode]);

  const focusChangedCallback = useCallback(() => {
    if (loggedIn) {
      SystemEventsHandler.onInfo({info: 'useAuthorisationModel()->LOGGED_IN'});

      navigation.navigate(AppRoutes.Group);
    } else {
      SystemEventsHandler.onInfo({
        info: 'useAuthorisationModel()->NOT_LOGGED_IN',
      });
    }
  }, [loggedIn, navigation]);
  useFocusEffect(focusChangedCallback);

  return {
    data: {
      authorisationModes,
      currentAuthorisationMode,
      authorisationStatus,
      groupName,
      groupPassword,
      deviceName,
      forceGroupNameFieldFocus,
      forceGroupPasswordFieldFocus,
      forceDeviceNameFieldFocus,
      needCreateGroupDialogVisible,
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
    navigation,
    dispatch,
  };
};

export default useAuthorisationModel;
