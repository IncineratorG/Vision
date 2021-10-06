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
    deviceMovementServiceRunning,
  } = device;

  const activeOptionColor = '#2ecc71';
  const notActiveOptionColor = 'lightgrey';

  const lastLoginDateText = new Date(lastLoginTimestamp).toLocaleString();
  const lastUpdateDateText =
    lastUpdateTimestamp > 0
      ? new Date(lastUpdateTimestamp).toLocaleString()
      : '-';

  const backgroundColor =
    deviceMode === 'user'
      ? notActiveOptionColor
      : Date.now() - lastUpdateTimestamp > 90000
      ? '#d35400'
      : activeOptionColor;

  const hasBackCameraOptionIndicatorColor = hasBackCamera
    ? activeOptionColor
    : notActiveOptionColor;
  const hasFrontCameraOptionIndicatorColor = hasFrontCamera
    ? activeOptionColor
    : notActiveOptionColor;
  const canDetectDeviceMovementOptionIndicatorColor = canDetectDeviceMovement
    ? activeOptionColor
    : notActiveOptionColor;

  const deviceMovementServiceIndicatorColor = deviceMovementServiceRunning
    ? activeOptionColor
    : notActiveOptionColor;

  const hasBackCameraOptionIndicator = (
    <View
      style={[
        styles.statusBarIndicator,
        {backgroundColor: hasBackCameraOptionIndicatorColor},
      ]}>
      <Text style={styles.statusBarIndicatorText}>{'BC'}</Text>
    </View>
  );
  const hasFrontCameraOptionIndicator = (
    <View
      style={[
        styles.statusBarIndicator,
        {backgroundColor: hasFrontCameraOptionIndicatorColor},
      ]}>
      <Text style={styles.statusBarIndicatorText}>{'FC'}</Text>
    </View>
  );
  const canDetectDeviceMovementOptionIndicator = (
    <View
      style={[
        styles.statusBarIndicator,
        {backgroundColor: canDetectDeviceMovementOptionIndicatorColor},
      ]}>
      <Text style={styles.statusBarIndicatorText}>{'DM'}</Text>
    </View>
  );

  const deviceMovementServiceRunningIndicator = (
    <View
      style={[
        styles.statusBarIndicator,
        {backgroundColor: deviceMovementServiceIndicatorColor},
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
        <Text style={styles.itemName}>{deviceName}</Text>
        <View style={styles.deviceOptionsBar}>
          {hasBackCameraOptionIndicator}
          {hasFrontCameraOptionIndicator}
          {canDetectDeviceMovementOptionIndicator}
        </View>
        <View style={styles.deviceStatusBar}>
          {deviceMovementServiceRunningIndicator}
        </View>
        <View style={styles.freeSpace} />
        <Text style={styles.itemCode}>{lastLoginDateText}</Text>
        <Text style={styles.itemCode}>{lastUpdateDateText}</Text>
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
    // padding: 10,
    paddingLeft: 10,
    paddingRight: 10,
    paddingBottom: 10,
    height: 170,
  },
  itemName: {
    fontSize: 16,
    color: '#fff',
    fontWeight: '600',
    margin: 4,
  },
  itemCode: {
    fontWeight: '600',
    fontSize: 12,
    color: '#fff',
  },
  freeSpace: {
    flex: 1,
  },
  deviceOptionsBar: {
    height: 16,
    backgroundColor: '#2c3e50',
    alignSelf: 'stretch',
    flexDirection: 'row',
    alignItems: 'center',
  },
  deviceStatusBar: {
    marginTop: 4,
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

export default React.memo(GroupDeviceItem);
