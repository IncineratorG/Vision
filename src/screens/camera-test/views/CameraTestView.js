import React, {useEffect, useCallback, useState} from 'react';
import {View, Image, StyleSheet} from 'react-native';
import SimpleButton from '../../../components/common/simple-button/SimpleButton';
import Services from '../../../services/Services';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';

const CameraTestView = ({model, controller}) => {
  const [cameraRunning, setCameraRunning] = useState(false);
  const [currentBase64ImageString, setCurrentBase64ImageString] =
    useState(null);

  const getPermissions = useCallback(() => {
    Services.services().surveillanceService.getAppPermissions();
  }, []);

  const startBackCamera = useCallback(() => {
    SystemEventsHandler.onInfo({info: 'CameraTestView->startBackCamera()'});

    Services.services().surveillanceService.startBackCamera();
  }, []);

  const startFrontCamera = useCallback(() => {
    SystemEventsHandler.onInfo({info: 'CameraTestView->startFrontCamera()'});

    Services.services().surveillanceService.startFrontCamera();
  }, []);

  const stopBackCamera = useCallback(() => {
    SystemEventsHandler.onInfo({info: 'CameraTestView->stopBackCamera()'});

    Services.services().surveillanceService.stopBackCamera();
  }, []);

  const stopFrontCamera = useCallback(() => {
    SystemEventsHandler.onInfo({info: 'CameraTestView->stopFrontCamera()'});

    Services.services().surveillanceService.stopFrontCamera();
  }, []);

  const takeBackCameraPicture = useCallback(async () => {
    SystemEventsHandler.onInfo({
      info: 'CameraTestView->takeBackCameraPicture()',
    });

    setCurrentBase64ImageString(null);

    const imageData =
      await Services.services().surveillanceService.takeBackCameraPicture();
    if (imageData === null) {
      SystemEventsHandler.onInfo({
        info: 'CameraTestView->takeBackCameraPicture(): IMAGE_DATA_IS_NULL',
      });
      return;
    }

    const {base64String} = imageData;
    if (base64String === null) {
      SystemEventsHandler.onInfo({
        info: 'CameraTestView->takeBackCameraPicture(): BASE_64_STRING_IS_NULL',
      });
      return;
    }

    setCurrentBase64ImageString('data:image/jpg;base64,' + base64String);
  }, []);

  const takeFrontCameraPicture = useCallback(async () => {
    SystemEventsHandler.onInfo({
      info: 'CameraTestView->takeFrontCameraPicture()',
    });

    setCurrentBase64ImageString(null);

    const imageData =
      await Services.services().surveillanceService.takeFrontCameraPicture();
    if (imageData === null) {
      SystemEventsHandler.onInfo({
        info: 'CameraTestView->takeFrontCameraPicture(): IMAGE_DATA_IS_NULL',
      });
      return;
    }

    const {base64String} = imageData;
    if (base64String === null) {
      SystemEventsHandler.onInfo({
        info: 'CameraTestView->takeFrontCameraPicture(): BASE_64_STRING_IS_NULL',
      });
      return;
    }

    setCurrentBase64ImageString('data:image/jpg;base64,' + base64String);
  }, []);

  return (
    <View style={styles.mainContainer}>
      <View style={styles.contentContainer}>
        <View
          style={[
            styles.indicatorContainer,
            {backgroundColor: cameraRunning ? 'green' : 'grey'},
          ]}
        />
        <View style={styles.imageContainer}>
          <Image
            style={{
              flex: 1,
              width: null,
              height: null,
              resizeMode: 'contain',
              // flex: 1,
              // alignSelf: 'stretch',
            }}
            source={{uri: currentBase64ImageString}}
          />
        </View>
      </View>
      <View style={styles.buttons}>
        <View style={styles.buttonContainer}>
          <SimpleButton title={'Get Permission'} onPress={getPermissions} />
        </View>
        <View style={styles.buttonContainer}>
          <SimpleButton title={'Start Back Camera'} onPress={startBackCamera} />
        </View>
        <View style={styles.buttonContainer}>
          <SimpleButton
            title={'Start Front Camera'}
            onPress={startFrontCamera}
          />
        </View>
        <View style={styles.buttonContainer}>
          <SimpleButton title={'Stop Back Camera'} onPress={stopBackCamera} />
        </View>
        <View style={styles.buttonContainer}>
          <SimpleButton title={'Stop Front Camera'} onPress={stopFrontCamera} />
        </View>
        <View style={styles.buttonContainer}>
          <SimpleButton
            title={'Take Back Camera Picture'}
            onPress={takeBackCameraPicture}
          />
        </View>
        <View style={styles.buttonContainer}>
          <SimpleButton
            title={'Take Front Camera Picture'}
            onPress={takeFrontCameraPicture}
          />
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    backgroundColor: 'orange',
  },
  contentContainer: {
    flex: 1,
    // backgroundColor: 'pink',
  },
  indicatorContainer: {
    height: 10,
    alignSelf: 'stretch',
    backgroundColor: 'green',
  },
  imageContainer: {
    flex: 1,
    margin: 4,
    backgroundColor: 'white',
  },
  buttons: {},
  buttonContainer: {
    height: 50,
    padding: 8,
    backgroundColor: 'green',
    justifyContent: 'center',
    borderWidth: 1,
    borderColor: 'grey',
  },
});

export default React.memo(CameraTestView);

// const test = useCallback(() => {
//   setCurrentBase64ImageString(null);
//
//   Services.services().surveillanceService.testCameraMotionDetection();
// }, []);

// const startPreview = useCallback(async () => {
//   SystemEventsHandler.onInfo({info: 'startPreview()'});
//
//   const started =
//     await Services.services().surveillanceService.testStartCameraPreview();
//   setCameraRunning(started);
// }, []);
//
// const stopPreview = useCallback(async () => {
//   SystemEventsHandler.onInfo({info: 'stopPreview()'});
//
//   const stopped =
//     await Services.services().surveillanceService.testStopCameraPreview();
//   setCameraRunning(!stopped);
// }, []);
//
// const takePreviewPicture = useCallback(async () => {
//   SystemEventsHandler.onInfo({info: 'takePreviewPicture()'});
//
//   setCurrentBase64ImageString(null);
//
//   const imageData =
//     await Services.services().surveillanceService.testTakeCameraPreviewPicture();
//
//   if (imageData === null) {
//     SystemEventsHandler.onInfo({
//       info: 'CameraTestView->takePreviewPicture(): IMAGE_DATA_IS_NULL',
//     });
//     return;
//   }
//
//   const {base64String} = imageData;
//   if (base64String === null) {
//     SystemEventsHandler.onInfo({
//       info: 'CameraTestView->takePreviewPicture(): BASE_64_STRING_IS_NULL',
//     });
//     return;
//   }
//
//   setCurrentBase64ImageString('data:image/jpg;base64,' + base64String);
// }, []);
//
// useEffect(() => {
//   Services.services().surveillanceService.nativeService.addImageTakenListener(
//     (data) => {
//       const {base64String} = data;
//
//       SystemEventsHandler.onInfo({
//         info: 'IMAGE_TAKEN: ' + base64String.length,
//       });
//
//       setCurrentBase64ImageString('data:image/jpg;base64,' + base64String);
//     },
//   );
// }, []);
