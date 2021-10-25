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
  onToggleRecognizePersonWithFrontCameraRequestPress,
  onToggleRecognizePersonWithBackCameraRequestPress,
  onCancelPress,
}) => {
  const {t} = useTranslation();

  const requestTypes = useMemo(() => {
    return {
      UNKNOWN: 'unknown',
      GET_FRONT_CAMERA_IMAGE: 'getFrontCameraImage',
      GET_BACK_CAMERA_IMAGE: 'getBackCameraImage',
      TOGGLE_DETECT_DEVICE_MOVEMENT: 'toggleDetectDeviceMovement',
      TOGGLE_RECOGNIZE_PERSON_WITH_FRONT_CAMERA:
        'TOGGLE_RECOGNIZE_PERSON_WITH_FRONT_CAMERA',
      TOGGLE_RECOGNIZE_PERSON_WITH_BACK_CAMERA:
        'TOGGLE_RECOGNIZE_PERSON_WITH_BACK_CAMERA',
    };
  }, []);

  const requestComponentTypes = useMemo(() => {
    return {
      DIVIDER: 'divider',
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

        case requestTypes.TOGGLE_RECOGNIZE_PERSON_WITH_FRONT_CAMERA: {
          if (onToggleRecognizePersonWithFrontCameraRequestPress) {
            onToggleRecognizePersonWithFrontCameraRequestPress({
              selectedDevice: device,
            });
          }
          break;
        }

        case requestTypes.TOGGLE_RECOGNIZE_PERSON_WITH_BACK_CAMERA: {
          if (onToggleRecognizePersonWithBackCameraRequestPress) {
            onToggleRecognizePersonWithBackCameraRequestPress({
              selectedDevice: device,
            });
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
      onToggleRecognizePersonWithFrontCameraRequestPress,
      onToggleRecognizePersonWithBackCameraRequestPress,
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
        canRecognizePerson,
        deviceMovementServiceRunning,
        frontCameraRecognizePersonServiceRunning,
        backCameraRecognizePersonServiceRunning,
      } = device;

      const requests = [];
      if (hasFrontCamera) {
        requests.push({
          id: '1',
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
          id: '2',
          type: requestTypes.GET_BACK_CAMERA_IMAGE,
          recomendedComponentType: requestComponentTypes.TEXT,
          name: t('DeviceRequestsDialog_getBackCameraImage'),
          icon: <MaterialIcon name="photo-camera" size={28} color="grey" />,
        });
      }
      requests.push({
        id: '3',
        type: requestTypes.UNKNOWN,
        recomendedComponentType: requestComponentTypes.DIVIDER,
      });
      if (canDetectDeviceMovement) {
        requests.push({
          id: '4',
          type: requestTypes.TOGGLE_DETECT_DEVICE_MOVEMENT,
          recomendedComponentType: requestComponentTypes.CHECKBOX,
          name: t('DeviceRequestsDialog_detectDeviceMovement'),
          checked: deviceMovementServiceRunning,
        });
      }
      requests.push({
        id: '5',
        type: requestTypes.UNKNOWN,
        recomendedComponentType: requestComponentTypes.DIVIDER,
      });
      if (canRecognizePerson && hasFrontCamera) {
        requests.push({
          id: '6',
          type: requestTypes.TOGGLE_RECOGNIZE_PERSON_WITH_FRONT_CAMERA,
          recomendedComponentType: requestComponentTypes.CHECKBOX,
          name: t('DeviceRequestsDialog_recognizePersonWithFrontCamera'),
          checked: frontCameraRecognizePersonServiceRunning,
        });
      }
      if (canRecognizePerson && hasBackCamera) {
        requests.push({
          id: '7',
          type: requestTypes.TOGGLE_RECOGNIZE_PERSON_WITH_BACK_CAMERA,
          recomendedComponentType: requestComponentTypes.CHECKBOX,
          name: t('DeviceRequestsDialog_recognizePersonWithBackCamera'),
          checked: backCameraRecognizePersonServiceRunning,
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
