import React from 'react';
import {View, StyleSheet} from 'react-native';
import SimpleButton from '../../../components/common/simple-button/SimpleButton';
import GroupDevicesList from '../../../components/specific/group/group-devices-list/GroupDevicesList';
import DeviceRequestsDialog from '../../../components/specific/group/device-requests-dialog/DeviceRequestsDialog';
import SelectedDeviceErrorDialog from '../../../components/specific/group/selected-device-error-dialog/SelectedDeviceErrorDialog';
import CameraRecognizePersonSettingsDialog from '../../../components/specific/group/camera-recognize-person-settings-dialog/CameraRecognizePersonSettingsDialog';

const GroupView = ({model, controller}) => {
  const {
    data: {
      devicesInGroupArray,
      loadingDevicesInGroup,
      isDeviceAliveRequestInProgress,
      selectedDeviceAlive,
      localState: {
        deviceRequestsDialog: {
          visible: deviceRequestsDialogVisible,
          device: deviceRequestsDialogSelectedDevice,
        },
        selectedDeviceErrorDialog: {
          visible: selectedDeviceErrorDialogVisible,
          message: selectedDeviceErrorDialogMessage,
        },
        requestInProgressDialog: {
          visible: requestInProgressDialogVisible,
          requestId: requestInProgressDialogRequestId,
        },
        requestStatusDialog: {
          visible: requestStatusDialogVisible,
          completed: requestStatusDialogCompleted,
          responseData: requestStatusDialogResponseData,
          canViewResponse: requestStatusDialogCanViewResponse,
          responseViewerCallback: requestStatusDialogResponseViewerCallback,
        },
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
    groupController: {
      getAppPermissions,
      testRequest,
      startService,
      stopService,
      updateDevicesInGroupData,
      logout,
      devicePressHandler,
      selectedDeviceErrorDialogCancelHandler,
    },
    deviceRequestsDialogController: {
      deviceRequestsDialogCancelHandler,
      deviceRequestsDialogGetFrontCameraImageRequestPressHandler,
      deviceRequestsDialogGetBackCameraImageRequestPressHandler,
      deviceRequestsDialogToggleDetectDeviceMovementRequestPressHandler,
      deviceRequestsDialogToggleRecognizePersonWithFrontCameraRequestPressHandler,
      deviceRequestsDialogToggleRecognizePersonWithBackCameraRequestPressHandler,
    },
    imageViewerController: {imageViewerCloseHandler},
  } = controller;

  const updateDataIndicator = loadingDevicesInGroup ? (
    <View style={styles.updateDataIndicator} />
  ) : null;

  const devicesList = (
    <GroupDevicesList
      devices={devicesInGroupArray}
      onDevicePress={devicePressHandler}
    />
  );

  const deviceRequestsDialog = (
    <DeviceRequestsDialog
      visible={deviceRequestsDialogVisible}
      device={deviceRequestsDialogSelectedDevice}
      checkingSelectedDeviceAvailability={isDeviceAliveRequestInProgress}
      selectedDeviceAvailable={selectedDeviceAlive}
      onGetFrontCameraRequestPress={
        deviceRequestsDialogGetFrontCameraImageRequestPressHandler
      }
      onGetBackCameraRequestPress={
        deviceRequestsDialogGetBackCameraImageRequestPressHandler
      }
      onToggleDetectDeviceMovementRequestPress={
        deviceRequestsDialogToggleDetectDeviceMovementRequestPressHandler
      }
      onToggleRecognizePersonWithFrontCameraRequestPress={
        deviceRequestsDialogToggleRecognizePersonWithFrontCameraRequestPressHandler
      }
      onToggleRecognizePersonWithBackCameraRequestPress={
        deviceRequestsDialogToggleRecognizePersonWithBackCameraRequestPressHandler
      }
      onCancelPress={deviceRequestsDialogCancelHandler}
    />
  );

  const selectedDeviceErrorDialog = (
    <SelectedDeviceErrorDialog
      visible={selectedDeviceErrorDialogVisible}
      errorText={selectedDeviceErrorDialogMessage}
      onCancelPress={selectedDeviceErrorDialogCancelHandler}
    />
  );

  // const currentRequestStatusDialog = (
  //   <CurrentRequestStatusDialog
  //     visible={currentRequestStatusDialogVisible}
  //     statusText={currentRequestStatusDialogStatusText}
  //     leftButtonVisible={currentRequestStatusDialogLeftButtonVisible}
  //     leftButtonText={currentRequestStatusDialogLeftButtonText}
  //     leftButtonPressHandler={currentRequestStatusDialogLeftButtonPressHandler}
  //     rightButtonVisible={currentRequestStatusDialogRightButtonVisible}
  //     rightButtonText={currentRequestStatusDialogRightButtonText}
  //     rightButtonPressHandler={
  //       currentRequestStatusDialogRightButtonPressHandler
  //     }
  //     onCancel={currentRequestStatusDialogOnCancel}
  //   />
  // );

  // const imageViewer = (
  //   <ImageViewerModal
  //     visible={imageViewerVisible}
  //     image={imageViewerImage}
  //     onClose={imageViewerCloseHandler}
  //   />
  // );

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
      <View style={styles.contentContainer}>{devicesList}</View>
      <View style={styles.buttons}>
        <View style={styles.buttonContainer}>
          <SimpleButton title={'Test request'} onPress={testRequest} />
        </View>
        <View style={styles.buttonContainer}>
          <SimpleButton
            title={'Get app permissions'}
            onPress={getAppPermissions}
          />
        </View>
        <View style={styles.buttonContainer}>
          <SimpleButton title={'Start service'} onPress={startService} />
        </View>
        <View style={styles.buttonContainer}>
          <SimpleButton title={'Stop service'} onPress={stopService} />
        </View>
        <View style={styles.buttonContainer}>
          <SimpleButton
            title={'Update devices in group data'}
            onPress={updateDevicesInGroupData}
          />
        </View>
        <View style={styles.buttonContainer}>
          <SimpleButton title={'Logout'} onPress={logout} />
        </View>
      </View>
      {updateDataIndicator}
      {deviceRequestsDialog}
      {selectedDeviceErrorDialog}
      {cameraRecognizePersonSettingsDialog}
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    backgroundColor: 'white',
  },
  contentContainer: {
    flex: 1,
    // backgroundColor: 'pink',
  },
  updateDataIndicator: {
    width: 20,
    height: 20,
    backgroundColor: 'coral',
    position: 'absolute',
    top: 0,
    right: 0,
    // marginTop: 10,
    // marginRight: 10,
  },
  buttons: {},
  buttonContainer: {
    height: 50,
    padding: 8,
    backgroundColor: 'green',
    justifyContent: 'center',
    borderWidth: 1,
    borderColor: 'grey',
  },
});

export default React.memo(GroupView);
