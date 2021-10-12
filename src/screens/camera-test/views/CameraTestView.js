import React, {useEffect, useCallback, useState} from 'react';
import {View, Image, StyleSheet} from 'react-native';
import SimpleButton from '../../../components/common/simple-button/SimpleButton';
import Services from '../../../services/Services';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';

const CameraTestView = ({model, controller}) => {
  const [currentBase64ImageString, setCurrentBase64ImageString] =
    useState(null);

  const test = useCallback(() => {
    setCurrentBase64ImageString(null);

    Services.services().surveillanceService.testCameraMotionDetection();
  }, []);

  const getPermissions = useCallback(() => {
    Services.services().surveillanceService.getAppPermissions();
  }, []);

  useEffect(() => {
    Services.services().surveillanceService.nativeService.addImageTakenListener(
      (data) => {
        const {base64String} = data;

        SystemEventsHandler.onInfo({
          info: 'IMAGE_TAKEN: ' + base64String.length,
        });

        setCurrentBase64ImageString('data:image/jpg;base64,' + base64String);
      },
    );
  }, []);

  return (
    <View style={styles.mainContainer}>
      <View style={styles.contentContainer}>
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
          <SimpleButton title={'Test request'} onPress={test} />
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
