import React from 'react';
import {StyleSheet, Text, View} from 'react-native';
import useTranslation from '../../../../../utils/common/localization';
import Icon from 'react-native-vector-icons/Feather';

const CheckingSelectedDevice = () => {
  const {t} = useTranslation();

  return (
    <View style={styles.mainContainer}>
      <View style={styles.messageContainer}>
        <View style={styles.progressIconContainer}>
          <Icon name="loader" size={24} color={'grey'} />
        </View>
        <View style={styles.messageTextContainer}>
          <Text style={styles.messageText}>
            {t('CheckingSelectedDevice_message')}
          </Text>
        </View>
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
  messageContainer: {
    minHeight: 50,
    alignSelf: 'stretch',
    flexDirection: 'row',
  },
  progressIconContainer: {
    width: 50,
    alignSelf: 'stretch',
    alignItems: 'center',
    justifyContent: 'center',
  },
  messageTextContainer: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    marginLeft: 8,
  },
  messageText: {
    fontSize: 16,
  },
});

export default React.memo(CheckingSelectedDevice);
