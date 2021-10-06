import {useCallback} from 'react';
import GroupLocalActions from '../../store/GroupLocalActions';

const useImageViewerGroupController = (model) => {
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

  const imageViewerCloseHandler = useCallback(() => {
    localDispatch(GroupLocalActions.actions.closeImageViewer());
  }, [localDispatch]);

  return {
    imageViewerCloseHandler,
  };
};

export default useImageViewerGroupController;
