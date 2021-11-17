import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import AppActions from '../../../store/actions/AppActions';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import SelectedDeviceLocalActions from '../store/SelectedDeviceLocalActions';

const useSelectedDeviceController = (model) => {
  const {dispatch, localDispatch} = model;

  const imageViewerCloseHandler = useCallback(() => {
    localDispatch(SelectedDeviceLocalActions.actions.closeImageViewer());
  }, [localDispatch]);

  const checkingDeviceDialogCancelHandler = useCallback(() => {
    dispatch(
      AppActions.surveillanceIsDeviceAliveRequest.actions.cancelSendIsAliveRequest(),
    );
  }, [dispatch]);

  const getFrontCameraImageRequestPressHandler = useCallback(
    ({selectedDevice}) => {
      SystemEventsHandler.onInfo({
        info:
          'useSelectedDeviceController()->getFrontCameraImageRequestPressHandler(): ' +
          JSON.stringify(selectedDevice),
      });

      const {deviceName: selectedDeviceName} = selectedDevice;

      dispatch(
        AppActions.surveillanceTakeFrontCameraImageRequest.actions.sendTakeFrontCameraImageRequest(
          {receiverDeviceName: selectedDeviceName},
        ),
      );
    },
    [dispatch],
  );

  const getBackCameraImageRequestPressHandler = useCallback(
    ({selectedDevice}) => {
      SystemEventsHandler.onInfo({
        info:
          'useSelectedDeviceController()->getBackCameraImageRequestPressHandler(): ' +
          JSON.stringify(selectedDevice),
      });

      const {deviceName: selectedDeviceName} = selectedDevice;

      dispatch(
        AppActions.surveillanceTakeBackCameraImageRequest.actions.sendTakeBackCameraImageRequest(
          {receiverDeviceName: selectedDeviceName},
        ),
      );
    },
    [dispatch],
  );

  const toggleDetectDeviceMovementRequestPressHandler = useCallback(
    ({selectedDevice}) => {
      SystemEventsHandler.onInfo({
        info:
          'useSelectedDeviceController()->toggleDetectDeviceMovementRequestPressHandler(): ' +
          JSON.stringify(selectedDevice),
      });

      const {deviceName: selectedDeviceName} = selectedDevice;

      dispatch(
        AppActions.surveillanceToggleDetectDeviceMovementRequest.actions.sendToggleDetectDeviceMovementRequest(
          {receiverDeviceName: selectedDeviceName},
        ),
      );
    },
    [dispatch],
  );

  const toggleRecognizePersonWithFrontCameraRequestPressHandler = useCallback(
    ({selectedDevice}) => {
      SystemEventsHandler.onInfo({
        info:
          'useSelectedDeviceController()->toggleRecognizePersonWithFrontCameraRequestPressHandler(): ' +
          JSON.stringify(selectedDevice),
      });

      const {
        deviceName: selectedDeviceName,
        frontCameraRecognizePersonServiceRunning,
      } = selectedDevice;

      if (frontCameraRecognizePersonServiceRunning) {
        dispatch(
          AppActions.surveillanceToggleRecognizePersonRequest.actions.sendToggleRecognizePersonRequest(
            {
              receiverDeviceName: selectedDeviceName,
              cameraType: 'front',
              imageRotationDeg: 0,
            },
          ),
        );
      } else {
        dispatch(
          AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.actions.sendGetCameraRecognizePersonSettingsRequest(
            {
              receiverDeviceName: selectedDeviceName,
              cameraType: 'front',
            },
          ),
        );
      }
    },
    [dispatch],
  );

  const toggleRecognizePersonWithBackCameraRequestPressHandler = useCallback(
    ({selectedDevice}) => {
      SystemEventsHandler.onInfo({
        info:
          'useSelectedDeviceController()->toggleRecognizePersonWithBackCameraRequestPressHandler(): ' +
          JSON.stringify(selectedDevice),
      });

      const {
        deviceName: selectedDeviceName,
        backCameraRecognizePersonServiceRunning,
      } = selectedDevice;

      if (backCameraRecognizePersonServiceRunning) {
        dispatch(
          AppActions.surveillanceToggleRecognizePersonRequest.actions.sendToggleRecognizePersonRequest(
            {
              receiverDeviceName: selectedDeviceName,
              cameraType: 'back',
              imageRotationDeg: 0,
            },
          ),
        );
      } else {
        dispatch(
          AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.actions.sendGetCameraRecognizePersonSettingsRequest(
            {
              receiverDeviceName: selectedDeviceName,
              cameraType: 'back',
            },
          ),
        );
      }
    },
    [dispatch],
  );

  return {
    imageViewerCloseHandler,
    checkingDeviceDialogCancelHandler,
    getFrontCameraImageRequestPressHandler,
    getBackCameraImageRequestPressHandler,
    toggleDetectDeviceMovementRequestPressHandler,
    toggleRecognizePersonWithFrontCameraRequestPressHandler,
    toggleRecognizePersonWithBackCameraRequestPressHandler,
  };
};

export default useSelectedDeviceController;
