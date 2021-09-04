import React, {useState, useCallback, useRef, useEffect} from 'react';
import {View, Text, TextInput, StyleSheet} from 'react-native';
import {Button, Dialog, Portal} from 'react-native-paper';
import useTranslation from '../../../../utils/common/localization';

const RegisterUserGroupDialog = ({visible, onCreatePress, onCancelPress}) => {
  const {t} = useTranslation();

  const passwordInputRef = useRef(null);

  const [groupLogin, setGroupLogin] = useState('');
  const [groupPassword, setGroupPassword] = useState('');

  const createPressHandler = useCallback(() => {
    if (onCreatePress) {
      onCreatePress({login: groupLogin, password: groupPassword});
    }
  }, [groupLogin, groupPassword, onCreatePress]);

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
    createPressHandler();
  }, [createPressHandler]);

  useEffect(() => {
    if (!visible) {
      setGroupLogin('');
      setGroupPassword('');
    }
  }, [visible]);

  return (
    <Portal>
      <Dialog visible={visible} onDismiss={onCancelPress}>
        <Dialog.Title>{t('RegisterUserGroupDialog_title')}</Dialog.Title>
        <Dialog.Content>
          <View style={styles.mainContainer}>
            <View style={styles.groupLoginContainer}>
              <TextInput
                style={{
                  fontSize: 18,
                  color: '#000000',
                  borderBottomColor: 'transparent',
                }}
                placeholder={t('RegisterUserGroupDialog_groupLoginPlaceholder')}
                defaultValue={groupLogin}
                underlineColorAndroid={'transparent'}
                spellCheck={false}
                autoCorrect={false}
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
                placeholder={t(
                  'RegisterUserGroupDialog_groupPasswordPlaceholder',
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
          </View>
        </Dialog.Content>
        <Dialog.Actions>
          <Button onPress={createPressHandler}>
            {t('RegisterUserGroupDialog_createButton')}
          </Button>
          <Button onPress={cancelPressHandler}>
            {t('RegisterUserGroupDialog_cancelButton')}
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

export default React.memo(RegisterUserGroupDialog);
