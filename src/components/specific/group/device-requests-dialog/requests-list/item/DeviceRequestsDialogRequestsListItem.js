import React, {useCallback} from 'react';
import {View, Text, TouchableOpacity, StyleSheet} from 'react-native';

const DeviceRequestsDialogRequestsListItem = ({type, name, onPress}) => {
  const itemPressHandler = useCallback(() => {
    if (onPress) {
      onPress({type});
    }
  }, [type, onPress]);

  return (
    <TouchableOpacity onPress={itemPressHandler}>
      <View style={styles.mainContainer}>
        <Text style={styles.text}>{name}</Text>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    minHeight: 30,
    // width: 300,
    marginTop: 8,
    backgroundColor: '#03A9F4',
    padding: 8,
    justifyContent: 'center',
    borderRadius: 4,
  },
  text: {
    color: 'white',
    fontSize: 16,
  },
});

export default React.memo(DeviceRequestsDialogRequestsListItem);
