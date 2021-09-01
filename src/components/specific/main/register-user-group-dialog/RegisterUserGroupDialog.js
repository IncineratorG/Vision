import React, {useState, useCallback, useRef, useEffect} from 'react';
import {View, Text, TextInput, StyleSheet} from 'react-native';
import {Button, Dialog, Portal} from 'react-native-paper';
import useTranslation from '../../../../utils/common/localization';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';

const RegisterUserGroupDialog = ({visible, onCancelPress}) => {
  const {t} = useTranslation();

  const createPressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'RegisterUserGroupDialog->createPressHandler()',
    });
  }, []);

  const cancelPressHandler = useCallback(() => {
    if (onCancelPress) {
      onCancelPress();
    }
  }, []);

  return (
    <Portal>
      <Dialog visible={visible} onDismiss={onCancelPress}>
        <Dialog.Title>{t('RegisterUserGroupDialog_title')}</Dialog.Title>
        <Dialog.Content>
          <View style={styles.mainContainer}></View>
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
    minHeight: 200,
    backgroundColor: 'green',
  },
});

export default React.memo(RegisterUserGroupDialog);
