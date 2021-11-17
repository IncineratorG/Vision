import React from 'react';
import {View, StyleSheet} from 'react-native';
import CheckingDeviceDialog from '../../../components/specific/selected-device/checking-device-dialog/CheckingDeviceDialog';
import DeviceNotAvailable from '../../../components/specific/selected-device/device-not-available/DeviceNotAvailable';
import DeviceRequestsList from '../../../components/specific/selected-device/device-requests-list/DeviceRequestsList';
import CurrentRequestStatusDialog from '../../../components/specific/selected-device/current-request-status-dialog/CurrentRequestStatusDialog';
import ImageViewerModal from '../../../components/specific/selected-device/image-viewer-modal/ImageViewerModal';

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

  return (
    <View style={styles.mainContainer}>
      {screenContent}
      {checkingDeviceDialog}
      {currentRequestStatusDialog}
      {imageViewer}
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
