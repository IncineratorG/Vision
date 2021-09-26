import useGroupController from './group/useGroupController';
import useDeviceRequestsDialogGroupController from './device-requests-dialog/useDeviceRequestDialogGroupController';
import useRequestStatusDialogGroupController from './request-status-dialog/useRequestStatusDialogGroupController';

const useGroupRootController = (model) => {
  const groupController = useGroupController(model);
  const deviceRequestsDialogController =
    useDeviceRequestsDialogGroupController(model);
  const requestStatusDialogController =
    useRequestStatusDialogGroupController(model);

  return {
    groupController,
    deviceRequestsDialogController,
    requestStatusDialogController,
  };
};

export default useGroupRootController;
