import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import GroupLocalActions from '../../store/GroupLocalActions';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import useTranslation from '../../../../utils/common/localization';

const useToggleDetectDeviceMovementRequestGroupScreenBehavior = ({
  localDispatch,
  dispatch,
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
      SystemEventsHandler.onInfo({
        info: 'useToggleDetectDeviceMovementRequestGroupScreenBehavior()->IN_PROGRESS',
      });

      // localDispatch(
      //     GroupLocalActions
      // );
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
    }
  }, [
    screenFocused,
    toggleDetectDeviceMovementRequestCompleted,
    detectDeviceMovementServiceRunning,
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
