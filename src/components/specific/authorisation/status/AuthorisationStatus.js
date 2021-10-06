import React from 'react';
import {View, Text, StyleSheet} from 'react-native';
import MaterialIcon from 'react-native-vector-icons/MaterialIcons';

const AuthorisationStatus = ({show, text, isError}) => {
  const progressIconComponent = (
    <MaterialIcon name="loop" size={18} color="lightgrey" />
  );
  const errorIconComponent = (
    <MaterialIcon name="error" size={18} color="red" />
  );

  const iconComponent = isError ? errorIconComponent : progressIconComponent;

  const statusInfoComponent = (
    <View style={styles.statusInfoContainer}>
      <View style={styles.iconContainer}>{iconComponent}</View>
      <View style={styles.textContainer}>
        <Text style={styles.text}>{text}</Text>
      </View>
    </View>
  );

  const statusComponent = show ? statusInfoComponent : null;
  // const statusComponent = statusInfoComponent;

  return <View style={styles.mainContainer}>{statusComponent}</View>;
};

const styles = StyleSheet.create({
  mainContainer: {
    height: 25,
    // width: 300,
    flexDirection: 'row',
    alignSelf: 'center',
    // backgroundColor: 'black',
  },
  statusInfoContainer: {
    // backgroundColor: 'grey',
    // flex: 1,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
  },
  iconContainer: {
    width: 25,
    height: 25,
    // backgroundColor: 'orange',
    justifyContent: 'center',
    alignItems: 'center',
  },
  textContainer: {
    // flex: 1,
    // alignSelf: 'stretch',
    // backgroundColor: 'red',
  },
  text: {
    color: 'lightgrey',
    fontSize: 14,
  },
});

export default React.memo(AuthorisationStatus);

// import React from 'react';
// import {View, Text, StyleSheet} from 'react-native';
// import MaterialIcon from 'react-native-vector-icons/MaterialIcons';
//
// const AuthorisationStatus = ({show, text, isError}) => {
//   // const progressIconComponent = (
//   //   <MaterialIcon name="loop" size={18} color="lightgrey" />
//   // );
//   // const errorIconComponent = (
//   //   <MaterialIcon name="error" size={18} color="lightgrey" />
//   // );
//
//   const statusInfoComponent = (
//     <View style={styles.statusInfoContainer}>
//       <View style={styles.iconContainer}>
//         <MaterialIcon name="loop" size={18} color="lightgrey" />
//       </View>
//       <View style={styles.textContainer}>
//         <Text style={styles.text}>{text}</Text>
//       </View>
//     </View>
//   );
//
//   const statusComponent = show ? statusInfoComponent : null;
//
//   return <View style={styles.mainContainer}>{statusComponent}</View>;
// };
//
// const styles = StyleSheet.create({
//   mainContainer: {
//     height: 25,
//     // width: 300,
//     flexDirection: 'row',
//     alignSelf: 'center',
//     // backgroundColor: 'black',
//   },
//   statusInfoContainer: {
//     // backgroundColor: 'grey',
//     // flex: 1,
//     flexDirection: 'row',
//     justifyContent: 'center',
//     alignItems: 'center',
//   },
//   iconContainer: {
//     width: 25,
//     height: 25,
//     // backgroundColor: 'orange',
//     justifyContent: 'center',
//     alignItems: 'center',
//   },
//   textContainer: {
//     // flex: 1,
//     // alignSelf: 'stretch',
//     // backgroundColor: 'red',
//   },
//   text: {
//     color: 'lightgrey',
//     fontSize: 14,
//   },
// });
//
// export default React.memo(AuthorisationStatus);
