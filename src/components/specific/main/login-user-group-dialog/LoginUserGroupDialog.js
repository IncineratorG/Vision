import React, {useState, useCallback, useRef, useEffect} from 'react';
import {View, Text, TextInput, StyleSheet} from 'react-native';
import {Button, Dialog, Portal} from 'react-native-paper';
import useTranslation from '../../../../utils/common/localization';

const LoginUserGroupDialog = ({
  visible,
  onLoginPress,
  onRegisterPress,
  onCancelPress,
}) => {
  const {t} = useTranslation();

  const passwordInputRef = useRef(null);

  const [groupLogin, setGroupLogin] = useState('');
  const [groupPassword, setGroupPassword] = useState('');

  const loginPressHandler = useCallback(() => {
    if (onLoginPress) {
      onLoginPress({login: groupLogin, password: groupPassword});
    }
  }, [groupLogin, groupPassword, onLoginPress]);

  const cancelPressHandler = useCallback(() => {
    if (onCancelPress) {
      onCancelPress();
    }
  }, [onCancelPress]);

  const groupLoginChangeHandler = useCallback((text) => {
    setGroupLogin(text);
  }, []);

  const groupPasswordChangeHandler = useCallback((text) => {
    setGroupPassword(text);
  }, []);

  const groupLoginSubmitEditingPressHandler = useCallback(() => {
    passwordInputRef.current.focus();
  }, []);

  const groupPasswordSubmitEditingPressHandler = useCallback(() => {
    loginPressHandler();
  }, [loginPressHandler]);

  useEffect(() => {
    if (!visible) {
      setGroupLogin('');
      setGroupPassword('');
    }
  }, [visible]);

  return (
    <Portal>
      <Dialog visible={visible} onDismiss={onCancelPress}>
        <Dialog.Title>{t('LoginUserGroupDialog_title')}</Dialog.Title>
        <Dialog.Content>
          <View style={styles.mainContainer}>
            <View style={styles.groupLoginContainer}>
              <TextInput
                style={{
                  fontSize: 18,
                  color: '#000000',
                  borderBottomColor: 'transparent',
                }}
                placeholder={t('LoginUserGroupDialog_groupLoginPlaceholder')}
                defaultValue={groupLogin}
                underlineColorAndroid={'transparent'}
                onChangeText={groupLoginChangeHandler}
                onSubmitEditing={groupLoginSubmitEditingPressHandler}
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
                placeholder={t('LoginUserGroupDialog_groupPasswordPlaceholder')}
                defaultValue={groupPassword}
                underlineColorAndroid={'transparent'}
                onChangeText={groupPasswordChangeHandler}
                onSubmitEditing={groupPasswordSubmitEditingPressHandler}
              />
            </View>
            <View style={styles.underline} />
          </View>
        </Dialog.Content>
        <Dialog.Actions>
          <Button onPress={onRegisterPress}>
            {t('LoginUserGroupDialog_registerButton')}
          </Button>
          <View style={{flex: 1}} />
          <Button onPress={loginPressHandler}>
            {t('LoginUserGroupDialog_loginButton')}
          </Button>
          <Button onPress={cancelPressHandler}>
            {t('LoginUserGroupDialog_cancelButton')}
          </Button>
        </Dialog.Actions>
      </Dialog>
    </Portal>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    minHeight: 150,
    // backgroundColor: 'green',
  },
  underline: {
    height: 1,
    backgroundColor: 'black',
  },
  groupLoginContainer: {
    minHeight: 50,
  },
  groupPasswordContainer: {
    minHeight: 50,
  },
});

export default React.memo(LoginUserGroupDialog);
