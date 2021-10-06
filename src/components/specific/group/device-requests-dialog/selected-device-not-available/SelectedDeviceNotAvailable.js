import React from 'react';
import {View, Text, StyleSheet} from 'react-native';
import useTranslation from '../../../../../utils/common/localization';

const SelectedDeviceNotAvailable = () => {
  const {t} = useTranslation();

  return (
    <View style={styles.mainContainer}>
      <Text style={styles.messageText}>
        {t('SelectedDeviceNotAvailable_message')}
      </Text>
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  messageText: {
    fontSize: 16,
  },
});

export default React.memo(SelectedDeviceNotAvailable);
