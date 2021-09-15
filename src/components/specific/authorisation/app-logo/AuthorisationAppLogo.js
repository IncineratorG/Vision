import React from 'react';
import {View, Text, StyleSheet} from 'react-native';
import MaterialIcon from 'react-native-vector-icons/MaterialIcons';
import useTranslation from '../../../../utils/common/localization';

const AuthorisationAppLogo = () => {
  const {t} = useTranslation();

  return (
    <View style={styles.mainContainer}>
      <View style={styles.logoOuterContainer}>
        <View style={styles.logoContainer}>
          <MaterialIcon name="camera" size={96} color="#0098ef" />
        </View>
        <View style={styles.appNameContainer}>
          <Text style={styles.appName}>{t('AppName')}</Text>
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    // backgroundColor: 'oldlace',
    backgroundColor: 'white',
    justifyContent: 'center',
    alignItems: 'center',
  },
  logoOuterContainer: {
    height: 200,
    width: 180,
  },
  logoContainer: {
    flex: 1,
    alignSelf: 'stretch',
    // backgroundColor: 'coral',
    // backgroundColor: '#0098ef',
    borderRadius: 20,
    alignItems: 'center',
    justifyContent: 'flex-end',
  },
  appNameContainer: {
    height: 40,
    alignItems: 'center',
    justifyContent: 'center',
  },
  appName: {
    fontSize: 16,
    fontWeight: 'bold',
    color: 'grey',
  },
});

export default React.memo(AuthorisationAppLogo);
