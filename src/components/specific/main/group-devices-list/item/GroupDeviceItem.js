import React, {useState, useEffect, useCallback} from 'react';
import {View, Text, StyleSheet} from 'react-native';

const GroupDeviceItem = ({item}) => {
  return (
    <View style={[styles.itemContainer /*{backgroundColor: item.code}*/]}>
      <Text style={styles.itemName}>{item.name}</Text>
      <Text style={styles.itemCode}>{item.code}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    height: 100,
    width: 200,
    // backgroundColor: 'red',
    justifyContent: 'center',
    alignItems: 'center',
  },
  itemContainer: {
    justifyContent: 'flex-end',
    borderRadius: 5,
    // borderWidth: 1,
    // borderColor: 'grey',
    backgroundColor: 'purple',
    padding: 10,
    height: 150,
  },
  itemName: {
    fontSize: 16,
    color: '#fff',
    fontWeight: '600',
  },
  itemCode: {
    fontWeight: '600',
    fontSize: 12,
    color: '#fff',
  },
});
// const styles = StyleSheet.create({
//   mainContainer: {
//     height: 100,
//     width: 200,
//     backgroundColor: 'red',
//     justifyContent: 'center',
//     alignItems: 'center',
//   },
//   itemContainer: {
//     justifyContent: 'flex-end',
//     borderRadius: 5,
//     padding: 10,
//     height: 150,
//   },
//   itemName: {
//     fontSize: 16,
//     color: '#fff',
//     fontWeight: '600',
//   },
//   itemCode: {
//     fontWeight: '600',
//     fontSize: 12,
//     color: '#fff',
//   },
// });

export default React.memo(GroupDeviceItem);
