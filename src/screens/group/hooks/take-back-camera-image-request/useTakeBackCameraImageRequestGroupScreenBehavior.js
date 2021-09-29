import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import GroupLocalActions from '../../store/GroupLocalActions';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';

const useTakeBackCameraImageRequestGroupScreenBehavior = ({localDispatch}) => {
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
      localDispatch(GroupLocalActions.actions.clearRequestStatusDialogData());
      localDispatch(
        GroupLocalActions.actions.setRequestStatusDialogVisibility({
          visible: true,
        }),
      );
    }
  }, [screenFocused, takeBackCameraImageRequestInProgress, localDispatch]);

  useEffect(() => {
    if (!screenFocused) {
      return;
    }

    if (takeBackCameraImageRequestCompleted) {
      localDispatch(
        GroupLocalActions.actions.setRequestStatusDialogResponseData({
          data: selectedDeviceBackCameraImage,
          canViewResponse: true,
          responseViewerCallback: () => {
            SystemEventsHandler.onInfo({info: 'hook->responseViewerCallback'});

            localDispatch(
              GroupLocalActions.actions.setRequestStatusDialogVisibility({
                visible: false,
              }),
            );

            localDispatch(
              GroupLocalActions.actions.openImageViewer({
                image: selectedDeviceBackCameraImage,
              }),
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
