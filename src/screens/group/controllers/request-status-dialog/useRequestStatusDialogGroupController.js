import {useCallback} from 'react';
import GroupLocalActions from '../../store/GroupLocalActions';

const useRequestStatusDialogGroupController = (model) => {
  const {
    data: {
      currentGroupName,
      currentGroupPassword,
      currentDeviceName,
      loggedIn,
      localState: {
        deviceRequestsDialog: {
          visible: deviceRequestsDialogVisible,
          selectedDeviceName: deviceRequestsDialogSelectedDeviceName,
        },
      },
    },
    navigation,
    dispatch,
    localDispatch,
  } = model;

  const requestStatusDialogCancelHandler = useCallback(() => {
    localDispatch(
      GroupLocalActions.actions.setRequestStatusDialogVisibility({
        visible: false,
      }),
    );
  }, [localDispatch]);

  return {
    requestStatusDialogCancelHandler,
  };
};

export default useRequestStatusDialogGroupController;
