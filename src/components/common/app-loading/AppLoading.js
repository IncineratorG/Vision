import React from 'react';
import {View, Text, TouchableOpacity, StyleSheet} from 'react-native';

const AppLoading = () => {
  return (
    <View style={styles.mainContainer}>
      <View />
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    backgroundColor: 'orange',
  },
});

export default React.memo(AppLoading);
