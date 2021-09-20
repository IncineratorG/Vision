import React from 'react';
import {View, StyleSheet} from 'react-native';
import SimpleButton from '../../../components/common/simple-button/SimpleButton';
import GroupDevicesList from '../../../components/specific/group/group-devices-list/GroupDevicesList';
import DeviceRequestsDialog from '../../../components/specific/group/device-requests-dialog/DeviceRequestsDialog';
import SelectedDeviceErrorDialog from '../../../components/specific/group/selected-device-error-dialog/SelectedDeviceErrorDialog';

const GroupView = ({model, controller}) => {
  const {
    data: {
      devicesInGroupArray,
      localState: {
        deviceRequestsDialog: {
          visible: deviceRequestsDialogVisible,
          device: deviceRequestsDialogSelectedDevice,
        },
        selectedDeviceErrorDialog: {
          visible: selectedDeviceErrorDialogVisible,
          message: selectedDeviceErrorDialogMessage,
        },
      },
    },
  } = model;

  const {
    testRequest,
    startService,
    stopService,
    updateDevicesInGroupData,
    logout,
    devicePressHandler,
    deviceRequestsDialogCancelHandler,
    deviceRequestsDialogGetFrontCameraImageRequestPressHandler,
    deviceRequestsDialogGetBackCameraImageRequestPressHandler,
    selectedDeviceErrorDialogCancelHandler,
  } = controller;

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
      onGetFrontCameraRequestPress={
        deviceRequestsDialogGetFrontCameraImageRequestPressHandler
      }
      onGetBackCameraRequestPress={
        deviceRequestsDialogGetBackCameraImageRequestPressHandler
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

  return (
    <View style={styles.mainContainer}>
      <View style={styles.contentContainer}>{devicesList}</View>
      <View style={styles.buttons}>
        <View style={styles.buttonContainer}>
          <SimpleButton title={'Test request'} onPress={testRequest} />
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
      {deviceRequestsDialog}
      {selectedDeviceErrorDialog}
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    // backgroundColor: 'coral',
  },
  contentContainer: {
    flex: 1,
    // backgroundColor: 'pink',
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
