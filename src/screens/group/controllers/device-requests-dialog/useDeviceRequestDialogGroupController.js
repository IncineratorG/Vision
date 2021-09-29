import {useCallback} from 'react';
import AppActions from '../../../../store/actions/AppActions';
import GroupLocalActions from '../../store/GroupLocalActions';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';

const useDeviceRequestsDialogGroupController = (model) => {
  const {
    data: {
      deviceRequestTypes,
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
    setters: {setCurrentRequestType},
    navigation,
    dispatch,
    localDispatch,
  } = model;

  const deviceRequestsDialogCancelHandler = useCallback(() => {
    dispatch(
      AppActions.surveillanceIsDeviceAliveRequest.actions.cancelSendIsAliveRequest(),
    );
    localDispatch(
      GroupLocalActions.actions.setDeviceRequestsDialogVisibility({
        visible: false,
      }),
    );
  }, [localDispatch, dispatch]);

  const deviceRequestsDialogGetFrontCameraImageRequestPressHandler =
    useCallback(
      ({selectedDevice}) => {
        SystemEventsHandler.onInfo({
          info:
            'useDeviceRequestsDialogGroupController->deviceRequestsDialogGetFrontCameraImageRequestPressHandler(): ' +
            JSON.stringify(selectedDevice),
        });

        const {deviceName: selectedDeviceName} = selectedDevice;

        localDispatch(
          GroupLocalActions.actions.setDeviceRequestsDialogVisibility({
            visible: false,
          }),
        );
      },
      [localDispatch],
    );

  const deviceRequestsDialogGetBackCameraImageRequestPressHandler = useCallback(
    ({selectedDevice}) => {
      SystemEventsHandler.onInfo({
        info:
          'useDeviceRequestsDialogGroupController->deviceRequestsDialogGetBackCameraImageRequestPressHandler(): ' +
          JSON.stringify(selectedDevice),
      });

      const {deviceName: selectedDeviceName} = selectedDevice;

      localDispatch(
        GroupLocalActions.actions.setDeviceRequestsDialogVisibility({
          visible: false,
        }),
      );

      dispatch(
        AppActions.surveillanceTakeBackCameraImageRequest.actions.sendTakeBackCameraImageRequest(
          {receiverDeviceName: selectedDeviceName},
        ),
      );
      setCurrentRequestType(deviceRequestTypes.TAKE_BACK_CAMERA_IMAGE);
    },
    [deviceRequestTypes, setCurrentRequestType, localDispatch, dispatch],
  );

  return {
    deviceRequestsDialogCancelHandler,
    deviceRequestsDialogGetFrontCameraImageRequestPressHandler,
    deviceRequestsDialogGetBackCameraImageRequestPressHandler,
  };
};

export default useDeviceRequestsDialogGroupController;
