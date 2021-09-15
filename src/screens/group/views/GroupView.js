import React from 'react';
import {View, StyleSheet} from 'react-native';

const GroupView = ({model, controller}) => {
  return (
    <View style={styles.mainContainer}>
      <View />
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    backgroundColor: 'coral',
  },
});

export default React.memo(GroupView);
