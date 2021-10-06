import React, {useState, useEffect, useCallback} from 'react';
import {View, Text, TouchableOpacity, StyleSheet} from 'react-native';
import {SystemEventsHandler} from '../../../../../utils/common/system-events-handler/SystemEventsHandler';

const GroupDeviceItem = ({device, onDevicePress}) => {
  const {
    deviceName,
    deviceMode,
    lastLoginTimestamp,
    lastUpdateTimestamp,
    hasFrontCamera,
    hasBackCamera,
    canDetectDeviceMovement,
  } = device;

  const activeOptionColor = '#2ecc71';
  const notActiveOptionColor = 'lightgrey';

  // const backgroundColor =
  //   deviceMode === 'user' ? notActiveOptionColor : activeOptionColor;

  SystemEventsHandler.onInfo({
    info: 'DEF: ' + deviceName + ': ' + (Date.now() - lastUpdateTimestamp),
  });

  const backgroundColor =
    deviceMode === 'user'
      ? notActiveOptionColor
      : Date.now() - lastUpdateTimestamp > 90000
      ? '#d35400'
      : activeOptionColor;

  const hasBackCameraIndicatorColor = hasBackCamera
    ? activeOptionColor
    : notActiveOptionColor;
  const hasFrontCameraIndicatorColor = hasFrontCamera
    ? activeOptionColor
    : notActiveOptionColor;
  const canDetectDeviceMovementIndicatorColor = canDetectDeviceMovement
    ? activeOptionColor
    : notActiveOptionColor;

  const hasBackCameraIndicator = (
    <View
      style={[
        styles.statusBarIndicator,
        {backgroundColor: hasBackCameraIndicatorColor},
      ]}>
      <Text style={styles.statusBarIndicatorText}>{'BC'}</Text>
    </View>
  );
  const hasFrontCameraIndicator = (
    <View
      style={[
        styles.statusBarIndicator,
        {backgroundColor: hasFrontCameraIndicatorColor},
      ]}>
      <Text style={styles.statusBarIndicatorText}>{'FC'}</Text>
    </View>
  );
  const canDetectDeviceMovementIndicator = (
    <View
      style={[
        styles.statusBarIndicator,
        {backgroundColor: canDetectDeviceMovementIndicatorColor},
      ]}>
      <Text style={styles.statusBarIndicatorText}>{'DM'}</Text>
    </View>
  );

  const devicePressHandler = useCallback(() => {
    onDevicePress({device});
  }, [device, onDevicePress]);

  return (
    <TouchableOpacity activeOpacity={0.5} onPress={devicePressHandler}>
      <View style={[styles.itemContainer, {backgroundColor}]}>
        <View style={styles.statusBar}>
          {hasBackCameraIndicator}
          {hasFrontCameraIndicator}
          {canDetectDeviceMovementIndicator}
        </View>
        <View style={styles.freeSpace} />
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
  freeSpace: {
    flex: 1,
  },
  statusBar: {
    height: 16,
    backgroundColor: '#2c3e50',
    alignSelf: 'stretch',
    flexDirection: 'row',
    alignItems: 'center',
  },
  statusBarIndicator: {
    width: 10,
    height: 10,
    backgroundColor: 'red',
    marginLeft: 2,
    justifyContent: 'center',
    alignItems: 'center',
  },
  statusBarIndicatorText: {
    fontSize: 6,
    color: 'white',
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
