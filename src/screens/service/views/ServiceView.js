import React from 'react';
import {View, StyleSheet} from 'react-native';
import SimpleButton from '../../../components/common/simple-button/SimpleButton';
import ServiceDeviceInfo from '../../../components/specific/service/device-info/ServiceDeviceInfo';

const ServiceView = ({model, controller}) => {
  const {stopService} = controller;

  const serviceDeviceInfoComponent = (
    <ServiceDeviceInfo onStopServicePress={stopService} />
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

export default React.memo(ServiceView);
