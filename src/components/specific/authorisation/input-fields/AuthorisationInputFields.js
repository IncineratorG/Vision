import React, {useState, useMemo, useCallback, useEffect} from 'react';
import {View, StyleSheet} from 'react-native';
import AuthorisationInputField from './input-field/AuthorisationInputField';
import useTranslation from '../../../../utils/common/localization';
import MaterialIcon from 'react-native-vector-icons/MaterialIcons';

const AuthorisationInputFields = ({
  groupName,
  groupPassword,
  deviceName,
  forceGroupNameFieldFocus,
  forceGroupPasswordFieldFocus,
  forceDeviceNameFieldFocus,
  onGroupNameChange,
  onGroupPasswordChange,
  onDeviceNameChange,
  onGroupNameSubmitEditing,
  onGroupPasswordSubmitEditing,
  onDeviceNameSubmitEditing,
}) => {
  const {t} = useTranslation();

  const [innerGroupName, setInnerGroupName] = useState('');
  const [innerGroupPassword, setInnerGroupPassword] = useState('');
  const [innerDeviceName, setInnerDeviceName] = useState('');

  const groupNameIcon = useMemo(() => {
    return <MaterialIcon name="dns" size={22} color="grey" />;
  }, []);
  const groupPasswordIcon = useMemo(() => {
    return <MaterialIcon name="vpn-key" size={22} color="grey" />;
  }, []);
  const deviceNameIcon = useMemo(() => {
    return (
      <MaterialIcon name="perm-device-information" size={22} color="grey" />
    );
  }, []);

  const groupNameChangeTextHandler = useCallback(
    (text) => {
      if (onGroupNameChange) {
        onGroupNameChange(text);
      }
    },
    [onGroupNameChange],
  );
  const groupNameSubmitEditingPressHandler = useCallback(() => {
    if (onGroupNameSubmitEditing) {
      onGroupNameSubmitEditing();
    }
  }, [onGroupNameSubmitEditing]);

  const groupPasswordChangeTextHandler = useCallback(
    (text) => {
      if (onGroupPasswordChange) {
        onGroupPasswordChange(text);
      }
    },
    [onGroupPasswordChange],
  );
  const groupPasswordSubmitEditingPressHandler = useCallback(() => {
    if (onGroupPasswordSubmitEditing) {
      onGroupPasswordSubmitEditing();
    }
  }, [onGroupPasswordSubmitEditing]);

  const deviceNameChangeTextHandler = useCallback(
    (text) => {
      if (onDeviceNameChange) {
        onDeviceNameChange(text);
      }
    },
    [onDeviceNameChange],
  );
  const deviceNameSubmitEditingPressHandler = useCallback(() => {
    if (onDeviceNameSubmitEditing) {
      onDeviceNameSubmitEditing();
    }
  }, [onDeviceNameSubmitEditing]);

  const groupNameInputFieldComponent = (
    <AuthorisationInputField
      icon={groupNameIcon}
      value={innerGroupName}
      forceFocus={forceGroupNameFieldFocus}
      placeholder={t('AuthorisationInputFields_groupNameFieldPlaceholder')}
      onChangeText={groupNameChangeTextHandler}
      onSubmitEditing={groupNameSubmitEditingPressHandler}
    />
  );
  const groupPasswordInputFieldComponent = (
    <AuthorisationInputField
      icon={groupPasswordIcon}
      value={innerGroupPassword}
      forceFocus={forceGroupPasswordFieldFocus}
      placeholder={t('AuthorisationInputFields_groupPasswordFieldPlaceholder')}
      onChangeText={groupPasswordChangeTextHandler}
      onSubmitEditing={groupPasswordSubmitEditingPressHandler}
    />
  );
  const deviceNameInputFieldComponent = (
    <AuthorisationInputField
      icon={deviceNameIcon}
      value={innerDeviceName}
      forceFocus={forceDeviceNameFieldFocus}
      placeholder={t('AuthorisationInputFields_deviceNameFieldPlaceholder')}
      onChangeText={deviceNameChangeTextHandler}
      onSubmitEditing={deviceNameSubmitEditingPressHandler}
    />
  );

  useEffect(() => {
    setInnerGroupName(groupName);
  }, [groupName]);

  useEffect(() => {
    setInnerGroupPassword(groupPassword);
  }, [groupPassword]);

  useEffect(() => {
    setInnerDeviceName(deviceName);
  }, [deviceName]);

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
    // backgroundColor: 'mediumturquoise',
    backgroundColor: 'white',
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
