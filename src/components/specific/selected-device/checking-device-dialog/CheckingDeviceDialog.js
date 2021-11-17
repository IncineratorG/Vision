import React from 'react';
import {View, Text, StyleSheet} from 'react-native';
import {Button, Dialog, Portal} from 'react-native-paper';
import useTranslation from '../../../../utils/common/localization';

const CheckingDeviceDialog = ({visible, onCancel}) => {
  const {t} = useTranslation();

  const rightButton = (
    <Button onPress={onCancel}>{t('CheckingDeviceDialog_cancelButton')}</Button>
  );

  return (
    <Portal>
      <Dialog visible={visible} onDismiss={onCancel}>
        <Dialog.Content>
          <View style={styles.mainContainer}>
            <Text style={styles.messageText}>
              {t('CheckingDeviceDialog_messageText')}
            </Text>
          </View>
        </Dialog.Content>
        <Dialog.Actions>
          <View style={styles.freeSpace} />
          {rightButton}
        </Dialog.Actions>
      </Dialog>
    </Portal>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    minHeight: 50,
    alignItems: 'center',
    justifyContent: 'center',
  },
  messageText: {
    fontSize: 16,
  },
  freeSpace: {
    flex: 1,
  },
});

export default React.memo(CheckingDeviceDialog);
