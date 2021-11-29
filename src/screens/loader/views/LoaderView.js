import React from 'react';
import {View, StyleSheet} from 'react-native';

const LoaderView = ({model, controller}) => {
  return <View style={styles.mainContainer} />;
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    backgroundColor: 'orange',
  },
});

export default React.memo(LoaderView);
