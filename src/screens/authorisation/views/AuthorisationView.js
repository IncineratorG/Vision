import React from 'react';
import {View, TextInput, StyleSheet} from 'react-native';
import AuthorisationAppLogo from '../../../components/specific/authorisation/app-logo/AuthorisationAppLogo';

const AuthorisationView = ({model, controller}) => {
  const appLogoComponent = <AuthorisationAppLogo />;

  return (
    <View style={styles.mainContainer}>
      <View style={styles.topContainer}>{appLogoComponent}</View>
      <View style={styles.inputFieldsContainer}>
        <View style={styles.inputFieldsInnerContainer}>
          <View style={styles.inputFieldItemContainer}>
            <View style={styles.groupNameInputFieldContainer} />
          </View>
          <View style={styles.inputFieldItemContainer} />
          <View style={styles.inputFieldItemContainer} />
        </View>
      </View>
      <View style={styles.buttonsContainer} />
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    backgroundColor: 'white',
  },
  freeSpace: {
    flex: 1,
  },
  topContainer: {
    flex: 1,
    backgroundColor: 'red',
  },
  appLogoContainer: {
    height: '50%',
    width: '50%',
    backgroundColor: 'yellow',
  },
  inputFieldsContainer: {
    flex: 1,
    backgroundColor: 'green',
  },
  inputFieldsInnerContainer: {
    flex: 1,
    backgroundColor: 'white',
    margin: 10,
    alignItems: 'center',
    justifyContent: 'center',
  },
  inputFieldItemContainer: {
    flex: 1,
    alignSelf: 'stretch',
    backgroundColor: 'grey',
    borderWidth: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  groupNameInputFieldContainer: {
    backgroundColor: 'purple',
    height: 50,
    maxWidth: 300,
    minWidth: 100,
  },
  groupPasswordInputFieldContainer: {
    backgroundColor: 'cyan',
    height: 50,
    alignSelf: 'stretch',
    marginTop: 10,
    marginBottom: 10,
  },
  deviceNameInputFieldContainer: {
    backgroundColor: 'yellow',
    height: 50,
    alignSelf: 'stretch',
  },
  buttonsContainer: {
    flex: 1,
    backgroundColor: 'blue',
  },
});

export default React.memo(AuthorisationView);
