import React from 'react';
import {View, Text, StyleSheet} from 'react-native';

const AuthorisationAppLogo = () => {
  return (
    <View style={styles.mainContainer}>
      <View style={styles.logoOuterContainer}>
        <View style={styles.logoContainer} />
        <View style={styles.appNameContainer}>
          <Text style={styles.appName}>{'Vision'}</Text>
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    backgroundColor: 'oldlace',
    justifyContent: 'center',
    alignItems: 'center',
  },
  logoOuterContainer: {
    height: 200,
    width: 180,
    // backgroundColor: 'black',
  },
  logoContainer: {
    flex: 1,
    alignSelf: 'stretch',
    backgroundColor: 'coral',
    borderRadius: 20,
  },
  appNameContainer: {
    height: 40,
    // backgroundColor: 'red',
    alignItems: 'center',
    justifyContent: 'center',
  },
  appName: {
    fontSize: 16,
    fontWeight: 'bold',
  },
});

export default React.memo(AuthorisationAppLogo);
