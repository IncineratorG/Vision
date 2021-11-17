import {useState, useCallback, useEffect} from 'react';
import {useFocusEffect} from '@react-navigation/core';
import {useSelector} from 'react-redux';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import useTranslation from '../../../../utils/common/localization';
import AppActions from '../../../../store/actions/AppActions';
import SelectedDeviceLocalActions from '../../store/SelectedDeviceLocalActions';

const useToggleDetectDeviceMovementRequestSelectedDeviceScreenBehavior = ({
  localDispatch,
  dispatch,
  currentGroupName,
  currentGroupPassword,
  currentDeviceName,
  selectedDevice,
}) => {
  const {t} = useTranslation();

  const [screenFocused, setScreenFocused] = useState(false);

  const {
    inProgress: toggleDetectDeviceMovementRequestInProgress,
    completed: toggleDetectDeviceMovementRequestCompleted,
    response: {
      payload: {detectDeviceMovementServiceRunning},
    },
    error: {
      hasError: toggleDetectDeviceMovementRequestHasError,
      code: toggleDetectDeviceMovementRequestErrorCode,
    },
  } = useSelector(
    (state) =>
      state.surveillanceToggleDetectDeviceMovementRequest
        .toggleDetectDeviceMovementRequest,
  );

  const focusChangedCallback = useCallback(() => {
    setScreenFocused(true);
    return () => {
      setScreenFocused(false);
    };
  }, []);
  useFocusEffect(focusChangedCallback);

  useEffect(() => {
    if (!screenFocused) {
      return;
    }

    if (toggleDetectDeviceMovementRequestInProgress) {
      localDispatch(
        SelectedDeviceLocalActions.actions.setCurrentRequestStatusDialogData({
          visible: true,
          statusText: t(
            'CurrentRequestStatusDialog_toggleDetectDeviceMovementRequestInProgressStatusText',
          ),
          leftButtonVisible: false,
          leftButtonText: '',
          leftButtonPressHandler: null,
          rightButtonVisible: true,
          rightButtonText: t(
            'CurrentRequestStatusDialog_toggleDetectDeviceMovementRequestInProgressRightButtonText',
          ),
          rightButtonPressHandler: () => {
            dispatch(
              AppActions.surveillanceToggleDetectDeviceMovementRequest.actions.cancelSendToggleDetectDeviceMovementRequest(),
            );
            localDispatch(
              SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
          },
          onCancel: () => {
            dispatch(
              AppActions.surveillanceToggleDetectDeviceMovementRequest.actions.cancelSendToggleDetectDeviceMovementRequest(),
            );
            localDispatch(
              SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
          },
        }),
      );
    }
  }, [
    screenFocused,
    toggleDetectDeviceMovementRequestInProgress,
    localDispatch,
    dispatch,
    t,
  ]);

  useEffect(() => {
    if (!screenFocused) {
      return;
    }

    if (toggleDetectDeviceMovementRequestCompleted) {
      dispatch(
        AppActions.surveillanceToggleDetectDeviceMovementRequest.actions.clear(),
      );
      dispatch(
        AppActions.surveillanceCommon.actions.getDevicesInGroup({
          groupName: currentGroupName,
          groupPassword: currentGroupPassword,
          deviceName: currentDeviceName,
        }),
      );

      const updatedSelectedDevice = {
        ...selectedDevice,
        deviceMovementServiceRunning: detectDeviceMovementServiceRunning,
      };

      localDispatch(
        SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
      );
      localDispatch(
        SelectedDeviceLocalActions.actions.setDeviceData({
          device: updatedSelectedDevice,
        }),
      );
    }
  }, [
    screenFocused,
    toggleDetectDeviceMovementRequestCompleted,
    detectDeviceMovementServiceRunning,
    currentGroupName,
    currentGroupPassword,
    currentDeviceName,
    selectedDevice,
    localDispatch,
    dispatch,
    t,
  ]);

  useEffect(() => {
    if (!screenFocused) {
      return;
    }

    if (toggleDetectDeviceMovementRequestHasError) {
      SystemEventsHandler.onInfo({
        info:
          'useToggleDetectDeviceMovementRequestSelectedDeviceScreenBehavior()->ERROR: ' +
          toggleDetectDeviceMovementRequestErrorCode,
      });

      localDispatch(
        SelectedDeviceLocalActions.actions.setCurrentRequestStatusDialogData({
          visible: true,
          statusText: t('CurrentRequestStatusDialog_generalErrorStatusText'),
          leftButtonVisible: false,
          leftButtonText: '',
          leftButtonPressHandler: null,
          rightButtonVisible: true,
          rightButtonText: t(
            'CurrentRequestStatusDialog_generalErrorRightButtonText',
          ),
          rightButtonPressHandler: () => {
            dispatch(
              AppActions.surveillanceToggleDetectDeviceMovementRequest.actions.clear(),
            );
            localDispatch(
              SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
          },
          onCancel: () => {
            dispatch(
              AppActions.surveillanceToggleDetectDeviceMovementRequest.actions.clear(),
            );
            localDispatch(
              SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
          },
        }),
      );
    }
  }, [
    screenFocused,
    toggleDetectDeviceMovementRequestHasError,
    toggleDetectDeviceMovementRequestErrorCode,
    dispatch,
    localDispatch,
    t,
  ]);

  // return {
  //   inProgress: toggleDetectDeviceMovementRequestInProgress,
  //   completed: toggleDetectDeviceMovementRequestCompleted,
  //   response: {
  //     payload: {detectDeviceMovementServiceRunning},
  //   },
  //   error: {
  //     hasError: toggleDetectDeviceMovementRequestHasError,
  //     code: toggleDetectDeviceMovementRequestErrorCode,
  //   },
  // };
};

export default useToggleDetectDeviceMovementRequestSelectedDeviceScreenBehavior;
