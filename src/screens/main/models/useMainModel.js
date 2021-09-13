import {useCallback, useReducer} from 'react';
import {useNavigation} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import mainLocalReducer from '../store/mainLocalReducer';
import mainLocalState from '../store/mainLocalState';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';

const useMainModel = () => {
  const navigation = useNavigation();

  const dispatch = useDispatch();

  const [localState, localDispatch] = useReducer(
    mainLocalReducer,
    mainLocalState,
  );

  const registerDeviceInGroupInProgress = useSelector(
    (store) => store.auth.registerDeviceInGroup.inProgress,
  );
  const registerDeviceInGroupHasError = useSelector(
    (store) => store.auth.registerDeviceInGroup.error.hasError,
  );
  const registerDeviceInGroupErrorCode = useSelector(
    (store) => store.auth.registerDeviceInGroup.error.code,
  );
  const registerDeviceInGroupErrorMessage = useSelector(
    (store) => store.auth.registerDeviceInGroup.error.message,
  );
  SystemEventsHandler.onInfo({
    info: 'useMainModel(): ' + registerDeviceInGroupInProgress,
  });

  return {
    data: {localState},
    setters: {},
    dispatch,
    localDispatch,
    navigation,
  };
};

export default useMainModel;
