import React from 'react';
import {View, StyleSheet} from 'react-native';
import {List} from 'react-native-paper';
import useTranslation from '../../../utils/common/localization';
import CameraImageQuality from '../../../data/common/camera-image-quality/CameraImageQuality';
import BackCameraImageQualityDialog from '../../../components/specific/settings/back-camera-image-quality-dialog/BackCameraImageQualityDialog';

const SettingsView = ({model, controller}) => {
  const {t} = useTranslation();

  const {
    data: {backCameraImageQuality, backCameraImageQualityDialogVisible},
  } = model;

  const {
    backCameraImageQualityPressHandler,
    backCameraImageQualityDialogCancelPressHandler,
    backCameraImageQualityDialogImageQualitySelectHandler,
  } = controller;

  const backCameraImageQualityDialog = (
    <BackCameraImageQualityDialog
      visible={backCameraImageQualityDialogVisible}
      currentImageQualityType={backCameraImageQuality}
      onImageQualityTypeSelect={
        backCameraImageQualityDialogImageQualitySelectHandler
      }
      onCancel={backCameraImageQualityDialogCancelPressHandler}
    />
  );

  return (
    <View style={styles.mainContainer}>
      <List.Section>
        <List.Subheader>
          {t('SettingsView_imageQualitySubheader')}
        </List.Subheader>
        <List.Item
          style={{borderBottomColor: 'lightgrey', borderBottomWidth: 1}}
          title={t('SettingsView_backCameraImageQuality')}
          description={
            backCameraImageQuality === CameraImageQuality.HIGH
              ? t('SettingsView_backCameraImageQualityHigh')
              : backCameraImageQuality === CameraImageQuality.MEDIUM
              ? t('SettingsView_backCameraImageQualityMedium')
              : backCameraImageQuality === CameraImageQuality.LOW
              ? t('SettingsView_backCameraImageQualityLow')
              : t('SettingsView_backCameraImageQualityUnknown')
          }
          onPress={backCameraImageQualityPressHandler}
        />
      </List.Section>
      {backCameraImageQualityDialog}
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    backgroundColor: 'white',
  },
});

export default React.memo(SettingsView);
