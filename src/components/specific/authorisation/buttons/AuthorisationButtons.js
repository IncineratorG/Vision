import React from 'react';
import {View, StyleSheet} from 'react-native';

const AuthorisationButtons = () => {
  return (
    <View style={styles.mainContainer}>
      <View />
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    backgroundColor: 'deepskyblue',
  },
});

export default React.memo(AuthorisationButtons);
