import {useState, useCallback, useEffect} from 'react';
import {useFocusEffect} from '@react-navigation/core';
import {useSelector} from 'react-redux';
import useTranslation from '../../../../utils/common/localization';
import AppActions from '../../../../store/actions/AppActions';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import SelectedDeviceLocalActions from '../../store/SelectedDeviceLocalActions';

const useToggleRecognizePersonRequestSelectedDeviceScreenBehavior = ({
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
    inProgress: toggleRecognizePersonRequestInProgress,
    completed: toggleRecognizePersonRequestCompleted,
    response: {
      payload: {
        frontCameraRecognizePersonServiceRunning,
        backCameraRecognizePersonServiceRunning,
      },
    },
    error: {
      hasError: toggleRecognizePersonRequestHasError,
      code: toggleRecognizePersonRequestErrorCode,
    },
  } = useSelector(
    (state) =>
      state.surveillanceToggleRecognizePersonRequest
        .toggleRecognizePersonRequest,
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

    if (toggleRecognizePersonRequestInProgress) {
      SystemEventsHandler.onInfo({
        info:
          '===> useToggleRecognizePersonRequestSelectedDeviceScreenBehavior->REQUEST_IN_PROGRESS: ' +
          toggleRecognizePersonRequestInProgress,
      });

      localDispatch(
        SelectedDeviceLocalActions.actions.setCurrentRequestStatusDialogData({
          visible: true,
          statusText: t(
            'CurrentRequestStatusDialog_toggleRecognizePersonRequestInProgressStatusText',
          ),
          leftButtonVisible: false,
          leftButtonText: '',
          leftButtonPressHandler: null,
          rightButtonVisible: true,
          rightButtonText: t(
            'CurrentRequestStatusDialog_toggleRecognizePersonRequestInProgressRightButtonText',
          ),
          rightButtonPressHandler: () => {
            dispatch(
              AppActions.surveillanceToggleRecognizePersonRequest.actions.cancelSendToggleRecognizePersonRequest(),
            );
            localDispatch(
              SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
          },
          onCancel: () => {
            dispatch(
              AppActions.surveillanceToggleRecognizePersonRequest.actions.cancelSendToggleRecognizePersonRequest(),
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
    toggleRecognizePersonRequestInProgress,
    localDispatch,
    dispatch,
    t,
  ]);

  useEffect(() => {
    if (!screenFocused) {
      return;
    }

    if (toggleRecognizePersonRequestCompleted) {
      dispatch(
        AppActions.surveillanceToggleRecognizePersonRequest.actions.clear(),
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
        frontCameraRecognizePersonServiceRunning,
        backCameraRecognizePersonServiceRunning,
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
    toggleRecognizePersonRequestCompleted,
    frontCameraRecognizePersonServiceRunning,
    backCameraRecognizePersonServiceRunning,
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

    if (toggleRecognizePersonRequestHasError) {
      SystemEventsHandler.onInfo({
        info:
          'useToggleRecognizePersonRequestSelectedDeviceScreenBehavior()->ERROR: ' +
          toggleRecognizePersonRequestErrorCode,
      });

      // dispatch(
      //   AppActions.surveillanceToggleRecognizePersonRequest.actions.clear(),
      // );

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
              AppActions.surveillanceToggleRecognizePersonRequest.actions.clear(),
            );
            localDispatch(
              SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
          },
          onCancel: () => {
            dispatch(
              AppActions.surveillanceToggleRecognizePersonRequest.actions.clear(),
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
    toggleRecognizePersonRequestHasError,
    toggleRecognizePersonRequestErrorCode,
    currentGroupName,
    currentGroupPassword,
    currentDeviceName,
    localDispatch,
    dispatch,
    t,
  ]);
};

export default useToggleRecognizePersonRequestSelectedDeviceScreenBehavior;
