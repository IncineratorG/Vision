import React, {useState, useCallback, useEffect} from 'react';
import {View, StyleSheet, Modal, TouchableOpacity} from 'react-native';
import {ImageViewer} from 'react-native-image-zoom-viewer';
import Icon from 'react-native-vector-icons/MaterialIcons';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';

const ImageViewerModal = ({visible, image, onClose}) => {
  const [imagesData, setImagesData] = useState([]);
  const [viewerInitialIndex, setViewerInitialIndex] = useState(0);

  const onShowHandler = useCallback(() => {}, []);

  const arrowBackHandler = useCallback(() => {
    if (onClose) {
      onClose();
    }
  }, [onClose]);

  const requestCloseHandler = useCallback(() => {
    if (onClose) {
      onClose();
    }
  }, [onClose]);

  const changeImageHandler = useCallback(async (index) => {
    SystemEventsHandler.onInfo({info: 'CHANGE_IMAGE'});
  }, []);

  useEffect(() => {
    if (!image) {
      return;
    }

    const imagesArray = [];
    imagesArray.push(image);
    setImagesData(imagesArray);
  }, [image]);

  return (
    <Modal
      visible={visible}
      transparent={false}
      onShow={onShowHandler}
      onRequestClose={requestCloseHandler}>
      <ImageViewer
        imageUrls={imagesData}
        onCancel={onClose}
        useNativeDriver={false}
        index={viewerInitialIndex}
        enablePreload={false}
        onChange={changeImageHandler}
        renderHeader={(currentIndex) => {
          const shareImageHandler = () => {};

          const removeImageHandler = () => {};

          return (
            <View style={styles.mainContainer}>
              <TouchableOpacity onPress={arrowBackHandler}>
                <View style={styles.arrowBackContainer}>
                  <Icon name="arrow-back" size={26} color={'#ffffff'} />
                </View>
              </TouchableOpacity>
              <View style={styles.freeSpace} />
              <TouchableOpacity onPress={shareImageHandler}>
                <View style={styles.shareImageContainer}>
                  <Icon name="share" size={26} color={'#ffffff'} />
                </View>
              </TouchableOpacity>
              <TouchableOpacity onPress={removeImageHandler}>
                <View style={styles.removeImageContainer}>
                  <Icon name="delete" size={26} color={'#ffffff'} />
                </View>
              </TouchableOpacity>
            </View>
          );
        }}
      />
    </Modal>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    height: 50,
    alignSelf: 'stretch',
    flexDirection: 'row',
  },
  arrowBackContainer: {
    height: 50,
    width: 50,
    backgroundColor: '#00000066',
    alignItems: 'center',
    justifyContent: 'center',
  },
  freeSpace: {
    flex: 1,
  },
  shareImageContainer: {
    height: 50,
    width: 50,
    backgroundColor: '#00000066',
    alignItems: 'center',
    justifyContent: 'center',
  },
  removeImageContainer: {
    height: 50,
    width: 50,
    backgroundColor: '#00000066',
    alignItems: 'center',
    justifyContent: 'center',
  },
});

export default React.memo(ImageViewerModal);
