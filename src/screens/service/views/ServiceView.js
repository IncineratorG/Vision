import React from 'react';
import {View, StyleSheet} from 'react-native';
import ServiceDeviceInfo from '../../../components/specific/service/device-info/ServiceDeviceInfo';

const ServiceView = ({model, controller}) => {
  const {
    data: {groupName, groupPassword, deviceName},
  } = model;

  const {
    stopService,
    testCameraPressHandler,
    testNotificationPressHandler,
    testMotionSensorPressHandler,
    testCameraMotionDetectionPressHandler,
  } = controller;

  const serviceDeviceInfoComponent = (
    <ServiceDeviceInfo
      groupName={groupName}
      groupPassword={groupPassword}
      deviceName={deviceName}
      onTestCameraPress={testCameraPressHandler}
      onTestNotificationPress={testNotificationPressHandler}
      onTestMotionSensorPress={testMotionSensorPressHandler}
      onTestCameraMotionDetectionPress={testCameraMotionDetectionPressHandler}
      onStopServicePress={stopService}
    />
  );

  return (
    <View style={styles.mainContainer}>
      <View style={styles.contentContainer}>{serviceDeviceInfoComponent}</View>
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
    justifyContent: 'center',
    alignItems: 'center',
  },
  // buttons: {},
  // buttonContainer: {
  //   height: 50,
  //   padding: 8,
  //   backgroundColor: 'green',
  //   justifyContent: 'center',
  //   borderWidth: 1,
  //   borderColor: 'grey',
  // },
});

export default React.memo(ServiceView);
