import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import {useNavigation, useRoute, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import AppActions from '../../../store/actions/AppActions';
import selectedDeviceLocalReducer from '../store/selectedDeviceLocalReducer';
import selectedDeviceLocalState from '../store/selectedDeviceLocalState';
import SelectedDeviceLocalActions from '../store/SelectedDeviceLocalActions';

const useSelectedDeviceModel = () => {
  const navigation = useNavigation();
  const route = useRoute();
  const {selectedDevice} = route.params;

  const dispatch = useDispatch();

  const [localState, localDispatch] = useReducer(
    selectedDeviceLocalReducer,
    selectedDeviceLocalState,
  );

  const {
    inProgress: isDeviceAliveRequestInProgress,
    response: {
      payload: {isAlive: selectedDeviceAlive},
    },
  } = useSelector(
    (state) => state.surveillanceIsDeviceAliveRequest.isDeviceAliveRequest,
  );

  const focusChangedCallback = useCallback(() => {
    if (!selectedDevice) {
      return;
    }

    const {deviceName} = selectedDevice;

    localDispatch(
      SelectedDeviceLocalActions.actions.setDeviceData({
        device: selectedDevice,
      }),
    );

    dispatch(
      AppActions.surveillanceIsDeviceAliveRequest.actions.sendIsAliveRequest({
        receiverDeviceName: deviceName,
      }),
    );
  }, [selectedDevice, localDispatch, dispatch]);
  useFocusEffect(focusChangedCallback);

  return {
    data: {
      localState,
      isDeviceAliveRequestInProgress,
      selectedDeviceAlive,
    },
    navigation,
    dispatch,
    localDispatch,
  };
};

export default useSelectedDeviceModel;
