import React from 'react';
import {View, TouchableOpacity, StyleSheet} from 'react-native';

const MainView = ({model, controller}) => {
  const {callback1, callback2} = controller;

  return (
    <View style={styles.mainContainer}>
      <View style={styles.buttonsContainer}>
        <TouchableOpacity
          style={styles.testButtonContainer}
          onPress={callback1}>
          <View style={styles.testButtonContainer} />
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.test2ButtonContainer}
          onPress={callback2}>
          <View style={styles.test2ButtonContainer} />
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
  },
  buttonsContainer: {
    flexDirection: 'row',
    position: 'absolute',
    bottom: 0,
    left: 0,
    right: 0,
    height: 50,
  },
  testButtonContainer: {
    alignSelf: 'stretch',
    width: 50,
    backgroundColor: 'grey',
    margin: 5,
  },
  test2ButtonContainer: {
    alignSelf: 'stretch',
    width: 50,
    backgroundColor: 'lightgrey',
    margin: 5,
  },
});

export default React.memo(MainView);
