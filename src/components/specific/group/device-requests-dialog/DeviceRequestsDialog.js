import React, {useCallback, useEffect, useMemo, useState} from 'react';
import {StyleSheet, View} from 'react-native';
import {Button, Dialog, Portal} from 'react-native-paper';
import useTranslation from '../../../../utils/common/localization';
import DeviceRequestsDialogRequestsList from './requests-list/DeviceRequestsDialogRequestsList';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import CheckingSelectedDevice from './checking-selected-device/CheckingSelectedDevice';
import SelectedDeviceNotAvailable from './selected-device-not-available/SelectedDeviceNotAvailable';
import MaterialIcon from 'react-native-vector-icons/MaterialIcons';

const DeviceRequestsDialog = ({
  visible,
  checkingSelectedDeviceAvailability,
  selectedDeviceAvailable,
  device,
  onGetFrontCameraRequestPress,
  onGetBackCameraRequestPress,
  onToggleDetectDeviceMovementRequestPress,
  onCancelPress,
}) => {
  const {t} = useTranslation();

  const requestTypes = useMemo(() => {
    return {
      GET_FRONT_CAMERA_IMAGE: 'getFrontCameraImage',
      GET_BACK_CAMERA_IMAGE: 'getBackCameraImage',
      TOGGLE_DETECT_DEVICE_MOVEMENT: 'toggleDetectDeviceMovement',
    };
  }, []);

  const requestComponentTypes = useMemo(() => {
    return {
      TEXT: 'text',
      CHECKBOX: 'checkbox',
    };
  }, []);

  const [contentComponent, setContentComponent] = useState(null);
  const [availableRequests, setAvailableRequests] = useState([]);

  const requestPressHandler = useCallback(
    ({type}) => {
      SystemEventsHandler.onInfo({
        info: 'DeviceRequestsDialog->requestPressHandler(): ' + type,
      });

      switch (type) {
        case requestTypes.GET_FRONT_CAMERA_IMAGE: {
          if (onGetFrontCameraRequestPress) {
            onGetFrontCameraRequestPress({selectedDevice: device});
          }
          break;
        }

        case requestTypes.GET_BACK_CAMERA_IMAGE: {
          if (onGetBackCameraRequestPress) {
            onGetBackCameraRequestPress({selectedDevice: device});
          }
          break;
        }

        case requestTypes.TOGGLE_DETECT_DEVICE_MOVEMENT: {
          if (onToggleDetectDeviceMovementRequestPress) {
            onToggleDetectDeviceMovementRequestPress({selectedDevice: device});
          }
          break;
        }

        default: {
          SystemEventsHandler.onInfo({
            info:
              'DeviceRequestsDialog->requestPressHandler()->UNKNOWN_REQUEST_TYPE: ' +
              type,
          });
        }
      }
    },
    [
      device,
      requestTypes,
      onGetFrontCameraRequestPress,
      onGetBackCameraRequestPress,
      onToggleDetectDeviceMovementRequestPress,
    ],
  );

  const requestsListComponent = useMemo(() => {
    return (
      <DeviceRequestsDialogRequestsList
        requestsList={availableRequests}
        onRequestPress={requestPressHandler}
      />
    );
  }, [availableRequests, requestPressHandler]);

  useEffect(() => {
    SystemEventsHandler.onInfo({
      info: 'DeviceRequestsDialog->SELECTED_DEVICE: ' + JSON.stringify(device),
    });

    if (device) {
      const {
        deviceName,
        deviceMode,
        lastLoginTimestamp,
        lastUpdateTimestamp,
        hasFrontCamera,
        hasBackCamera,
        canDetectDeviceMovement,
        deviceMovementServiceRunning,
      } = device;

      const requests = [];
      if (hasFrontCamera) {
        requests.push({
          type: requestTypes.GET_FRONT_CAMERA_IMAGE,
          recomendedComponentType: requestComponentTypes.TEXT,
          name: t('DeviceRequestsDialog_getFrontCameraImage'),
          icon: (
            <MaterialIcon name="photo-camera-front" size={28} color="grey" />
          ),
        });
      }
      if (hasBackCamera) {
        requests.push({
          type: requestTypes.GET_BACK_CAMERA_IMAGE,
          recomendedComponentType: requestComponentTypes.TEXT,
          name: t('DeviceRequestsDialog_getBackCameraImage'),
          icon: <MaterialIcon name="photo-camera" size={28} color="grey" />,
        });
      }
      if (canDetectDeviceMovement) {
        requests.push({
          type: requestTypes.TOGGLE_DETECT_DEVICE_MOVEMENT,
          recomendedComponentType: requestComponentTypes.CHECKBOX,
          name: t('DeviceRequestsDialog_detectDeviceMovement'),
          checked: deviceMovementServiceRunning,
        });
      }

      setAvailableRequests(requests);
    } else {
      setAvailableRequests([]);
    }
  }, [device, requestTypes, requestComponentTypes, t]);

  useEffect(() => {
    if (checkingSelectedDeviceAvailability) {
      setContentComponent(<CheckingSelectedDevice />);
    } else if (selectedDeviceAvailable) {
      setContentComponent(requestsListComponent);
    } else {
      setContentComponent(<SelectedDeviceNotAvailable />);
    }
  }, [
    checkingSelectedDeviceAvailability,
    selectedDeviceAvailable,
    requestsListComponent,
  ]);

  return (
    <Portal>
      <Dialog visible={visible} onDismiss={onCancelPress}>
        <Dialog.Title>{t('DeviceRequestsDialog_title')}</Dialog.Title>
        <Dialog.Content>
          <View style={styles.mainContainer}>{contentComponent}</View>
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
    minHeight: 200,
    justifyContent: 'center',
  },
});

export default React.memo(DeviceRequestsDialog);
