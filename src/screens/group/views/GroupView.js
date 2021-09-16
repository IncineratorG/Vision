import React from 'react';
import {View, StyleSheet} from 'react-native';
import SimpleButton from '../../../components/common/simple-button/SimpleButton';
import GroupDevicesList from '../../../components/specific/group/group-devices-list/GroupDevicesList';
import DeviceRequestsDialog from '../../../components/specific/group/device-requests-dialog/DeviceRequestsDialog';

const GroupView = ({model, controller}) => {
  const {
    data: {
      devicesInGroupArray,
      localState: {
        deviceRequestsDialog: {visible: deviceRequestsDialogVisible},
      },
    },
  } = model;

  const {
    testRequest,
    updateDevicesInGroupData,
    logout,
    devicePressHandler,
    deviceRequestsDialogCancelHandler,
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
      onCancelPress={deviceRequestsDialogCancelHandler}
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
            title={'Update devices in group data'}
            onPress={updateDevicesInGroupData}
          />
        </View>
        <View style={styles.buttonContainer}>
          <SimpleButton title={'Logout'} onPress={logout} />
        </View>
      </View>
      {deviceRequestsDialog}
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
