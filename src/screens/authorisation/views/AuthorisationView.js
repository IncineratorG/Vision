import React from 'react';
import {View, StyleSheet} from 'react-native';
import AuthorisationAppLogo from '../../../components/specific/authorisation/app-logo/AuthorisationAppLogo';
import AuthorisationInputFields from '../../../components/specific/authorisation/input-fields/AuthorisationInputFields';
import AuthorisationButtons from '../../../components/specific/authorisation/buttons/AuthorisationButtons';

const AuthorisationView = ({model, controller}) => {
  const appLogoComponent = <AuthorisationAppLogo />;
  const inputFieldsComponent = <AuthorisationInputFields />;
  const buttonsComponent = <AuthorisationButtons />;

  return (
    <View style={styles.mainContainer}>
      <View style={styles.topContainer}>{appLogoComponent}</View>
      <View style={styles.inputFieldsContainer}>{inputFieldsComponent}</View>
      <View style={styles.buttonsContainer}>{buttonsComponent}</View>
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    // backgroundColor: 'white',
  },
  topContainer: {
    flex: 1,
    // backgroundColor: 'red',
  },
  inputFieldsContainer: {
    flex: 1,
    // backgroundColor: 'green',
  },
  buttonsContainer: {
    flex: 1,
    // backgroundColor: 'blue',
  },
});

export default React.memo(AuthorisationView);
