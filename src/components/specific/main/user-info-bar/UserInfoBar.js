import React, {useState, useEffect} from 'react';
import {View, Text, StyleSheet} from 'react-native';

const UserInfoBar = ({groupName, groupPassword, deviceName}) => {
  const [innerGroupName, setInnerGroupName] = useState('-');
  const [innerGroupPassword, setInnerGroupPassword] = useState('-');
  const [innerDeviceName, setInnerDeviceName] = useState('-');

  useEffect(() => {
    if (groupName) {
      setInnerGroupName(groupName);
    } else {
      setInnerGroupName('-');
    }

    if (groupPassword) {
      setInnerGroupPassword(groupPassword);
    } else {
      setInnerGroupPassword('-');
    }

    if (deviceName) {
      setInnerDeviceName(deviceName);
    } else {
      setInnerDeviceName('-');
    }
  }, [groupName, groupPassword, deviceName]);

  return (
    <View style={styles.mainContainer}>
      <View style={styles.infoItemContainer}>
        <Text style={styles.infoItemText}>{innerGroupName}</Text>
      </View>
      <View style={styles.infoItemContainer}>
        <Text style={styles.infoItemText}>{innerGroupPassword}</Text>
      </View>
      <View style={styles.infoItemContainer}>
        <Text style={styles.infoItemText}>{innerDeviceName}</Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flexDirection: 'row',
    height: 25,
    backgroundColor: 'white',
  },
  infoItemContainer: {
    flex: 1,
    alignSelf: 'stretch',
    borderWidth: 1,
    borderColor: 'black',
    alignItems: 'center',
    justifyContent: 'center',
  },
  infoItemText: {
    fontSize: 10,
  },
});

export default React.memo(UserInfoBar);
