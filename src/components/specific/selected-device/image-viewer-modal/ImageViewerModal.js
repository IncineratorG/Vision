import React, {useState, useCallback, useEffect} from 'react';
import {
  View,
  Image,
  Text,
  StyleSheet,
  Modal,
  TouchableOpacity,
} from 'react-native';
import {ImageViewer} from 'react-native-image-zoom-viewer';
import Icon from 'react-native-vector-icons/MaterialIcons';

const ImageViewerModal = ({visible, image, onClose}) => {
  const [internalVisible, setInternalVisible] = useState(false);
  const [imageDataSize, setImageDataSize] = useState(0);
  const [imagesData, setImagesData] = useState([]);
  const [viewerInitialIndex, setViewerInitialIndex] = useState(-2);
  const [imageRotationDegrees, setImageRotationDegrees] = useState(0);

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
    // SystemEventsHandler.onInfo({info: 'CHANGE_IMAGE'});
  }, []);

  const getCurrentImageRotationStyleParam = useCallback(() => {
    let imageRotationStyleParam = imageRotationDegrees.toString();
    imageRotationStyleParam = imageRotationStyleParam + 'deg';
    return imageRotationStyleParam;
  }, [imageRotationDegrees]);

  useEffect(() => {
    if (!visible) {
      setInternalVisible(false);
      setImageRotationDegrees(0);
      return;
    }

    if (image && visible) {
      const imageObject = {
        url: 'data:image/jpg;base64,' + image,
      };

      const imagesArray = [];
      imagesArray.push(imageObject);

      setImagesData(imagesArray);
      setViewerInitialIndex(0);

      setTimeout(() => {
        setImagesData([]);
        setViewerInitialIndex(0);

        setTimeout(() => {
          const otherImagesArray = [];
          otherImagesArray.push(imageObject);

          setImagesData(otherImagesArray);
          setViewerInitialIndex(0);
          setInternalVisible(true);

          setImageDataSize(image.length);
        }, 100);
      }, 200);
    }
  }, [image, visible]);

  return (
    <Modal
      visible={internalVisible}
      transparent={false}
      onShow={onShowHandler}
      onRequestClose={requestCloseHandler}>
      <ImageViewer
        imageUrls={imagesData}
        onCancel={onClose}
        useNativeDriver={false}
        index={viewerInitialIndex}
        enablePreload={true}
        onChange={changeImageHandler}
        renderHeader={(currentIndex) => {
          const rotateImageHandler = () => {
            setImageRotationDegrees((prev) => prev + 90);
          };

          const removeImageHandler = () => {};

          return (
            <View style={styles.mainContainer}>
              <TouchableOpacity onPress={arrowBackHandler}>
                <View style={styles.arrowBackContainer}>
                  <Icon name="arrow-back" size={26} color={'#ffffff'} />
                </View>
              </TouchableOpacity>
              <View style={styles.freeSpace} />
              <View
                style={{
                  backgroundColor: 'white',
                  alignItems: 'center',
                  justifyContent: 'center',
                  paddingLeft: 4,
                  paddingRight: 4,
                }}>
                <Text>{imageDataSize}</Text>
              </View>
              <TouchableOpacity onPress={rotateImageHandler}>
                <View style={styles.shareImageContainer}>
                  <Icon name="rotate-right" size={26} color={'#ffffff'} />
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
        renderImage={(props) => {
          return (
            <Image
              style={{
                flex: 1,
                alignSelf: 'stretch',
                transform: [{rotate: getCurrentImageRotationStyleParam()}],
              }}
              source={{uri: 'data:image/jpg;base64,' + image}}
            />
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
    // backgroundColor: '#00000066',
    backgroundColor: 'black',
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

export default ImageViewerModal;

// import React, {useState, useCallback, useEffect} from 'react';
// import {View, StyleSheet, Modal, TouchableOpacity} from 'react-native';
// import {ImageViewer} from 'react-native-image-zoom-viewer';
// import Icon from 'react-native-vector-icons/MaterialIcons';
// import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
//
// const ImageViewerModal = ({visible, image, onClose}) => {
//   const [imagesData, setImagesData] = useState([]);
//   const [viewerInitialIndex, setViewerInitialIndex] = useState(-2);
//
//   const onShowHandler = useCallback(() => {}, []);
//
//   const arrowBackHandler = useCallback(() => {
//     if (onClose) {
//       onClose();
//     }
//   }, [onClose]);
//
//   const requestCloseHandler = useCallback(() => {
//     if (onClose) {
//       onClose();
//     }
//   }, [onClose]);
//
//   const changeImageHandler = useCallback(async (index) => {
//     SystemEventsHandler.onInfo({info: 'CHANGE_IMAGE'});
//   }, []);
//
//   useEffect(() => {
//     SystemEventsHandler.onInfo({info: 'ImageViewerModal->useEffect()'});
//
//     if (!image) {
//       // setViewerInitialIndex(-1);
//       // setImagesData([]);
//       return;
//     }
//
//     const imageObject = {
//       url: 'data:image/jpg;base64,' + image,
//     };
//
//     setViewerInitialIndex(0);
//
//     const imagesArray = [];
//     imagesArray.push(imageObject);
//     setImagesData(imagesArray);
//
//     SystemEventsHandler.onInfo({
//       info: 'ImageViewerModal->useEffect(): IMAGES_SET',
//     });
//
//     // setViewerInitialIndex(0);
//   }, [image]);
//
//   // useEffect(() => {
//   //   SystemEventsHandler.onInfo({info: 'ImageViewerModal->useEffect()'});
//   //
//   //   if (!image) {
//   //     // setViewerInitialIndex(-1);
//   //     // setImagesData([]);
//   //     return;
//   //   }
//   //
//   //   const imageObject = {
//   //     url: 'data:image/jpg;base64,' + image,
//   //   };
//   //
//   //   setViewerInitialIndex(0);
//   //
//   //   const imagesArray = [];
//   //   imagesArray.push(imageObject);
//   //   setImagesData(imagesArray);
//   //
//   //   SystemEventsHandler.onInfo({
//   //     info: 'ImageViewerModal->useEffect(): IMAGES_SET',
//   //   });
//   //
//   //   // setViewerInitialIndex(0);
//   // }, [image]);
//
//   return (
//     <Modal
//       visible={visible}
//       transparent={false}
//       onShow={onShowHandler}
//       onRequestClose={requestCloseHandler}>
//       <ImageViewer
//         imageUrls={imagesData}
//         onCancel={onClose}
//         useNativeDriver={false}
//         index={viewerInitialIndex}
//         enablePreload={true}
//         onChange={changeImageHandler}
//         renderHeader={(currentIndex) => {
//           const shareImageHandler = () => {};
//
//           const removeImageHandler = () => {};
//
//           // return (
//           //   <View style={{width: 100, height: 50, backgroundColor: 'red'}} />
//           // );
//
//           return (
//             <View style={styles.mainContainer}>
//               <TouchableOpacity onPress={arrowBackHandler}>
//                 <View style={styles.arrowBackContainer}>
//                   <Icon name="arrow-back" size={26} color={'#ffffff'} />
//                 </View>
//               </TouchableOpacity>
//               <View style={styles.freeSpace} />
//               <TouchableOpacity onPress={shareImageHandler}>
//                 <View style={styles.shareImageContainer}>
//                   <Icon name="share" size={26} color={'#ffffff'} />
//                 </View>
//               </TouchableOpacity>
//               <TouchableOpacity onPress={removeImageHandler}>
//                 <View style={styles.removeImageContainer}>
//                   <Icon name="delete" size={26} color={'#ffffff'} />
//                 </View>
//               </TouchableOpacity>
//             </View>
//           );
//         }}
//       />
//     </Modal>
//   );
// };
//
// const styles = StyleSheet.create({
//   mainContainer: {
//     height: 50,
//     alignSelf: 'stretch',
//     flexDirection: 'row',
//   },
//   arrowBackContainer: {
//     height: 50,
//     width: 50,
//     // backgroundColor: '#00000066',
//     backgroundColor: 'black',
//     alignItems: 'center',
//     justifyContent: 'center',
//   },
//   freeSpace: {
//     flex: 1,
//   },
//   shareImageContainer: {
//     height: 50,
//     width: 50,
//     backgroundColor: '#00000066',
//     alignItems: 'center',
//     justifyContent: 'center',
//   },
//   removeImageContainer: {
//     height: 50,
//     width: 50,
//     backgroundColor: '#00000066',
//     alignItems: 'center',
//     justifyContent: 'center',
//   },
// });
//
// export default ImageViewerModal;
