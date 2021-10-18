import {useCallback} from 'react';
import AppActions from '../../../../store/actions/AppActions';
import GroupLocalActions from '../../store/GroupLocalActions';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';

const useDeviceRequestsDialogGroupController = (model) => {
  const {dispatch, localDispatch} = model;

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

        dispatch(
          AppActions.surveillanceTakeFrontCameraImageRequest.actions.sendTakeFrontCameraImageRequest(
            {receiverDeviceName: selectedDeviceName},
          ),
        );
      },
      [localDispatch, dispatch],
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
    },
    [localDispatch, dispatch],
  );

  const deviceRequestsDialogToggleDetectDeviceMovementRequestPressHandler =
    useCallback(
      ({selectedDevice}) => {
        SystemEventsHandler.onInfo({
          info:
            'useDeviceRequestsDialogGroupController->deviceRequestsDialogToggleDetectDeviceMovementRequestPressHandler(): ' +
            JSON.stringify(selectedDevice),
        });

        const {deviceName: selectedDeviceName} = selectedDevice;

        // localDispatch(
        //   GroupLocalActions.actions.setDeviceRequestsDialogVisibility({
        //     visible: false,
        //   }),
        // );

        dispatch(
          AppActions.surveillanceToggleDetectDeviceMovementRequest.actions.sendToggleDetectDeviceMovementRequest(
            {receiverDeviceName: selectedDeviceName},
          ),
        );
      },
      [dispatch],
    );

  const deviceRequestsDialogToggleRecognizePersonWithFrontCameraRequestPressHandler =
    useCallback(
      ({selectedDevice}) => {
        SystemEventsHandler.onInfo({
          info:
            'useDeviceRequestsDialogGroupController->deviceRequestsDialogToggleRecognizePersonWithFrontCameraRequestPressHandler(): ' +
            JSON.stringify(selectedDevice),
        });

        const {deviceName: selectedDeviceName} = selectedDevice;

        dispatch(
          AppActions.surveillanceToggleRecognizePersonRequest.actions.sendToggleRecognizePersonRequest(
            {receiverDeviceName: selectedDeviceName, cameraType: 'front'},
          ),
        );
      },
      [dispatch],
    );

  const deviceRequestsDialogToggleRecognizePersonWithBackCameraRequestPressHandler =
    useCallback(
      ({selectedDevice}) => {
        SystemEventsHandler.onInfo({
          info:
            'useDeviceRequestsDialogGroupController->deviceRequestsDialogToggleRecognizePersonWithBackCameraRequestPressHandler(): ' +
            JSON.stringify(selectedDevice),
        });

        const {deviceName: selectedDeviceName} = selectedDevice;

        dispatch(
          AppActions.surveillanceToggleRecognizePersonRequest.actions.sendToggleRecognizePersonRequest(
            {receiverDeviceName: selectedDeviceName, cameraType: 'back'},
          ),
        );
      },
      [dispatch],
    );

  return {
    deviceRequestsDialogCancelHandler,
    deviceRequestsDialogGetFrontCameraImageRequestPressHandler,
    deviceRequestsDialogGetBackCameraImageRequestPressHandler,
    deviceRequestsDialogToggleDetectDeviceMovementRequestPressHandler,
    deviceRequestsDialogToggleRecognizePersonWithFrontCameraRequestPressHandler,
    deviceRequestsDialogToggleRecognizePersonWithBackCameraRequestPressHandler,
  };
};

export default useDeviceRequestsDialogGroupController;
