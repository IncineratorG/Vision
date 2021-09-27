import React, {useCallback} from 'react';
import {View, StyleSheet} from 'react-native';
import {Dialog, Button, Portal} from 'react-native-paper';
import useTranslation from '../../../../utils/common/localization';
import BackCameraImageQualityDialogItem from './item/BackCameraImageQualityDialogItem';
import CameraImageQuality from '../../../../data/common/camera-image-quality/CameraImageQuality';

const BackCameraImageQualityDialog = ({
  visible,
  currentImageQualityType,
  onImageQualityTypeSelect,
  onCancel,
}) => {
  const {t} = useTranslation();

  const imageQualityItemPressHandler = useCallback(
    ({imageQualityType}) => {
      if (onImageQualityTypeSelect) {
        onImageQualityTypeSelect({quality: imageQualityType});
      }
    },
    [onImageQualityTypeSelect],
  );

  return (
    <Portal>
      <Dialog visible={visible} onDismiss={onCancel}>
        <Dialog.Title>{t('BackCameraImageQualityDialog_title')}</Dialog.Title>
        <Dialog.Content>
          <View style={styles.mainContainer}>
            <BackCameraImageQualityDialogItem
              text={t('BackCameraImageQualityDialog_qualityHigh')}
              imageQualityType={CameraImageQuality.HIGH}
              isSelected={currentImageQualityType === CameraImageQuality.HIGH}
              onPress={imageQualityItemPressHandler}
            />
            <BackCameraImageQualityDialogItem
              text={t('BackCameraImageQualityDialog_qualityMedium')}
              imageQualityType={CameraImageQuality.MEDIUM}
              isSelected={currentImageQualityType === CameraImageQuality.MEDIUM}
              onPress={imageQualityItemPressHandler}
            />
            <BackCameraImageQualityDialogItem
              text={t('BackCameraImageQualityDialog_qualityLow')}
              imageQualityType={CameraImageQuality.LOW}
              isSelected={currentImageQualityType === CameraImageQuality.LOW}
              onPress={imageQualityItemPressHandler}
            />
          </View>
        </Dialog.Content>
        <Dialog.Actions>
          <Button onPress={onCancel}>
            {t('BackCameraImageQualityDialog_cancelButton')}
          </Button>
        </Dialog.Actions>
      </Dialog>
    </Portal>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    minHeight: 100,
  },
});

export default React.memo(BackCameraImageQualityDialog);
