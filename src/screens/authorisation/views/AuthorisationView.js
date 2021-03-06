import React from 'react';
import {View, StyleSheet} from 'react-native';
import AuthorisationAppLogo from '../../../components/specific/authorisation/app-logo/AuthorisationAppLogo';
import AuthorisationInputFields from '../../../components/specific/authorisation/input-fields/AuthorisationInputFields';
import AuthorisationButtons from '../../../components/specific/authorisation/buttons/AuthorisationButtons';
import AuthorisationStatus from '../../../components/specific/authorisation/status/AuthorisationStatus';
import NeedCreateGroupDialog from '../../../components/specific/authorisation/need-create-group-dialog/NeedCreateGroupDialog';

const AuthorisationView = ({model, controller}) => {
  const {
    data: {
      authorisationModes,
      currentAuthorisationMode,
      authorisationStatus,
      authorisationStatusIsError,
      groupName,
      groupPassword,
      deviceName,
      forceGroupNameFieldFocus,
      forceGroupPasswordFieldFocus,
      forceDeviceNameFieldFocus,
      needCreateGroupDialogVisible,
    },
  } = model;

  const {
    loginButtonPressHandler,
    loginTextPressHandler,
    registerButtonPressHandler,
    registerTextPressHandler,
    groupNameChangeHandler,
    groupPasswordChangeHandler,
    deviceNameChangeHandler,
    groupNameSubmitEditingPressHandler,
    groupPasswordSubmitEditingPressHandler,
    deviceNameSubmitEditingPressHandler,
    needCreateGroupDialogCreatePressHandler,
    needCreateGroupDialogCancelPressHandler,
  } = controller;

  const appLogoComponent = <AuthorisationAppLogo />;
  const statusComponent = (
    <AuthorisationStatus
      show={!!authorisationStatus}
      text={authorisationStatus}
      isError={authorisationStatusIsError}
    />
  );
  const inputFieldsComponent = (
    <AuthorisationInputFields
      groupName={groupName}
      groupPassword={groupPassword}
      deviceName={deviceName}
      forceGroupNameFieldFocus={forceGroupNameFieldFocus}
      forceGroupPasswordFieldFocus={forceGroupPasswordFieldFocus}
      forceDeviceNameFieldFocus={forceDeviceNameFieldFocus}
      onGroupNameChange={groupNameChangeHandler}
      onGroupPasswordChange={groupPasswordChangeHandler}
      onDeviceNameChange={deviceNameChangeHandler}
      onGroupNameSubmitEditing={groupNameSubmitEditingPressHandler}
      onGroupPasswordSubmitEditing={groupPasswordSubmitEditingPressHandler}
      onDeviceNameSubmitEditing={deviceNameSubmitEditingPressHandler}
    />
  );
  const buttonsComponent = (
    <AuthorisationButtons
      loginMode={currentAuthorisationMode === authorisationModes.login}
      registerMode={currentAuthorisationMode === authorisationModes.register}
      onLoginButtonPress={loginButtonPressHandler}
      onLoginTextPress={loginTextPressHandler}
      onRegisterButtonPress={registerButtonPressHandler}
      onRegisterTextPress={registerTextPressHandler}
    />
  );

  const needCreateGroupDialog = (
    <NeedCreateGroupDialog
      visible={needCreateGroupDialogVisible}
      onCreatePress={needCreateGroupDialogCreatePressHandler}
      onCancelPress={needCreateGroupDialogCancelPressHandler}
    />
  );

  return (
    <View style={styles.mainContainer}>
      <View style={styles.topContainer}>{appLogoComponent}</View>
      <View style={styles.statusContainer}>{statusComponent}</View>
      <View style={styles.inputFieldsContainer}>{inputFieldsComponent}</View>
      <View style={styles.buttonsContainer}>{buttonsComponent}</View>
      {needCreateGroupDialog}
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
  statusContainer: {
    // height: 25,
    // backgroundColor: 'red',
    alignSelf: 'stretch',
    backgroundColor: 'white',
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
