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
        <View style={styles.iconColorContainer}>
          <View style={[styles.iconColor, {backgroundColor: '#03A9F4'}]} />
        </View>
        <View style={styles.requestNameContainer}>
          <Text style={styles.requestName}>{name}</Text>
        </View>
      </View>
    </TouchableOpacity>
  );

  // return (
  //   <TouchableOpacity onPress={itemPressHandler}>
  //     <View style={styles.mainContainer}>
  //       <Text style={styles.requestName}>{name}</Text>
  //     </View>
  //   </TouchableOpacity>
  // );
};

const styles = StyleSheet.create({
  mainContainer: {
    marginTop: 8,
    minHeight: 50,
    flexDirection: 'row',

    backgroundColor: 'white',
    padding: 8,
    justifyContent: 'center',
    borderWidth: 1,
    borderColor: 'lightgrey',
    borderRadius: 4,

    // minHeight: 30,
    // // width: 300,
    // marginTop: 8,
    // backgroundColor: '#03A9F4',
    // padding: 8,
    // justifyContent: 'center',
    // borderRadius: 4,
  },
  iconColorContainer: {
    width: 50,
    alignItems: 'center',
    justifyContent: 'center',
  },
  iconColor: {
    height: 30,
    width: 30,
    borderRadius: 15,
    backgroundColor: 'green',
  },
  requestNameContainer: {
    flex: 1,
    justifyContent: 'center',
    paddingLeft: 10,
  },
  requestName: {
    color: 'black',
    fontSize: 16,
  },
});

export default React.memo(DeviceRequestsDialogRequestsListItem);
