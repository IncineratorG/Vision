import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import GroupLocalActions from '../../store/GroupLocalActions';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';

const useTakeFrontCameraImageRequestGroupScreenBehavior = ({localDispatch}) => {
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
      localDispatch(GroupLocalActions.actions.clearRequestStatusDialogData());
      localDispatch(
        GroupLocalActions.actions.setRequestStatusDialogVisibility({
          visible: true,
        }),
      );
    }
  }, [screenFocused, takeFrontCameraImageRequestInProgress, localDispatch]);

  useEffect(() => {
    if (!screenFocused) {
      return;
    }

    if (takeFrontCameraImageRequestCompleted) {
      localDispatch(
        GroupLocalActions.actions.setRequestStatusDialogResponseData({
          data: selectedDeviceFrontCameraImage,
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
                image: selectedDeviceFrontCameraImage,
              }),
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
