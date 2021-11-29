import React, {useCallback, useEffect, useState} from 'react';
import {View, Text, Image, StyleSheet} from 'react-native';
import {Button, Dialog, Portal} from 'react-native-paper';
import useTranslation from '../../../../utils/common/localization';

const CameraRecognizePersonSettingsDialog = ({
  visible,
  image,
  confirmSettingsButtonPressHandler,
  cancelButtonPressHandler,
  onCancel,
}) => {
  const {t} = useTranslation();

  const [imageRotationDeg, setImageRotationDeg] = useState(0);
  const [imageRotationDegStyleValue, setImageRotationDegStyleValue] =
    useState('0deg');
  const [imageSource, setImageSource] = useState(null);

  const rotateImageButtonPressHandler = useCallback(() => {
    let imageRotationDegValue = imageRotationDeg;
    if (imageRotationDegValue >= 270) {
      imageRotationDegValue = 0;
    } else {
      imageRotationDegValue = imageRotationDegValue + 90;
    }

    setImageRotationDeg(imageRotationDegValue);
    setImageRotationDegStyleValue(imageRotationDegValue + 'deg');
  }, [imageRotationDeg]);

  const confirmSettingsButtonPress = useCallback(() => {
    if (confirmSettingsButtonPressHandler) {
      confirmSettingsButtonPressHandler({imageRotationDeg});
    }
  }, [imageRotationDeg, confirmSettingsButtonPressHandler]);

  const rotateButton = (
    <Button onPress={rotateImageButtonPressHandler}>
      {t('CameraRecognizePersonSettingsDialog_rotateButtonText')}
    </Button>
  );

  const confirmSettingsButton = (
    <Button onPress={confirmSettingsButtonPress}>
      {t('CameraRecognizePersonSettingsDialog_confirmButtonText')}
    </Button>
  );

  const cancelButton = (
    <Button onPress={cancelButtonPressHandler}>
      {t('CameraRecognizePersonSettingsDialog_cancelButtonText')}
    </Button>
  );

  useEffect(() => {
    if (image) {
      setImageSource({uri: 'data:image/jpg;base64,' + image});
    } else {
      setImageSource(null);
    }
  }, [image]);

  useEffect(() => {
    if (!visible) {
      setImageRotationDeg(0);
      setImageRotationDegStyleValue('0deg');
    }
  }, [visible]);

  return (
    <Portal>
      <Dialog visible={visible} onDismiss={onCancel}>
        <Dialog.Content>
          <View style={styles.mainContainer}>
            <View style={styles.messageContainer}>
              <Text style={styles.message}>
                {'Поверните изображение так, чтобы оно стало вертикальным.'}
              </Text>
            </View>
            <View style={styles.imageContainer}>
              <Image
                style={[
                  styles.image,
                  {transform: [{rotate: imageRotationDegStyleValue}]},
                ]}
                source={imageSource}
              />
            </View>
          </View>
        </Dialog.Content>
        <Dialog.Actions>
          {rotateButton}
          <View style={styles.freeSpace} />
          {confirmSettingsButton}
          {cancelButton}
        </Dialog.Actions>
      </Dialog>
    </Portal>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    minHeight: 300,
    alignItems: 'center',
    justifyContent: 'center',
  },
  messageContainer: {
    height: 75,
    alignSelf: 'stretch',
    // backgroundColor: 'orange',
  },
  message: {
    fontSize: 16,
    color: 'grey',
  },
  imageContainer: {
    height: 300,
    alignSelf: 'stretch',
  },
  image: {
    flex: 1,
    alignSelf: 'stretch',
    transform: [{rotate: '0deg'}],
  },
  freeSpace: {
    flex: 1,
  },
});

export default React.memo(CameraRecognizePersonSettingsDialog);
