import React from 'react';
import {View, StyleSheet} from 'react-native';
import SimpleButton from '../../../components/common/simple-button/SimpleButton';

const ServiceView = ({model, controller}) => {
  const {stopService} = controller;

  return (
    <View style={styles.mainContainer}>
      <View style={styles.contentContainer} />
      <View style={styles.buttons}>
        <View style={styles.buttonContainer}>
          <SimpleButton title={'Stop service'} onPress={stopService} />
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    backgroundColor: 'orange',
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

export default React.memo(ServiceView);
