import React from 'react';
import {View, Text, StyleSheet} from 'react-native';
import useTranslation from '../../../../utils/common/localization';

const DeviceNotAvailable = () => {
  const {t} = useTranslation();

  return (
    <View style={styles.mainContainer}>
      <View style={styles.messageContainer}>
        <Text style={styles.messageText}>
          {t('DeviceNotAvailable_messageText')}
        </Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  messageContainer: {},
  messageText: {
    fontSize: 16,
    color: 'grey',
  },
});

export default React.memo(DeviceNotAvailable);
