import React from 'react';
import {View, Text, StyleSheet} from 'react-native';
import {Button, Dialog, Portal} from 'react-native-paper';

const CurrentRequestStatusDialog = ({
  visible,
  statusText,
  leftButtonVisible,
  leftButtonText,
  leftButtonPressHandler,
  rightButtonVisible,
  rightButtonText,
  rightButtonPressHandler,
  onCancel,
}) => {
  const leftButton = leftButtonVisible ? (
    <Button onPress={leftButtonPressHandler}>{leftButtonText}</Button>
  ) : null;
  const rightButton = rightButtonVisible ? (
    <Button onPress={rightButtonPressHandler}>{rightButtonText}</Button>
  ) : null;

  return (
    <Portal>
      <Dialog visible={visible} onDismiss={onCancel}>
        <Dialog.Content>
          <View style={styles.mainContainer}>
            <Text style={styles.messageText}>{statusText}</Text>
          </View>
        </Dialog.Content>
        <Dialog.Actions>
          {leftButton}
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

export default React.memo(CurrentRequestStatusDialog);
