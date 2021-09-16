import React, {useCallback} from 'react';
import {View, StyleSheet} from 'react-native';
import {Button, Dialog, Portal} from 'react-native-paper';
import useTranslation from '../../../../utils/common/localization';
import DeviceRequestsDialogRequestsList from './requests-list/DeviceRequestsDialogRequestsList';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';

const DeviceRequestsDialog = ({visible, onCancelPress}) => {
  const {t} = useTranslation();

  const requestPressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'DeviceRequestsDialog->requestPressHandler()',
    });
  }, []);

  // const requestsListComponent = <DeviceRequestsDialogRequestsList />;
  const requestsListComponent = null;

  return (
    <Portal>
      <Dialog visible={visible} onDismiss={onCancelPress}>
        <Dialog.Content>
          <View style={styles.mainContainer}>{requestsListComponent}</View>
        </Dialog.Content>
        <Dialog.Actions>
          <Button onPress={onCancelPress}>
            {t('DeviceRequestDialog_cancelButton')}
          </Button>
        </Dialog.Actions>
      </Dialog>
    </Portal>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    minHeight: 250,
    justifyContent: 'center',
  },
});

export default React.memo(DeviceRequestsDialog);
