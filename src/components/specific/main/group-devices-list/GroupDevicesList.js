import React, {useState, useEffect} from 'react';
import {View, FlatList, Text, StyleSheet} from 'react-native';

const GroupDevicesList = () => {
  return (
    <View styles={styles.mainContainer}>
      <View />
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    backgroundColor: 'grey',
  },
});

export default React.memo(GroupDevicesList);
