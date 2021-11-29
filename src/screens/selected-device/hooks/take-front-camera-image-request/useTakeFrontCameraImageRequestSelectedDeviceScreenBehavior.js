import {useState, useCallback, useEffect} from 'react';
import {useFocusEffect} from '@react-navigation/core';
import {useSelector} from 'react-redux';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import useTranslation from '../../../../utils/common/localization';
import AppActions from '../../../../store/actions/AppActions';
import SelectedDeviceLocalActions from '../../store/SelectedDeviceLocalActions';

const useTakeFrontCameraImageRequestSelectedDeviceScreenBehavior = ({
  localDispatch,
  dispatch,
}) => {
  const {t} = useTranslation();

  const [screenFocused, setScreenFocused] = useState(false);

  const {
    inProgress: takeFrontCameraImageRequestInProgress,
    completed: takeFrontCameraImageRequestCompleted,
    response: {
      payload: {image: selectedDeviceFrontCameraImage},
    },
    error: {
      hasError: takeFrontCameraImageRequestHasError,
      code: takeFrontCameraImageRequestErrorCode,
    },
  } = useSelector(
    (state) =>
      state.surveillanceTakeFrontCameraImageRequest.takeFrontCameraImageRequest,
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

    if (takeFrontCameraImageRequestInProgress) {
      localDispatch(
        SelectedDeviceLocalActions.actions.setCurrentRequestStatusDialogData({
          visible: true,
          statusText: t(
            'CurrentRequestStatusDialog_takeFrontCameraImageRequestInProgressStatusText',
          ),
          leftButtonVisible: false,
          leftButtonText: '',
          leftButtonPressHandler: null,
          rightButtonVisible: true,
          rightButtonText: t(
            'CurrentRequestStatusDialog_takeFrontCameraImageRequestInProgressRightButtonText',
          ),
          rightButtonPressHandler: () => {
            dispatch(
              AppActions.surveillanceTakeFrontCameraImageRequest.actions.cancelSendTakeFrontCameraImageRequest(),
            );
            localDispatch(
              SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
          },
          onCancel: () => {
            dispatch(
              AppActions.surveillanceTakeFrontCameraImageRequest.actions.cancelSendTakeFrontCameraImageRequest(),
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
    takeFrontCameraImageRequestInProgress,
    localDispatch,
    t,
    dispatch,
  ]);

  useEffect(() => {
    if (!screenFocused) {
      return;
    }

    if (takeFrontCameraImageRequestCompleted) {
      localDispatch(
        SelectedDeviceLocalActions.actions.setCurrentRequestStatusDialogData({
          visible: true,
          statusText: t(
            'CurrentRequestStatusDialog_takeFrontCameraImageRequestCompletedStatusText',
          ),
          leftButtonVisible: true,
          leftButtonText: t(
            'CurrentRequestStatusDialog_takeFrontCameraImageRequestCompletedLeftButtonText',
          ),
          leftButtonPressHandler: () => {
            localDispatch(
              SelectedDeviceLocalActions.actions.openImageViewer({
                image: selectedDeviceFrontCameraImage,
              }),
            );
            localDispatch(
              SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
            dispatch(
              AppActions.surveillanceTakeFrontCameraImageRequest.actions.clear(),
            );
          },
          rightButtonVisible: true,
          rightButtonText: t(
            'CurrentRequestStatusDialog_takeFrontCameraImageRequestCompletedRightButtonText',
          ),
          rightButtonPressHandler: () => {
            localDispatch(
              SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
            dispatch(
              AppActions.surveillanceTakeFrontCameraImageRequest.actions.clear(),
            );
          },
          onCancel: () => {
            localDispatch(
              SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
            dispatch(
              AppActions.surveillanceTakeFrontCameraImageRequest.actions.clear(),
            );
          },
        }),
      );
    }
  }, [
    screenFocused,
    takeFrontCameraImageRequestCompleted,
    selectedDeviceFrontCameraImage,
    localDispatch,
    t,
    dispatch,
  ]);

  useEffect(() => {
    if (!screenFocused) {
      return;
    }

    if (takeFrontCameraImageRequestHasError) {
      SystemEventsHandler.onInfo({
        info:
          'useTakeFrontCameraImageRequestSelectedDeviceScreenBehavior()->ERROR: ' +
          takeFrontCameraImageRequestErrorCode,
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
              AppActions.surveillanceTakeFrontCameraImageRequest.actions.clear(),
            );
            localDispatch(
              SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
          },
          onCancel: () => {
            dispatch(
              AppActions.surveillanceTakeFrontCameraImageRequest.actions.clear(),
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
    takeFrontCameraImageRequestHasError,
    takeFrontCameraImageRequestErrorCode,
    localDispatch,
    dispatch,
    t,
  ]);

  // return {
  //   inProgress: takeFrontCameraImageRequestInProgress,
  //   completed: takeFrontCameraImageRequestCompleted,
  //   response: {
  //     payload: {image: selectedDeviceFrontCameraImage},
  //   },
  //   error: {
  //     hasError: takeFrontCameraImageRequestHasError,
  //     code: takeFrontCameraImageRequestErrorCode,
  //   },
  // };
};

export default useTakeFrontCameraImageRequestSelectedDeviceScreenBehavior;
