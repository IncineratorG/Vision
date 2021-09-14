import React, {useState, useCallback, useRef, useEffect} from 'react';
import {View, Text, TextInput, StyleSheet} from 'react-native';
import {Button, Dialog, Portal} from 'react-native-paper';
import useTranslation from '../../../../utils/common/localization';

const LoginDeviceInGroupDialog = ({visible, onLoginPress, onCancelPress}) => {
  const {t} = useTranslation();

  const passwordInputRef = useRef(null);
  const deviceNameInputRef = useRef(null);

  const [groupName, setGroupName] = useState('');
  const [groupPassword, setGroupPassword] = useState('');
  const [deviceName, setDeviceName] = useState('');

  const loginPressHandler = () => {
    if (onLoginPress) {
      onLoginPress({
        groupName,
        groupPassword,
        deviceName,
      });
    }
  };

  const cancelPressHandler = useCallback(() => {
    if (onCancelPress) {
      onCancelPress();
    }
  }, [onCancelPress]);

  const groupNameChangeHandler = useCallback((text) => {
    setGroupName(text);
  }, []);

  const groupPasswordChangeHandler = useCallback((text) => {
    setGroupPassword(text);
  }, []);

  const deviceNameChangeHandler = useCallback((text) => {
    setDeviceName(text);
  }, []);

  const groupNameSubmitEditingPressHandler = useCallback(() => {
    passwordInputRef.current.focus();
  }, []);

  const groupPasswordSubmitEditingPressHandler = useCallback(() => {
    deviceNameInputRef.current.focus();
  }, []);

  const deviceNameSubmitEditingPressHandler = () => {
    loginPressHandler();
  };

  useEffect(() => {
    if (!visible) {
      setGroupName('');
      setGroupPassword('');
      setDeviceName('');
    }
  }, [visible]);

  return (
    <Portal>
      <Dialog visible={visible} onDismiss={onCancelPress}>
        <Dialog.Title>{t('LoginDeviceInGroupDialog_title')}</Dialog.Title>
        <Dialog.Content>
          <View style={styles.mainContainer}>
            <View style={styles.groupNameContainer}>
              <TextInput
                style={{
                  fontSize: 18,
                  color: '#000000',
                  borderBottomColor: 'transparent',
                }}
                placeholder={t('LoginDeviceInGroupDialog_groupNamePlaceholder')}
                defaultValue={groupName}
                underlineColorAndroid={'transparent'}
                spellCheck={false}
                autoCorrect={false}
                onChangeText={groupNameChangeHandler}
                onSubmitEditing={groupNameSubmitEditingPressHandler}
              />
            </View>
            <View style={styles.underline} />
            <View style={styles.groupPasswordContainer}>
              <TextInput
                ref={passwordInputRef}
                style={{
                  fontSize: 18,
                  color: '#000000',
                  borderBottomColor: 'transparent',
                }}
                placeholder={t(
                  'LoginDeviceInGroupDialog_groupPasswordPlaceholder',
                )}
                defaultValue={groupPassword}
                underlineColorAndroid={'transparent'}
                spellCheck={false}
                autoCorrect={false}
                onChangeText={groupPasswordChangeHandler}
                onSubmitEditing={groupPasswordSubmitEditingPressHandler}
              />
            </View>
            <View style={styles.underline} />
            <View style={styles.deviceNameContainer}>
              <TextInput
                ref={deviceNameInputRef}
                style={{
                  fontSize: 18,
                  color: '#000000',
                  borderBottomColor: 'transparent',
                }}
                placeholder={t(
                  'LoginDeviceInGroupDialog_deviceNamePlaceholder',
                )}
                defaultValue={deviceName}
                underlineColorAndroid={'transparent'}
                spellCheck={false}
                autoCorrect={false}
                onChangeText={deviceNameChangeHandler}
                onSubmitEditing={deviceNameSubmitEditingPressHandler}
              />
            </View>
            <View style={styles.underline} />
          </View>
        </Dialog.Content>
        <Dialog.Actions>
          <Button onPress={loginPressHandler}>
            {t('LoginDeviceInGroupDialog_loginButton')}
          </Button>
          <Button onPress={cancelPressHandler}>
            {t('LoginDeviceInGroupDialog_cancelButton')}
          </Button>
        </Dialog.Actions>
      </Dialog>
    </Portal>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    minHeight: 150,
  },
  underline: {
    height: 1,
    backgroundColor: 'black',
  },
  groupNameContainer: {
    minHeight: 50,
  },
  groupPasswordContainer: {
    minHeight: 50,
  },
  deviceNameContainer: {
    minHeight: 50,
  },
});

export default React.memo(LoginDeviceInGroupDialog);
