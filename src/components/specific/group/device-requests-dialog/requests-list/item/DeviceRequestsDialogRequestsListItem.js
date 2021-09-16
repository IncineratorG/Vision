import React from 'react';
import {View, StyleSheet} from 'react-native';

const DeviceRequestsDialogRequestsListItem = () => {
  return (
    <View style={styles.mainContainer}>
      <View />
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    height: 50,
    width: 300,
    marginTop: 8,
    backgroundColor: 'coral',
  },
});

export default React.memo(DeviceRequestsDialogRequestsListItem);
