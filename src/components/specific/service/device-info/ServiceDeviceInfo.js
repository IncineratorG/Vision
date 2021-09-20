import React, {useMemo} from 'react';
import {View, Text, StyleSheet} from 'react-native';
import SimpleButton from '../../../common/simple-button/SimpleButton';
import MaterialIcon from 'react-native-vector-icons/MaterialIcons';

const ServiceDeviceInfo = ({
  groupName,
  groupPassword,
  deviceName,
  onStopServicePress,
}) => {
  const groupNameIcon = useMemo(() => {
    return <MaterialIcon name="dns" size={22} color="grey" />;
  }, []);
  const groupPasswordIcon = useMemo(() => {
    return <MaterialIcon name="vpn-key" size={22} color="grey" />;
  }, []);
  const deviceNameIcon = useMemo(() => {
    return (
      <MaterialIcon name="perm-device-information" size={22} color="grey" />
    );
  }, []);

  return (
    <View style={styles.mainContainer}>
      <View style={styles.contentContainer}>
        <View style={styles.deviceInfoItem}>
          <View style={styles.iconContainer}>
            <View style={styles.iconInnerContainer}>{groupNameIcon}</View>
          </View>
          <View style={styles.infoTextContainer}>
            <Text style={styles.infoText}>{groupName}</Text>
          </View>
        </View>
        <View style={styles.deviceInfoItem}>
          <View style={styles.iconContainer}>
            <View style={styles.iconInnerContainer}>{groupPasswordIcon}</View>
          </View>
          <View style={styles.infoTextContainer}>
            <Text style={styles.infoText}>{groupPassword}</Text>
          </View>
        </View>
        <View style={styles.deviceInfoItem}>
          <View style={styles.iconContainer}>
            <View style={styles.iconInnerContainer}>{deviceNameIcon}</View>
          </View>
          <View style={styles.infoTextContainer}>
            <Text style={styles.infoText}>{deviceName}</Text>
          </View>
        </View>
      </View>
      <View style={styles.buttons}>
        <View style={styles.buttonContainer}>
          <SimpleButton title={'Stop service'} onPress={onStopServicePress} />
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    height: 250,
    width: 300,
    backgroundColor: 'white',
  },
  contentContainer: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'space-evenly',
  },
  deviceInfoItem: {
    height: 40,
    width: 250,
    flexDirection: 'row',
    backgroundColor: '#F5F5F5',
    borderRadius: 4,
  },
  iconContainer: {
    width: 50,
    alignSelf: 'stretch',
    justifyContent: 'center',
    alignItems: 'center',
  },
  iconInnerContainer: {
    width: 30,
    height: 30,
    justifyContent: 'center',
    alignItems: 'center',
  },
  infoTextContainer: {
    flex: 1,
    alignSelf: 'stretch',
    justifyContent: 'center',
  },
  infoText: {
    fontSize: 16,
    color: '#000000',
    borderBottomColor: 'transparent',
  },
  buttons: {},
  buttonContainer: {
    height: 50,
    padding: 8,
    justifyContent: 'center',
  },
});

export default React.memo(ServiceDeviceInfo);
