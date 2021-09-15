import React from 'react';
import {View, Text, StyleSheet} from 'react-native';
import {Button, Dialog, Portal} from 'react-native-paper';
import useTranslation from '../../../../utils/common/localization';

const NeedCreateGroupDialog = ({visible, onCreatePress, onCancelPress}) => {
  const {t} = useTranslation();

  return (
    <Portal>
      <Dialog visible={visible} onDismiss={onCancelPress}>
        <Dialog.Content>
          <View style={styles.mainContainer}>
            <View style={styles.messageContainer}>
              <View style={styles.messageTextContainer}>
                <Text style={styles.messageText}>
                  {t('NeedCreateGroupDialog_message')}
                </Text>
              </View>
            </View>
          </View>
        </Dialog.Content>
        <Dialog.Actions>
          <Button onPress={onCreatePress}>
            {t('NeedCreateGroupDialog_createButton')}
          </Button>
          <Button onPress={onCancelPress}>
            {t('NeedCreateGroupDialog_cancelButton')}
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
  messageContainer: {
    minHeight: 50,
    alignSelf: 'stretch',
    flexDirection: 'row',
  },
  messageTextContainer: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  messageText: {
    fontSize: 16,
  },
});

export default React.memo(NeedCreateGroupDialog);
