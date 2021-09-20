import React, {useState, useEffect, useCallback} from 'react';
import {View, Text, TouchableOpacity, StyleSheet} from 'react-native';

const GroupDeviceItem = ({device, onDevicePress}) => {
  const {deviceName, deviceMode, lastLoginTimestamp, lastUpdateTimestamp} =
    device;

  const backgroundColor = deviceMode === 'user' ? 'grey' : 'EMERALD';

  const devicePressHandler = useCallback(() => {
    onDevicePress({deviceName});
  }, [deviceName, onDevicePress]);

  return (
    <TouchableOpacity activeOpacity={0.5} onPress={devicePressHandler}>
      <View style={[styles.itemContainer, {backgroundColor}]}>
        <Text style={styles.itemName}>{deviceName}</Text>
        <Text style={styles.itemCode}>{lastLoginTimestamp}</Text>
        <Text style={styles.itemCode}>{lastUpdateTimestamp}</Text>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    height: 100,
    width: 200,
    // backgroundColor: 'red',
    justifyContent: 'center',
    alignItems: 'center',
  },
  itemContainer: {
    justifyContent: 'flex-end',
    borderRadius: 5,
    // borderWidth: 1,
    // borderColor: 'grey',
    backgroundColor: 'orange',
    padding: 10,
    height: 150,
  },
  itemName: {
    fontSize: 16,
    color: '#fff',
    fontWeight: '600',
  },
  itemCode: {
    fontWeight: '600',
    fontSize: 12,
    color: '#fff',
  },
});
// const styles = StyleSheet.create({
//   mainContainer: {
//     height: 100,
//     width: 200,
//     backgroundColor: 'red',
//     justifyContent: 'center',
//     alignItems: 'center',
//   },
//   itemContainer: {
//     justifyContent: 'flex-end',
//     borderRadius: 5,
//     padding: 10,
//     height: 150,
//   },
//   itemName: {
//     fontSize: 16,
//     color: '#fff',
//     fontWeight: '600',
//   },
//   itemCode: {
//     fontWeight: '600',
//     fontSize: 12,
//     color: '#fff',
//   },
// });

export default React.memo(GroupDeviceItem);
