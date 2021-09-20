import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppRoutes from '../../../data/common/routes/AppRoutes';
import AppActions from '../../../store/actions/AppActions';
import groupLocalReducer from '../store/groupLocalReducer';
import groupLocalState from '../store/groupLocalState';
import useGainFocus from '../../../utils/common/hooks/useGainFocus';

const useGroupModel = () => {
  // ===
  useGainFocus();
  // ===

  const navigation = useNavigation();

  const dispatch = useDispatch();

  const [localState, localDispatch] = useReducer(
    groupLocalReducer,
    groupLocalState,
  );

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

  const {running: serviceRunning} = useSelector(
    (state) => state.surveillance.service,
  );

  const focusChangedCallback = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'useGroupModel()->WILL_UPDATE_GROUP_DATA',
    });

    dispatch(
      AppActions.surveillance.actions.getDevicesInGroup({
        groupName: currentGroupName,
        groupPassword: currentGroupPassword,
        deviceName: currentDeviceName,
      }),
    );
  }, [currentGroupName, currentGroupPassword, currentDeviceName, dispatch]);
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
    if (serviceRunning) {
      navigation.navigate(AppRoutes.Service);
    }
  }, [serviceRunning, navigation]);

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
      localState,
      currentGroupName,
      currentGroupPassword,
      currentDeviceName,
      loggedIn,
      loadingDevicesInGroup,
      devicesInGroupArray,
    },
    setters: {},
    dispatch,
    localDispatch,
    navigation,
  };
};

export default useGroupModel;
