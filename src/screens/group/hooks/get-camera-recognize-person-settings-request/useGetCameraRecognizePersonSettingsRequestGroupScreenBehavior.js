import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import useTranslation from '../../../../utils/common/localization';
import GroupLocalActions from '../../store/GroupLocalActions';
import AppActions from '../../../../store/actions/AppActions';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';

const useGetCameraRecognizePersonSettingsRequestGroupScreenBehavior = ({
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
    inProgress: getCameraRecognizePersonSettingsRequestInProgress,
    completed: getCameraRecognizePersonSettingsRequestCompleted,
    response: {
      payload: {image: getCameraRecognizePersonSettingsRequestImage},
    },
    error: {
      hasError: getCameraRecognizePersonSettingsRequestHasError,
      code: getCameraRecognizePersonSettingsRequestErrorCode,
    },
  } = useSelector(
    (state) =>
      state.surveillanceGetCameraRecognizePersonSettingsRequest
        .getCameraRecognizePersonSettingsRequest,
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

    if (getCameraRecognizePersonSettingsRequestInProgress) {
      SystemEventsHandler.onInfo({
        info:
          '===> useGetCameraRecognizePersonSettingsRequestGroupScreenBehavior->REQUEST_IN_PROGRESS: ' +
          getCameraRecognizePersonSettingsRequestInProgress,
      });

      localDispatch(
        GroupLocalActions.actions.setCurrentRequestStatusDialogData({
          visible: true,
          statusText: t(
            'CurrentRequestStatusDialog_getCameraRecognizePersonSettingsRequestInProgressStatusText',
          ),
          leftButtonVisible: false,
          leftButtonText: '',
          leftButtonPressHandler: null,
          rightButtonVisible: true,
          rightButtonText: t(
            'CurrentRequestStatusDialog_getCameraRecognizePersonSettingsRequestInProgressRightButtonText',
          ),
          rightButtonPressHandler: () => {
            dispatch(
              AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.actions.cancelSendGetCameraRecognizePersonSettingsRequest(),
            );
            localDispatch(
              GroupLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
          },
          onCancel: () => {
            dispatch(
              AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.actions.cancelSendGetCameraRecognizePersonSettingsRequest(),
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
    getCameraRecognizePersonSettingsRequestInProgress,
    localDispatch,
    dispatch,
    t,
  ]);

  useEffect(() => {
    if (!screenFocused) {
      return;
    }

    if (getCameraRecognizePersonSettingsRequestCompleted) {
      dispatch(
        AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.actions.clear(),
      );

      localDispatch(
        GroupLocalActions.actions.clearCurrentRequestStatusDialogData(),
      );
      localDispatch(
        GroupLocalActions.actions.setDeviceRequestsDialogVisibility({
          visible: true,
        }),
      );
    }
  }, [
    screenFocused,
    getCameraRecognizePersonSettingsRequestCompleted,
    dispatch,
    localDispatch,
  ]);

  useEffect(() => {
    if (!screenFocused) {
      return;
    }

    if (getCameraRecognizePersonSettingsRequestHasError) {
      SystemEventsHandler.onInfo({
        info:
          'useGetCameraRecognizePersonSettingsRequestGroupScreenBehavior()->ERROR: ' +
          getCameraRecognizePersonSettingsRequestErrorCode,
      });

      localDispatch(
        GroupLocalActions.actions.setCurrentRequestStatusDialogData({
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
              AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.actions.clear(),
            );
            localDispatch(
              GroupLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
          },
          onCancel: () => {
            dispatch(
              AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.actions.clear(),
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
    getCameraRecognizePersonSettingsRequestHasError,
    getCameraRecognizePersonSettingsRequestErrorCode,
    localDispatch,
    dispatch,
    t,
  ]);
};

export default useGetCameraRecognizePersonSettingsRequestGroupScreenBehavior;
