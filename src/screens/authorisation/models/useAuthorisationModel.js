import {useCallback} from 'react';
import {useNavigation} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';

const useAuthorisationModel = () => {
  const navigation = useNavigation();

  const dispatch = useDispatch();

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

  return {
    data: {},
    setters: {},
    navigation,
    dispatch,
  };
};

export default useAuthorisationModel;
