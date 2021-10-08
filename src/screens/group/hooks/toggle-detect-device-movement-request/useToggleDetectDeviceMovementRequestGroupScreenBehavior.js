import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import GroupLocalActions from '../../store/GroupLocalActions';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import useTranslation from '../../../../utils/common/localization';
import AppActions from '../../../../store/actions/AppActions';

const useToggleDetectDeviceMovementRequestGroupScreenBehavior = ({
  localDispatch,
  dispatch,
  currentGroupName,
  currentGroupPassword,
  currentDeviceName,
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
        GroupLocalActions.actions.setCurrentRequestStatusDialogData({
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
              GroupLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
          },
          onCancel: () => {
            dispatch(
              AppActions.surveillanceToggleDetectDeviceMovementRequest.actions.cancelSendToggleDetectDeviceMovementRequest(),
            );
            localDispatch(
              GroupLocalActions.actions.clearCurrentRequestStatusDialogData(),
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
      SystemEventsHandler.onInfo({
        info:
          'useToggleDetectDeviceMovementRequestGroupScreenBehavior()->COMPLETED: ' +
          detectDeviceMovementServiceRunning,
      });

      dispatch(
        AppActions.surveillanceCommon.actions.getDevicesInGroup({
          groupName: currentGroupName,
          groupPassword: currentGroupPassword,
          deviceName: currentDeviceName,
        }),
      );
      localDispatch(
        GroupLocalActions.actions.clearCurrentRequestStatusDialogData(),
      );
    }
  }, [
    screenFocused,
    toggleDetectDeviceMovementRequestCompleted,
    detectDeviceMovementServiceRunning,
    currentGroupName,
    currentGroupPassword,
    currentDeviceName,
    localDispatch,
    dispatch,
    t,
  ]);

  return {
    inProgress: toggleDetectDeviceMovementRequestInProgress,
    completed: toggleDetectDeviceMovementRequestCompleted,
    response: {
      payload: {detectDeviceMovementServiceRunning},
    },
    error: {
      hasError: toggleDetectDeviceMovementRequestHasError,
      code: toggleDetectDeviceMovementRequestErrorCode,
    },
  };
};

export default useToggleDetectDeviceMovementRequestGroupScreenBehavior;
