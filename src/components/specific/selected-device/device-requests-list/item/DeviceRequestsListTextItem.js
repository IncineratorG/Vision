import React, {useCallback} from 'react';
import {View, Text, TouchableOpacity, StyleSheet} from 'react-native';

const DeviceRequestsListTextItem = ({type, name, icon, onPress}) => {
  const itemPressHandler = useCallback(() => {
    if (onPress) {
      onPress({type});
    }
  }, [type, onPress]);

  return (
    <TouchableOpacity onPress={itemPressHandler}>
      <View style={styles.mainContainer}>
        <View style={styles.iconContainer}>
          <View style={[styles.icon]}>{icon}</View>
        </View>
        <View style={styles.requestNameContainer}>
          <Text style={styles.requestName}>{name}</Text>
        </View>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    marginTop: 8,
    height: 50,
    flexDirection: 'row',

    // backgroundColor: 'white',
    padding: 8,
    justifyContent: 'center',
    // borderWidth: 1,
    // borderColor: 'lightgrey',
    // borderRadius: 4,

    // minHeight: 30,
    // // width: 300,
    // marginTop: 8,
    // backgroundColor: '#03A9F4',
    // padding: 8,
    // justifyContent: 'center',
    // borderRadius: 4,
  },
  iconContainer: {
    width: 50,
    alignItems: 'center',
    justifyContent: 'center',
  },
  icon: {
    height: 30,
    width: 30,
    borderRadius: 15,
    // backgroundColor: 'green',
    alignItems: 'center',
    justifyContent: 'center',
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

export default React.memo(DeviceRequestsListTextItem);

// import React, {useCallback} from 'react';
// import {View, Text, TouchableOpacity, StyleSheet} from 'react-native';
//
// const DeviceRequestsListTextItem = ({type, name, icon, onPress}) => {
//   const itemPressHandler = useCallback(() => {
//     if (onPress) {
//       onPress({type});
//     }
//   }, [type, onPress]);
//
//   return (
//     <TouchableOpacity onPress={itemPressHandler}>
//       <View style={styles.mainContainer}>
//         <View style={styles.iconContainer}>
//           <View style={[styles.icon]}>{icon}</View>
//         </View>
//         <View style={styles.requestNameContainer}>
//           <Text style={styles.requestName}>{name}</Text>
//         </View>
//       </View>
//     </TouchableOpacity>
//   );
// };
//
// const styles = StyleSheet.create({
//   mainContainer: {
//     marginTop: 8,
//     minHeight: 50,
//     flexDirection: 'row',
//
//     backgroundColor: 'white',
//     padding: 8,
//     justifyContent: 'center',
//     // borderWidth: 1,
//     // borderColor: 'lightgrey',
//     // borderRadius: 4,
//
//     // minHeight: 30,
//     // // width: 300,
//     // marginTop: 8,
//     // backgroundColor: '#03A9F4',
//     // padding: 8,
//     // justifyContent: 'center',
//     // borderRadius: 4,
//   },
//   iconContainer: {
//     width: 50,
//     alignItems: 'center',
//     justifyContent: 'center',
//   },
//   icon: {
//     height: 30,
//     width: 30,
//     borderRadius: 15,
//     // backgroundColor: 'green',
//     alignItems: 'center',
//     justifyContent: 'center',
//   },
//   requestNameContainer: {
//     flex: 1,
//     justifyContent: 'center',
//     paddingLeft: 10,
//   },
//   requestName: {
//     color: 'black',
//     fontSize: 16,
//   },
// });
//
// export default React.memo(DeviceRequestsListTextItem);
