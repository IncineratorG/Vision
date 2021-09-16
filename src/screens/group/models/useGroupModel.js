import {useState, useCallback, useEffect, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppRoutes from '../../../data/common/routes/AppRoutes';

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

  const focusChangedCallback = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'useGroupModel()->WILL_UPDATE_GROUP_DATA',
    });
  }, []);
  useFocusEffect(focusChangedCallback);

  // ===
  useEffect(() => {
    SystemEventsHandler.onInfo({
      info:
        'useGroupModel()->LOADING_DEVICES_IN_GROUP: ' + loadingDevicesInGroup,
    });
  }, [loadingDevicesInGroup]);
  // ===

  useEffect(() => {
    if (!loggedIn) {
      navigation.navigate(AppRoutes.Authorisation);
    }
  }, [loggedIn, navigation]);

  useEffect(() => {
    SystemEventsHandler.onInfo({
      info: 'useGroupModel(): ' + devicesInGroupArray.length,
    });
    for (let i = 0; i < devicesInGroupArray.length; ++i) {
      SystemEventsHandler.onInfo({
        info: 'useGroupModel(): ' + JSON.stringify(devicesInGroupArray[i]),
      });
    }
  }, [devicesInGroupArray]);

  return {
    data: {
      currentGroupName,
      currentGroupPassword,
      currentDeviceName,
      loggedIn,
      devicesInGroupArray,
    },
    setters: {},
    navigation,
    dispatch,
  };
};

export default useGroupModel;
