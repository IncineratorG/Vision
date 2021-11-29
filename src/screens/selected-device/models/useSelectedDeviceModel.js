import {useCallback, useReducer} from 'react';
import {useNavigation, useRoute, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import AppActions from '../../../store/actions/AppActions';
import selectedDeviceLocalReducer from '../store/selectedDeviceLocalReducer';
import selectedDeviceLocalState from '../store/selectedDeviceLocalState';
import SelectedDeviceLocalActions from '../store/SelectedDeviceLocalActions';
import useTakeBackCameraImageRequestSelectedDeviceScreenBehavior from '../hooks/take-back-camera-image-request/useTakeBackCameraImageRequestSelectedDeviceScreenBehavior';
import useTakeFrontCameraImageRequestSelectedDeviceScreenBehavior from '../hooks/take-front-camera-image-request/useTakeFrontCameraImageRequestSelectedDeviceScreenBehavior';
import useToggleDetectDeviceMovementRequestSelectedDeviceScreenBehavior from '../hooks/toggle-detect-device-movement-request/useToggleDetectDeviceMovementRequestSelectedDeviceScreenBehavior';
import useGetCameraRecognizePersonSettingsRequestSelectedDeviceScreenBehavior from '../hooks/get-camera-recognize-person-settings-request/useGetCameraRecognizePersonSettingsRequestSelectedDeviceScreenBehavior';
import useToggleRecognizePersonRequestSelectedDeviceScreenBehavior from '../hooks/toggle-recognize-person-request/useToggleRecognizePersonRequestSelectedDeviceScreenBehavior';

const useSelectedDeviceModel = () => {
  const navigation = useNavigation();
  const route = useRoute();
  const {selectedDevice} = route.params;

  const dispatch = useDispatch();

  const [localState, localDispatch] = useReducer(
    selectedDeviceLocalReducer,
    selectedDeviceLocalState,
  );

  const {device} = localState;

  const {
    groupName: currentGroupName,
    groupPassword: currentGroupPassword,
    deviceName: currentDeviceName,
    loggedIn,
  } = useSelector((state) => state.auth.authInfo);
  const {
    inProgress: isDeviceAliveRequestInProgress,
    response: {
      payload: {isAlive: selectedDeviceAlive},
    },
  } = useSelector(
    (state) => state.surveillanceIsDeviceAliveRequest.isDeviceAliveRequest,
  );

  useTakeBackCameraImageRequestSelectedDeviceScreenBehavior({
    localDispatch,
    dispatch,
  });
  useTakeFrontCameraImageRequestSelectedDeviceScreenBehavior({
    localDispatch,
    dispatch,
  });
  useToggleDetectDeviceMovementRequestSelectedDeviceScreenBehavior({
    localDispatch,
    dispatch,
    currentGroupName,
    currentGroupPassword,
    currentDeviceName,
    selectedDevice: device,
  });
  useGetCameraRecognizePersonSettingsRequestSelectedDeviceScreenBehavior({
    localDispatch,
    dispatch,
    selectedDevice: device,
  });
  useToggleRecognizePersonRequestSelectedDeviceScreenBehavior({
    localDispatch,
    dispatch,
    currentGroupName,
    currentGroupPassword,
    currentDeviceName,
    selectedDevice: device,
  });

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
