import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import GroupLocalActions from '../../store/GroupLocalActions';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import useTranslation from '../../../../utils/common/localization';
import AppActions from '../../../../store/actions/AppActions';

const useTakeFrontCameraImageRequestGroupScreenBehavior = ({
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
        GroupLocalActions.actions.setCurrentRequestStatusDialogData({
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
              GroupLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
          },
          onCancel: () => {
            dispatch(
              AppActions.surveillanceTakeFrontCameraImageRequest.actions.cancelSendTakeFrontCameraImageRequest(),
            );
            localDispatch(
              GroupLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
          },
        }),
      );
    }

    // if (takeFrontCameraImageRequestInProgress) {
    //   localDispatch(GroupLocalActions.actions.clearRequestStatusDialogData());
    //   localDispatch(
    //     GroupLocalActions.actions.setRequestStatusDialogVisibility({
    //       visible: true,
    //     }),
    //   );
    // }
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
        GroupLocalActions.actions.setCurrentRequestStatusDialogData({
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
              GroupLocalActions.actions.openImageViewer({
                image: selectedDeviceFrontCameraImage,
              }),
            );
            localDispatch(
              GroupLocalActions.actions.clearCurrentRequestStatusDialogData(),
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
              GroupLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
            dispatch(
              AppActions.surveillanceTakeFrontCameraImageRequest.actions.clear(),
            );
          },
          onCancel: () => {
            localDispatch(
              GroupLocalActions.actions.clearCurrentRequestStatusDialogData(),
            );
            dispatch(
              AppActions.surveillanceTakeFrontCameraImageRequest.actions.clear(),
            );
          },
        }),
      );
    }

    // if (takeFrontCameraImageRequestCompleted) {
    //   localDispatch(
    //     GroupLocalActions.actions.setRequestStatusDialogResponseData({
    //       data: selectedDeviceFrontCameraImage,
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
    //             image: selectedDeviceFrontCameraImage,
    //           }),
    //         );
    //       },
    //     }),
    //   );
    // }
  }, [
    screenFocused,
    takeFrontCameraImageRequestCompleted,
    selectedDeviceFrontCameraImage,
    localDispatch,
    t,
    dispatch,
  ]);

  return {
    inProgress: takeFrontCameraImageRequestInProgress,
    completed: takeFrontCameraImageRequestCompleted,
    response: {
      payload: {image: selectedDeviceFrontCameraImage},
    },
    error: {
      hasError: takeFrontCameraImageRequestHasError,
      code: takeFrontCameraImageRequestErrorCode,
    },
  };
};

export default useTakeFrontCameraImageRequestGroupScreenBehavior;
