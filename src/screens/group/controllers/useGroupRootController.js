import useGroupController from './group/useGroupController';
import useDeviceRequestsDialogGroupController from './device-requests-dialog/useDeviceRequestDialogGroupController';
import useImageViewerGroupController from './image-viewer/useImageViewerGroupController';
import useHeaderGroupController from './header/useHeaderGroupController';

const useGroupRootController = (model) => {
  const headerController = useHeaderGroupController(model);
  const groupController = useGroupController(model);
  const deviceRequestsDialogController =
    useDeviceRequestsDialogGroupController(model);
  const imageViewerController = useImageViewerGroupController(model);

  return {
    headerController,
    groupController,
    deviceRequestsDialogController,
    imageViewerController,
  };
};

export default useGroupRootController;
