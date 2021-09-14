import {useCallback, useEffect, useReducer} from 'react';
import {useNavigation} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import mainLocalReducer from '../store/mainLocalReducer';
import mainLocalState from '../store/mainLocalState';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import MainLocalActions from '../store/MainLocalActions';

const useMainModel = () => {
  const navigation = useNavigation();

  const dispatch = useDispatch();

  const [localState, localDispatch] = useReducer(
    mainLocalReducer,
    mainLocalState,
  );

  const {
    registerDeviceInGroupDialog: {
      groupName: registerDeviceInGroupDialogGroupName,
      groupPassword: registerDeviceInGroupDialogGroupPassword,
      deviceName: registerDeviceInGroupDialogDeviceName,
    },
  } = localState;

  const {
    inProgress: registerDeviceInGroupInProgress,
    error: {
      hasError: registerDeviceInGroupHasError,
      code: registerDeviceInGroupErrorCode,
      message: registerDeviceInGroupErrorMessage,
    },
  } = useSelector((store) => store.auth.registerDeviceInGroup);
  const {
    inProgress: createGroupWithDeviceInProgress,
    error: {
      hasError: createGroupWithDeviceHasError,
      code: createGroupWithDeviceErrorCode,
      message: createGroupWithDeviceErrorMessage,
    },
  } = useSelector((store) => store.auth.createGroupWithDevice);

  const {
    inProgress: loginDeviceInGroupInProgress,
    error: {
      hasError: loginDeviceInGroupHasError,
      code: loginDeviceInGroupErrorCode,
      message: loginDeviceInGroupErrorMessage,
    },
  } = useSelector((store) => store.auth.loginDeviceInGroup);

  useEffect(() => {
    if (registerDeviceInGroupErrorCode) {
      if (registerDeviceInGroupErrorCode === '8') {
        localDispatch(
          MainLocalActions.actions.setNeedCreateGroupDialogData({
            groupName: registerDeviceInGroupDialogGroupName,
            groupPassword: registerDeviceInGroupDialogGroupPassword,
            deviceName: registerDeviceInGroupDialogDeviceName,
          }),
        );
        localDispatch(
          MainLocalActions.actions.setNeedCreateGroupDialogVisibility({
            visible: true,
          }),
        );
      }
    }
  }, [
    registerDeviceInGroupErrorCode,
    registerDeviceInGroupDialogGroupName,
    registerDeviceInGroupDialogGroupPassword,
    registerDeviceInGroupDialogDeviceName,
    localDispatch,
  ]);

  return {
    data: {
      localState,
      registerDeviceInGroupInProgress,
      createGroupWithDeviceInProgress,
      loginDeviceInGroupInProgress,
    },
    setters: {},
    dispatch,
    localDispatch,
    navigation,
  };
};

export default useMainModel;
