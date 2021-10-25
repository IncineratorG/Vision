import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import GroupLocalActions from '../../store/GroupLocalActions';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import useTranslation from '../../../../utils/common/localization';
import AppActions from '../../../../store/actions/AppActions';

const useTakeBackCameraImageRequestGroupScreenBehavior = ({
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
        GroupLocalActions.actions.setCurrentRequestStatusDialogData({
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
              GroupLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
          },
          onCancel: () => {
            dispatch(
              AppActions.surveillanceTakeBackCameraImageRequest.actions.cancelSendTakeBackCameraImageRequest(),
            );
            localDispatch(
              GroupLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
          },
        }),
      );
    }

    // if (takeBackCameraImageRequestInProgress) {
    //   localDispatch(GroupLocalActions.actions.clearRequestStatusDialogData());
    //   localDispatch(
    //     GroupLocalActions.actions.setRequestStatusDialogVisibility({
    //       visible: true,
    //     }),
    //   );
    // }
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
        GroupLocalActions.actions.setCurrentRequestStatusDialogData({
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
              GroupLocalActions.actions.openImageViewer({
                image: selectedDeviceBackCameraImage,
              }),
            );
            localDispatch(
              GroupLocalActions.actions.clearCurrentRequestStatusDialogData(),
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
              GroupLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
            dispatch(
              AppActions.surveillanceTakeBackCameraImageRequest.actions.clear(),
            );
          },
          onCancel: () => {
            localDispatch(
              GroupLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
            dispatch(
              AppActions.surveillanceTakeBackCameraImageRequest.actions.clear(),
            );
          },
        }),
      );
    }

    // if (takeBackCameraImageRequestCompleted) {
    //   localDispatch(
    //     GroupLocalActions.actions.setRequestStatusDialogResponseData({
    //       data: selectedDeviceBackCameraImage,
    //       canViewResponse: true,
    //       responseViewerCallback: () => {
    //         SystemEventsHandler.onInfo({info: 'hook->responseViewerCallback'});
    //
    //         localDispatch(
    //           GroupLocalActions.actions.setRequestStatusDialogVisibility({
    //             visible: false,
    //           }),
    //         );
    //
    //         localDispatch(
    //           GroupLocalActions.actions.openImageViewer({
    //             image: selectedDeviceBackCameraImage,
    //           }),
    //         );
    //       },
    //     }),
    //   );
    // }
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
          'useTakeBackCameraImageRequestGroupScreenBehavior()->ERROR: ' +
          takeBackCameraImageRequestErrorCode,
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
              AppActions.surveillanceTakeBackCameraImageRequest.actions.clear(),
            );
            localDispatch(
              GroupLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
          },
          onCancel: () => {
            dispatch(
              AppActions.surveillanceTakeBackCameraImageRequest.actions.clear(),
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
    takeBackCameraImageRequestHasError,
    takeBackCameraImageRequestErrorCode,
    dispatch,
    localDispatch,
    t,
  ]);

  return {
    inProgress: takeBackCameraImageRequestInProgress,
    completed: takeBackCameraImageRequestCompleted,
    response: {
      payload: {image: selectedDeviceBackCameraImage},
    },
    error: {
      hasError: takeBackCameraImageRequestHasError,
      code: takeBackCameraImageRequestErrorCode,
    },
  };
};

export default useTakeBackCameraImageRequestGroupScreenBehavior;
