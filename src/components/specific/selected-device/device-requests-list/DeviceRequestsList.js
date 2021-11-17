import React, {useState, useEffect, useMemo, useCallback} from 'react';
import {View, FlatList, StyleSheet} from 'react-native';
import MaterialIcon from 'react-native-vector-icons/MaterialIcons';
import useTranslation from '../../../../utils/common/localization';
import DeviceRequestsListDividerItem from './item/DeviceRequestsListDividerItem';
import DeviceRequestsListTextItem from './item/DeviceRequestsListTextItem';
import DeviceRequestsListCheckboxItem from './item/DeviceRequestsListCheckboxItem';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';

const DeviceRequestsList = ({
  device,
  onGetFrontCameraImageRequestPress,
  onGetBackCameraImageRequestPress,
  onToggleDetectDeviceMovementRequestPress,
  onToggleRecognizePersonWithFrontCameraRequestPress,
  onToggleRecognizePersonWithBackCameraRequestPress,
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

  const [availableRequests, setAvailableRequests] = useState([]);

  const requestPressHandler = useCallback(
    ({type}) => {
      SystemEventsHandler.onInfo({
        info: 'DeviceRequestsList->requestPressHandler(): ' + type,
      });

      switch (type) {
        case requestTypes.GET_FRONT_CAMERA_IMAGE: {
          if (onGetFrontCameraImageRequestPress) {
            onGetFrontCameraImageRequestPress({selectedDevice: device});
          }
          break;
        }

        case requestTypes.GET_BACK_CAMERA_IMAGE: {
          if (onGetBackCameraImageRequestPress) {
            onGetBackCameraImageRequestPress({selectedDevice: device});
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
              'DeviceRequestsList->requestPressHandler()->UNKNOWN_REQUEST_TYPE: ' +
              type,
          });
        }
      }
    },
    [
      device,
      requestTypes,
      onGetFrontCameraImageRequestPress,
      onGetBackCameraImageRequestPress,
      onToggleDetectDeviceMovementRequestPress,
      onToggleRecognizePersonWithFrontCameraRequestPress,
      onToggleRecognizePersonWithBackCameraRequestPress,
    ],
  );

  useEffect(() => {
    if (!device) {
      setAvailableRequests([]);
      return;
    }

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
        name: t('DeviceRequestsList_getFrontCameraImage'),
        icon: <MaterialIcon name="photo-camera-front" size={28} color="grey" />,
      });
    }
    if (hasBackCamera) {
      requests.push({
        id: '2',
        type: requestTypes.GET_BACK_CAMERA_IMAGE,
        recomendedComponentType: requestComponentTypes.TEXT,
        name: t('DeviceRequestsList_getBackCameraImage'),
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
        name: t('DeviceRequestsList_detectDeviceMovement'),
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
        name: t('DeviceRequestsList_recognizePersonWithFrontCamera'),
        checked: frontCameraRecognizePersonServiceRunning,
      });
    }
    if (canRecognizePerson && hasBackCamera) {
      requests.push({
        id: '7',
        type: requestTypes.TOGGLE_RECOGNIZE_PERSON_WITH_BACK_CAMERA,
        recomendedComponentType: requestComponentTypes.CHECKBOX,
        name: t('DeviceRequestsList_recognizePersonWithBackCamera'),
        checked: backCameraRecognizePersonServiceRunning,
      });
    }

    setAvailableRequests(requests);
  }, [device, requestTypes, requestComponentTypes, t]);

  const renderItem = useCallback(
    ({item}) => {
      const {type, recomendedComponentType, name, icon, checked} = item;

      switch (recomendedComponentType) {
        case 'divider': {
          return <DeviceRequestsListDividerItem />;
        }

        case 'text': {
          return (
            <DeviceRequestsListTextItem
              type={type}
              name={name}
              icon={icon}
              onPress={requestPressHandler}
            />
          );
        }

        case 'checkbox': {
          return (
            <DeviceRequestsListCheckboxItem
              type={type}
              name={name}
              checked={checked}
              onPress={requestPressHandler}
            />
          );
        }
      }
    },
    [requestPressHandler],
  );

  const keyExtractor = useCallback((item) => {
    return item.id;
  }, []);

  return (
    <View style={styles.mainContainer}>
      <FlatList
        showsVerticalScrollIndicator={false}
        data={availableRequests}
        renderItem={renderItem}
        keyExtractor={keyExtractor}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    // backgroundColor: 'orange',
  },
});

export default React.memo(DeviceRequestsList);
