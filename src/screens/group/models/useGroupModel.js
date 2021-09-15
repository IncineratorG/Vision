import {useState, useCallback, useEffect, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';

const useGroupModel = () => {
  const navigation = useNavigation();

  const dispatch = useDispatch();

  const {
    groupName: currentGroupName,
    groupPassword: currentGroupPassword,
    deviceName: currentDeviceName,
    loggedIn,
  } = useSelector((state) => state.auth.authInfo);

  const {
    inProgress: loadingDevicesInGroup,
    devices: devicesInGroupArray,
    error: {
      hasError: devicesInGroupHasError,
      code: devicesInGroupErrorCode,
      message: devicesInGroupErrorMessage,
    },
  } = useSelector((state) => state.surveillance.devicesInGroup);

  // ===
  useEffect(() => {
    SystemEventsHandler.onInfo({
      info:
        'useGroupModel()->LOADING_DEVICES_IN_GROUP: ' + loadingDevicesInGroup,
    });
  }, [loadingDevicesInGroup]);
  // ===

  const focusChangedCallback = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'useGroupModel()->WILL_UPDATE_GROUP_DATA',
    });
  }, []);
  useFocusEffect(focusChangedCallback);

  return {
    data: {
      currentGroupName,
      currentGroupPassword,
      currentDeviceName,
      loggedIn,
    },
    setters: {},
    navigation,
    dispatch,
  };
};

export default useGroupModel;
