import React, {useCallback} from 'react';
import {View, Text, StyleSheet} from 'react-native';
import {Button, Dialog, Portal} from 'react-native-paper';
import useTranslation from '../../../../utils/common/localization';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';

const RequestStatusDialog = ({
  visible,
  completed,
  responseData,
  canViewResponse,
  responseViewerCallback,
  onCancelPress,
}) => {
  const {t} = useTranslation();

  const messageText = completed
    ? t('RequestStatusDialog_requestCompletedMessage')
    : t('RequestStatusDialog_requestInProgressMessage');

  const viewResultHandler = useCallback(() => {
    if (responseViewerCallback) {
      responseViewerCallback();
    }
    // SystemEventsHandler.onInfo({
    //   info:
    //     'RequestStatusDialog->viewResultHandler(): ' +
    //     JSON.stringify(responseData),
    // });
  }, [responseViewerCallback]);

  const resultButton = canViewResponse ? (
    <Button onPress={viewResultHandler}>
      {t('RequestStatusDialog_viewResponseButton')}
    </Button>
  ) : null;

  const notResultButton = completed ? (
    <Button onPress={onCancelPress}>{t('RequestStatusDialog_okButton')}</Button>
  ) : (
    <Button onPress={onCancelPress}>
      {t('RequestStatusDialog_cancelButton')}
    </Button>
  );

  return (
    <Portal>
      <Dialog visible={visible} onDismiss={onCancelPress}>
        <Dialog.Content>
          <View style={styles.mainContainer}>
            <Text style={styles.messageText}>{messageText}</Text>
          </View>
        </Dialog.Content>
        <Dialog.Actions>
          {resultButton}
          <View style={styles.freeSpace} />
          {notResultButton}
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

export default React.memo(RequestStatusDialog);
