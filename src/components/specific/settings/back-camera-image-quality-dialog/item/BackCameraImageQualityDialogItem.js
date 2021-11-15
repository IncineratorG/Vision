import React, {useMemo, useCallback} from 'react';
import {View, Text, TouchableOpacity, StyleSheet} from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons';

const BackCameraImageQualityDialogItem = ({
  text,
  imageQualityType,
  isSelected,
  onPress,
}) => {
  const onPressHandler = useCallback(() => {
    onPress({imageQualityType});
  }, [imageQualityType, onPress]);

  const radioButtonCheckedIcon = useMemo(() => {
    return <Icon name="radio-button-checked" size={24} color={'#018786'} />;
  }, []);
  const radioButtonUncheckedIcon = useMemo(() => {
    return <Icon name="radio-button-unchecked" size={24} color={'grey'} />;
  }, []);

  return (
    <TouchableOpacity onPress={onPressHandler}>
      <View style={styles.mainContainer}>
        <View style={styles.checkmarkIconContainer}>
          {isSelected ? radioButtonCheckedIcon : radioButtonUncheckedIcon}
        </View>
        <View style={styles.textContainer}>
          <Text style={styles.text}>{text}</Text>
        </View>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    height: 50,
    flexDirection: 'row',
  },
  checkmarkIconContainer: {
    width: 50,
    alignItems: 'center',
    justifyContent: 'center',
  },
  textContainer: {
    flex: 1,
    justifyContent: 'center',
  },
  text: {
    fontSize: 18,
  },
});

export default React.memo(BackCameraImageQualityDialogItem);
