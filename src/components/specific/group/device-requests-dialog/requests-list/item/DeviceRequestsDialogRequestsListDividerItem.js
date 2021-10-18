import React from 'react';
import {View, StyleSheet} from 'react-native';

const DeviceRequestsDialogRequestsListDividerItem = () => {
  return <View style={styles.mainContainer} />;
};

const styles = StyleSheet.create({
  mainContainer: {
    marginTop: 8,
    height: 1,
    backgroundColor: 'lightgrey',
  },
});

export default React.memo(DeviceRequestsDialogRequestsListDividerItem);
