import {useState, useCallback, useEffect, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import useTranslation from '../../../utils/common/localization';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppRoutes from '../../../data/common/routes/AppRoutes';
import AppActions from '../../../store/actions/AppActions';

const useAuthorisationModel = () => {
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
  const [authorisationStatusIsError, setAuthorisationStatusIsError] =
    useState(false);
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
    setAuthorisationStatusIsError(false);
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

  // ===
  // =====
  useEffect(() => {
    const currentErrorCode = loginDeviceInGroupErrorCode
      ? loginDeviceInGroupErrorCode
      : createGroupWithDeviceErrorCode
      ? createGroupWithDeviceErrorCode
      : registerDeviceInGroupErrorCode
      ? registerDeviceInGroupErrorCode
      : '';

    if (currentErrorCode) {
      const errorMessage = mapAuthorisationErrorCodesToErrorMessages({
        errorCode: currentErrorCode,
        t,
      });

      setAuthorisationStatus(errorMessage);
      setAuthorisationStatusIsError(true);
    } else {
      setAuthorisationStatusIsError(false);
    }
  }, [
    currentAuthorisationMode,
    authorisationModes,
    loginDeviceInGroupErrorCode,
    createGroupWithDeviceErrorCode,
    registerDeviceInGroupErrorCode,
    t,
  ]);

  useEffect(() => {
    dispatch(AppActions.auth.actions.clearAllErrors());
    setAuthorisationStatus('');
    setAuthorisationStatusIsError(false);
  }, [
    groupName,
    groupPassword,
    deviceName,
    currentAuthorisationMode,
    dispatch,
  ]);
  // =====
  // ===

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
      authorisationStatusIsError,
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

const mapAuthorisationErrorCodesToErrorMessages = ({errorCode, t}) => {
  switch (errorCode) {
    case '5': {
      return t('AuthorisationStatus_errorEmptyGroupName');
    }

    case '6': {
      return t('AuthorisationStatus_errorEmptyGroupPassword');
    }

    case '7': {
      return t('AuthorisationStatus_errorEmptyDeviceName');
    }

    case '8': {
      return t('AuthorisationStatus_errorGroupNotExist');
    }

    case '9': {
      return t('AuthorisationStatus_errorGroupAlreadyExist');
    }

    case '12': {
      return t('AuthorisationStatus_errorIncorrectGroupPassword');
    }

    case '13': {
      return t('AuthorisationStatus_errorDeviceNameAlreadyExist');
    }

    case '15': {
      return t('AuthorisationStatus_errorDeviceAlreadyLoggedIn');
    }

    case '16': {
      return t('AuthorisationStatus_errorDeviceNameNotExist');
    }

    case '17': {
      return t('AuthorisationStatus_errorFirebaseFailure');
    }

    default: {
      return t('AuthorisationStatus_errorUnknown');
    }
  }
};

export default useAuthorisationModel;
