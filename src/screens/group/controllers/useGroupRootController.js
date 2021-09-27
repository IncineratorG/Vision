import useGroupController from './group/useGroupController';
import useDeviceRequestsDialogGroupController from './device-requests-dialog/useDeviceRequestDialogGroupController';
import useRequestStatusDialogGroupController from './request-status-dialog/useRequestStatusDialogGroupController';
import useImageViewerGroupController from './image-viewer/useImageViewerGroupController';

const useGroupRootController = (model) => {
  const groupController = useGroupController(model);
  const deviceRequestsDialogController =
    useDeviceRequestsDialogGroupController(model);
  const requestStatusDialogController =
    useRequestStatusDialogGroupController(model);
  const imageViewerController = useImageViewerGroupController(model);

  return {
    groupController,
    deviceRequestsDialogController,
    requestStatusDialogController,
    imageViewerController,
  };
};

export default useGroupRootController;
