import React from 'react';
import {View, StyleSheet} from 'react-native';
import CheckingDeviceDialog from '../../../components/specific/selected-device/checking-device-dialog/CheckingDeviceDialog';
import DeviceNotAvailable from '../../../components/specific/selected-device/device-not-available/DeviceNotAvailable';
import DeviceRequestsList from '../../../components/specific/selected-device/device-requests-list/DeviceRequestsList';
import CurrentRequestStatusDialog from '../../../components/specific/selected-device/current-request-status-dialog/CurrentRequestStatusDialog';
import ImageViewerModal from '../../../components/specific/selected-device/image-viewer-modal/ImageViewerModal';
import CameraRecognizePersonSettingsDialog from '../../../components/specific/selected-device/camera-recognize-person-settings-dialog/CameraRecognizePersonSettingsDialog';

const SelectedDeviceView = ({model, controller}) => {
  const {
    data: {
      isDeviceAliveRequestInProgress,
      selectedDeviceAlive,
      localState: {
        device,
        currentRequestStatusDialog: {
          visible: currentRequestStatusDialogVisible,
          statusText: currentRequestStatusDialogStatusText,
          leftButtonVisible: currentRequestStatusDialogLeftButtonVisible,
          leftButtonText: currentRequestStatusDialogLeftButtonText,
          leftButtonPressHandler:
            currentRequestStatusDialogLeftButtonPressHandler,
          rightButtonVisible: currentRequestStatusDialogRightButtonVisible,
          rightButtonText: currentRequestStatusDialogRightButtonText,
          rightButtonPressHandler:
            currentRequestStatusDialogRightButtonPressHandler,
          onCancel: currentRequestStatusDialogOnCancel,
        },
        cameraRecognizePersonSettingsDialog: {
          visible: cameraRecognizePersonSettingsDialogVisible,
          image: cameraRecognizePersonSettingsDialogImage,
          confirmSettingsButtonPressHandler:
            cameraRecognizePersonSettingsDialogConfirmSettingsDialogPressHandler,
          cancelButtonPressHandler:
            cameraRecognizePersonSettingsDialogCancelButtonPressHandler,
          onCancel: cameraRecognizePersonSettingsDialogOnCancel,
        },
        imageViewer: {visible: imageViewerVisible, image: imageViewerImage},
      },
    },
  } = model;

  const {
    imageViewerCloseHandler,
    checkingDeviceDialogCancelHandler,
    getFrontCameraImageRequestPressHandler,
    getBackCameraImageRequestPressHandler,
    toggleDetectDeviceMovementRequestPressHandler,
    toggleRecognizePersonWithFrontCameraRequestPressHandler,
    toggleRecognizePersonWithBackCameraRequestPressHandler,
  } = controller;

  const screenContent =
    isDeviceAliveRequestInProgress ? null : selectedDeviceAlive ? (
      <DeviceRequestsList
        device={device}
        onGetFrontCameraImageRequestPress={
          getFrontCameraImageRequestPressHandler
        }
        onGetBackCameraImageRequestPress={getBackCameraImageRequestPressHandler}
        onToggleDetectDeviceMovementRequestPress={
          toggleDetectDeviceMovementRequestPressHandler
        }
        onToggleRecognizePersonWithFrontCameraRequestPress={
          toggleRecognizePersonWithFrontCameraRequestPressHandler
        }
        onToggleRecognizePersonWithBackCameraRequestPress={
          toggleRecognizePersonWithBackCameraRequestPressHandler
        }
      />
    ) : (
      <DeviceNotAvailable />
    );

  const checkingDeviceDialog = (
    <CheckingDeviceDialog
      visible={isDeviceAliveRequestInProgress}
      onCancel={checkingDeviceDialogCancelHandler}
    />
  );

  const currentRequestStatusDialog = (
    <CurrentRequestStatusDialog
      visible={currentRequestStatusDialogVisible}
      statusText={currentRequestStatusDialogStatusText}
      leftButtonVisible={currentRequestStatusDialogLeftButtonVisible}
      leftButtonText={currentRequestStatusDialogLeftButtonText}
      leftButtonPressHandler={currentRequestStatusDialogLeftButtonPressHandler}
      rightButtonVisible={currentRequestStatusDialogRightButtonVisible}
      rightButtonText={currentRequestStatusDialogRightButtonText}
      rightButtonPressHandler={
        currentRequestStatusDialogRightButtonPressHandler
      }
      onCancel={currentRequestStatusDialogOnCancel}
    />
  );

  const imageViewer = (
    <ImageViewerModal
      visible={imageViewerVisible}
      image={imageViewerImage}
      onClose={imageViewerCloseHandler}
    />
  );

  const cameraRecognizePersonSettingsDialog = (
    <CameraRecognizePersonSettingsDialog
      visible={cameraRecognizePersonSettingsDialogVisible}
      image={cameraRecognizePersonSettingsDialogImage}
      confirmSettingsButtonPressHandler={
        cameraRecognizePersonSettingsDialogConfirmSettingsDialogPressHandler
      }
      cancelButtonPressHandler={
        cameraRecognizePersonSettingsDialogCancelButtonPressHandler
      }
      onCancel={cameraRecognizePersonSettingsDialogOnCancel}
    />
  );

  return (
    <View style={styles.mainContainer}>
      {screenContent}
      {checkingDeviceDialog}
      {currentRequestStatusDialog}
      {imageViewer}
      {cameraRecognizePersonSettingsDialog}
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    backgroundColor: 'white',
  },
});

export default React.memo(SelectedDeviceView);
