import {useState, useCallback, useEffect} from 'react';
import {useFocusEffect} from '@react-navigation/core';
import {useSelector} from 'react-redux';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import useTranslation from '../../../../utils/common/localization';
import AppActions from '../../../../store/actions/AppActions';
import SelectedDeviceLocalActions from '../../store/SelectedDeviceLocalActions';

const useTakeBackCameraImageRequestSelectedDeviceScreenBehavior = ({
  localDispatch,
  dispatch,
}) => {
  const {t} = useTranslation();

  const [screenFocused, setScreenFocused] = useState(false);

  const {
    inProgress: takeBackCameraImageRequestInProgress,
    completed: takeBackCameraImageRequestCompleted,
    response: {
      payload: {image: selectedDeviceBackCameraImage},
    },
    error: {
      hasError: takeBackCameraImageRequestHasError,
      code: takeBackCameraImageRequestErrorCode,
    },
  } = useSelector(
    (state) =>
      state.surveillanceTakeBackCameraImageRequest.takeBackCameraImageRequest,
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

    if (takeBackCameraImageRequestInProgress) {
      localDispatch(
        SelectedDeviceLocalActions.actions.setCurrentRequestStatusDialogData({
          visible: true,
          statusText: t(
            'CurrentRequestStatusDialog_takeBackCameraImageRequestInProgressStatusText',
          ),
          leftButtonVisible: false,
          leftButtonText: '',
          leftButtonPressHandler: null,
          rightButtonVisible: true,
          rightButtonText: t(
            'CurrentRequestStatusDialog_takeBackCameraImageRequestInProgressRightButtonText',
          ),
          rightButtonPressHandler: () => {
            dispatch(
              AppActions.surveillanceTakeBackCameraImageRequest.actions.cancelSendTakeBackCameraImageRequest(),
            );
            localDispatch(
              SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
          },
          onCancel: () => {
            dispatch(
              AppActions.surveillanceTakeBackCameraImageRequest.actions.cancelSendTakeBackCameraImageRequest(),
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
    takeBackCameraImageRequestInProgress,
    localDispatch,
    dispatch,
    t,
  ]);

  useEffect(() => {
    if (!screenFocused) {
      return;
    }

    if (takeBackCameraImageRequestCompleted) {
      localDispatch(
        SelectedDeviceLocalActions.actions.setCurrentRequestStatusDialogData({
          visible: true,
          statusText: t(
            'CurrentRequestStatusDialog_takeBackCameraImageRequestCompletedStatusText',
          ),
          leftButtonVisible: true,
          leftButtonText: t(
            'CurrentRequestStatusDialog_takeBackCameraImageRequestCompletedLeftButtonText',
          ),
          leftButtonPressHandler: () => {
            localDispatch(
              SelectedDeviceLocalActions.actions.openImageViewer({
                image: selectedDeviceBackCameraImage,
              }),
            );
            localDispatch(
              SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
            dispatch(
              AppActions.surveillanceTakeBackCameraImageRequest.actions.clear(),
            );
          },
          rightButtonVisible: true,
          rightButtonText: t(
            'CurrentRequestStatusDialog_takeBackCameraImageRequestCompletedRightButtonText',
          ),
          rightButtonPressHandler: () => {
            localDispatch(
              SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
            dispatch(
              AppActions.surveillanceTakeBackCameraImageRequest.actions.clear(),
            );
          },
          onCancel: () => {
            localDispatch(
              SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
            dispatch(
              AppActions.surveillanceTakeBackCameraImageRequest.actions.clear(),
            );
          },
        }),
      );
    }
  }, [
    screenFocused,
    takeBackCameraImageRequestCompleted,
    selectedDeviceBackCameraImage,
    localDispatch,
    dispatch,
    t,
  ]);

  useEffect(() => {
    if (!screenFocused) {
      return;
    }

    if (takeBackCameraImageRequestHasError) {
      SystemEventsHandler.onInfo({
        info:
          'useTakeBackCameraImageRequestSelectedDeviceScreenBehavior()->ERROR: ' +
          takeBackCameraImageRequestErrorCode,
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
              AppActions.surveillanceTakeBackCameraImageRequest.actions.clear(),
            );
            localDispatch(
              SelectedDeviceLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
          },
          onCancel: () => {
            dispatch(
              AppActions.surveillanceTakeBackCameraImageRequest.actions.clear(),
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
    takeBackCameraImageRequestHasError,
    takeBackCameraImageRequestErrorCode,
    dispatch,
    localDispatch,
    t,
  ]);

  // return {
  //   inProgress: takeBackCameraImageRequestInProgress,
  //   completed: takeBackCameraImageRequestCompleted,
  //   response: {
  //     payload: {image: selectedDeviceBackCameraImage},
  //   },
  //   error: {
  //     hasError: takeBackCameraImageRequestHasError,
  //     code: takeBackCameraImageRequestErrorCode,
  //   },
  // };
};

export default useTakeBackCameraImageRequestSelectedDeviceScreenBehavior;
