import React, {useCallback, useEffect} from 'react';
import {View, Text, StyleSheet} from 'react-native';
import {Button, Dialog, Portal} from 'react-native-paper';
import useTranslation from '../../../../utils/common/localization';

const SelectedDeviceErrorDialog = ({visible, errorText, onCancelPress}) => {
  const {t} = useTranslation();

  return (
    <Portal>
      <Dialog visible={visible} onDismiss={onCancelPress}>
        <Dialog.Content>
          <View style={styles.mainContainer}>
            <Text style={styles.messageText}>{errorText}</Text>
          </View>
        </Dialog.Content>
        <Dialog.Actions>
          <Button onPress={onCancelPress}>
            {t('SelectedDeviceErrorDialog_cancelButton')}
          </Button>
        </Dialog.Actions>
      </Dialog>
    </Portal>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    minHeight: 50,
    justifyContent: 'center',
  },
  messageText: {
    fontSize: 16,
  },
});

export default React.memo(SelectedDeviceErrorDialog);
