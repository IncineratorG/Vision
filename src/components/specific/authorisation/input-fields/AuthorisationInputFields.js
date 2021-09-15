import React, {useState, useMemo, useCallback} from 'react';
import {View, StyleSheet} from 'react-native';
import AuthorisationInputField from './input-field/AuthorisationInputField';
import useTranslation from '../../../../utils/common/localization';
import MaterialIcon from 'react-native-vector-icons/MaterialIcons';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';

const AuthorisationInputFields = () => {
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

  const groupNameChangeTextHandler = useCallback((text) => {
    setInnerGroupName(text);
  }, []);
  const groupNameSubmitEditingPressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({info: 'groupNameSubmitEditingPressHandler()'});
  }, []);

  const groupPasswordChangeTextHandler = useCallback((text) => {
    setInnerGroupPassword(text);
  }, []);
  const groupPasswordSubmitEditingPressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'groupPasswordSubmitEditingPressHandler()',
    });
  }, []);

  const deviceNameChangeTextHandler = useCallback((text) => {
    setInnerDeviceName(text);
  }, []);
  const deviceNameSubmitEditingPressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'deviceNameSubmitEditingPressHandler()',
    });
  }, []);

  const groupNameInputFieldComponent = (
    <AuthorisationInputField
      icon={groupNameIcon}
      value={innerGroupName}
      placeholder={t('AuthorisationInputFields_groupNameFieldPlaceholder')}
      onChangeText={groupNameChangeTextHandler}
      onSubmitEditing={groupNameSubmitEditingPressHandler}
    />
  );
  const groupPasswordInputFieldComponent = (
    <AuthorisationInputField
      icon={groupPasswordIcon}
      value={innerGroupPassword}
      placeholder={t('AuthorisationInputFields_groupPasswordFieldPlaceholder')}
      onChangeText={groupPasswordChangeTextHandler}
      onSubmitEditing={groupPasswordSubmitEditingPressHandler}
    />
  );
  const deviceNameInputFieldComponent = (
    <AuthorisationInputField
      icon={deviceNameIcon}
      value={innerDeviceName}
      placeholder={t('AuthorisationInputFields_deviceNameFieldPlaceholder')}
      onChangeText={deviceNameChangeTextHandler}
      onSubmitEditing={deviceNameSubmitEditingPressHandler}
    />
  );

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
