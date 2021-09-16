import React from 'react';
import {View, StyleSheet} from 'react-native';
import SimpleButton from '../../../components/common/simple-button/SimpleButton';
import GroupDevicesList from '../../../components/specific/group/group-devices-list/GroupDevicesList';

const GroupView = ({model, controller}) => {
  const {
    data: {devicesInGroupArray},
  } = model;

  const {testRequest, updateDevicesInGroupData, logout} = controller;

  const devicesList = <GroupDevicesList devices={devicesInGroupArray} />;

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
