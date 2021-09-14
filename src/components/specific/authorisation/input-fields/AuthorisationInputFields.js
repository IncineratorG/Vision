import React from 'react';
import {View, StyleSheet} from 'react-native';
import AuthorisationInputField from './input-field/AuthorisationInputField';

const AuthorisationInputFields = () => {
  const groupNameInputFieldComponent = <AuthorisationInputField />;
  const groupPasswordInputFieldComponent = <AuthorisationInputField />;
  const deviceNameInputFieldComponent = <AuthorisationInputField />;

  return (
    <View style={styles.mainContainer}>
      <View style={styles.inputFieldsContainer}>
        {groupNameInputFieldComponent}
        {groupPasswordInputFieldComponent}
        {deviceNameInputFieldComponent}
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    backgroundColor: 'mediumturquoise',
  },
  inputFieldsContainer: {
    flex: 1,
    alignSelf: 'stretch',
    margin: 10,
    // backgroundColor: 'white',
    alignItems: 'center',
    justifyContent: 'space-evenly',
  },
});

export default React.memo(AuthorisationInputFields);
