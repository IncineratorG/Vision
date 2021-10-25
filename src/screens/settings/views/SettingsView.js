import React from 'react';
import {View, StyleSheet} from 'react-native';
import {List, Checkbox} from 'react-native-paper';
import useTranslation from '../../../utils/common/localization';
import CameraImageQuality from '../../../data/common/camera-image-quality/CameraImageQuality';
import BackCameraImageQualityDialog from '../../../components/specific/settings/back-camera-image-quality-dialog/BackCameraImageQualityDialog';
import FrontCameraImageQualityDialog from '../../../components/specific/settings/front-camera-image-quality-dialog/FrontCameraImageQualityDialog';

const SettingsView = ({model, controller}) => {
  const {t} = useTranslation();

  const {
    data: {
      backCameraImageQuality,
      backCameraImageQualityDialogVisible,
      frontCameraImageQuality,
      frontCameraImageQualityDialogVisible,
      receiveNotificationsFromCurrentGroup,
    },
  } = model;

  const {
    backCameraImageQualityPressHandler,
    backCameraImageQualityDialogCancelPressHandler,
    backCameraImageQualityDialogImageQualitySelectHandler,
    frontCameraImageQualityPressHandler,
    frontCameraImageQualityDialogCancelPressHandler,
    frontCameraImageQualityDialogImageQualitySelectHandler,
    receiveNotificationsFromCurrentGroupPressHandler,
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

  const frontCameraImageQualityDialog = (
    <FrontCameraImageQualityDialog
      visible={frontCameraImageQualityDialogVisible}
      currentImageQualityType={frontCameraImageQuality}
      onImageQualityTypeSelect={
        frontCameraImageQualityDialogImageQualitySelectHandler
      }
      onCancel={frontCameraImageQualityDialogCancelPressHandler}
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
          title={t('SettingsView_frontCameraImageQuality')}
          description={
            frontCameraImageQuality === CameraImageQuality.HIGH
              ? t('SettingsView_frontCameraImageQualityHigh')
              : frontCameraImageQuality === CameraImageQuality.MEDIUM
              ? t('SettingsView_frontCameraImageQualityMedium')
              : frontCameraImageQuality === CameraImageQuality.LOW
              ? t('SettingsView_frontCameraImageQualityLow')
              : t('SettingsView_frontCameraImageQualityUnknown')
          }
          onPress={frontCameraImageQualityPressHandler}
        />
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
      <List.Section>
        <List.Subheader>
          {t('SettingsView_notificationsSubheader')}
        </List.Subheader>
        <Checkbox.Item
          style={{borderBottomColor: 'lightgrey', borderBottomWidth: 1}}
          label={t('SettingsView_receiveNotificationsFromCurrentGroup')}
          status={
            receiveNotificationsFromCurrentGroup ? 'checked' : 'unchecked'
          }
          onPress={receiveNotificationsFromCurrentGroupPressHandler}
        />
      </List.Section>
      {backCameraImageQualityDialog}
      {frontCameraImageQualityDialog}
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
