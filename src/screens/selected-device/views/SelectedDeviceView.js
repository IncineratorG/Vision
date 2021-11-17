import React from 'react';
import {View, StyleSheet} from 'react-native';
import CheckingDeviceDialog from '../../../components/specific/selected-device/checking-device-dialog/CheckingDeviceDialog';
import DeviceNotAvailable from '../../../components/specific/selected-device/device-not-available/DeviceNotAvailable';
import DeviceRequestsList from '../../../components/specific/selected-device/device-requests-list/DeviceRequestsList';

const SelectedDeviceView = ({model, controller}) => {
  const {
    data: {
      isDeviceAliveRequestInProgress,
      selectedDeviceAlive,
      localState: {device},
    },
  } = model;

  const {checkingDeviceDialogCancelHandler} = controller;

  const screenContent =
    isDeviceAliveRequestInProgress ? null : selectedDeviceAlive ? (
      <DeviceRequestsList device={device} />
    ) : (
      <DeviceNotAvailable />
    );

  const checkingDeviceDialog = (
    <CheckingDeviceDialog
      visible={isDeviceAliveRequestInProgress}
      onCancel={checkingDeviceDialogCancelHandler}
    />
  );

  return (
    <View style={styles.mainContainer}>
      {screenContent}
      {checkingDeviceDialog}
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    // backgroundColor: 'orange',
  },
});

export default React.memo(SelectedDeviceView);
