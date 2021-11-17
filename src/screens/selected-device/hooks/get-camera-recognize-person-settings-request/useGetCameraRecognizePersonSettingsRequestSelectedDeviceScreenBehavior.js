import {useState, useCallback, useEffect} from 'react';
import {useFocusEffect} from '@react-navigation/core';
import {useSelector} from 'react-redux';
import useTranslation from '../../../../utils/common/localization';
import AppActions from '../../../../store/actions/AppActions';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import SelectedDeviceLocalActions from '../../store/SelectedDeviceLocalActions';

const useGetCameraRecognizePersonSettingsRequestSelectedDeviceScreenBehavior =
  ({localDispatch, dispatch, selectedDevice}) => {
    const {t} = useTranslation();

    const [screenFocused, setScreenFocused] = useState(false);

    const {
      inProgress: getCameraRecognizePersonSettingsRequestInProgress,
      completed: getCameraRecognizePersonSettingsRequestCompleted,
      response: {
        payload: {
          cameraType: getCameraRecognizePersonSettingsRequestCameraType,
          image: getCameraRecognizePersonSettingsRequestImage,
        },
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
            '===> useGetCameraRecognizePersonSettingsRequestSelectedDeviceScreenBehavior->REQUEST_IN_PROGRESS: ' +
            getCameraRecognizePersonSettingsRequestInProgress,
        });

        localDispatch(
          SelectedDeviceLocalActions.actions.setCurrentRequestStatusDialogData({
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
                SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
              );
            },
            onCancel: () => {
              dispatch(
                AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.actions.cancelSendGetCameraRecognizePersonSettingsRequest(),
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
          SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
        );
        localDispatch(
          SelectedDeviceLocalActions.actions.setCameraRecognizePersonSettingsDialogData(
            {
              visible: true,
              image: getCameraRecognizePersonSettingsRequestImage,
              confirmSettingsButtonPressHandler: ({imageRotationDeg}) => {
                const {deviceName: selectedDeviceName} = selectedDevice;

                SystemEventsHandler.onInfo({
                  info:
                    'useGetCameraRecognizePersonSettingsRequestSelectedDeviceScreenBehavior->confirmSettingsButtonPressHandler(): ' +
                    getCameraRecognizePersonSettingsRequestCameraType +
                    ' - ' +
                    imageRotationDeg +
                    ' - ' +
                    selectedDeviceName,
                });

                localDispatch(
                  SelectedDeviceLocalActions.actions.clearCameraRecognizePersonSettingsDialogData(),
                );

                dispatch(
                  AppActions.surveillanceToggleRecognizePersonRequest.actions.sendToggleRecognizePersonRequest(
                    {
                      receiverDeviceName: selectedDeviceName,
                      cameraType:
                        getCameraRecognizePersonSettingsRequestCameraType,
                      imageRotationDeg,
                    },
                  ),
                );
              },
              cancelButtonPressHandler: () => {
                localDispatch(
                  SelectedDeviceLocalActions.actions.clearCameraRecognizePersonSettingsDialogData(),
                );
              },
              onCancel: () => {
                localDispatch(
                  SelectedDeviceLocalActions.actions.clearCameraRecognizePersonSettingsDialogData(),
                );
              },
            },
          ),
        );
      }
    }, [
      screenFocused,
      selectedDevice,
      getCameraRecognizePersonSettingsRequestCompleted,
      getCameraRecognizePersonSettingsRequestImage,
      getCameraRecognizePersonSettingsRequestCameraType,
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
            'useGetCameraRecognizePersonSettingsRequestSelectedDeviceScreenBehavior()->ERROR: ' +
            getCameraRecognizePersonSettingsRequestErrorCode,
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
                AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.actions.clear(),
              );
              localDispatch(
                SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
              );
            },
            onCancel: () => {
              dispatch(
                AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.actions.clear(),
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
      getCameraRecognizePersonSettingsRequestHasError,
      getCameraRecognizePersonSettingsRequestErrorCode,
      localDispatch,
      dispatch,
      t,
    ]);
  };

export default useGetCameraRecognizePersonSettingsRequestSelectedDeviceScreenBehavior;
