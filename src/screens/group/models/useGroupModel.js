import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppRoutes from '../../../data/common/routes/AppRoutes';
import AppActions from '../../../store/actions/AppActions';
import groupLocalReducer from '../store/groupLocalReducer';
import groupLocalState from '../store/groupLocalState';
import GroupLocalActions from '../store/GroupLocalActions';
import useTakeBackCameraImageRequestGroupScreenBehavior from '../hooks/take-back-camera-image-request/useTakeBackCameraImageRequestGroupScreenBehavior';
import useTakeFrontCameraImageRequestGroupScreenBehavior from '../hooks/take-front-camera-image-request/useTakeFrontCameraImageRequestGroupScreenBehavior';

const useGroupModel = () => {
  const navigation = useNavigation();

  const dispatch = useDispatch();

  const [localState, localDispatch] = useReducer(
    groupLocalReducer,
    groupLocalState,
  );

  const [currentRequestType, setCurrentRequestType] = useState(null);
  const [screenFocused, setScreenFocused] = useState(false);

  const deviceRequestTypes = {
    TAKE_BACK_CAMERA_IMAGE: 'TAKE_BACK_CAMERA_IMAGE',
  };

  const {
    groupName: currentGroupName,
    groupPassword: currentGroupPassword,
    deviceName: currentDeviceName,
    loggedIn,
  } = useSelector((state) => state.auth.authInfo);

  const {inProgress: loadingDevicesInGroup, devices: devicesInGroupArray} =
    useSelector((state) => state.surveillanceCommon.devicesInGroup);

  const {running: serviceRunning} = useSelector(
    (state) => state.surveillanceCommon.service,
  );

  const {
    inProgress: isDeviceAliveRequestInProgress,
    response: {
      payload: {isAlive: selectedDeviceAlive},
    },
  } = useSelector(
    (state) => state.surveillanceIsDeviceAliveRequest.isDeviceAliveRequest,
  );

  const {
    inProgress: takeBackCameraImageRequestInProgress,
    response: {
      payload: {image: selectedDeviceBackCameraImage},
    },
  } = useTakeBackCameraImageRequestGroupScreenBehavior({localDispatch});
  const {
    inProgress: takeFrontCameraImageRequestInProgress,
    completed: takeFrontCameraImageRequestCompleted,
    response: {
      payload: {image: selectedDeviceFrontCameraImage},
    },
    error: {
      hasError: takeFrontCameraImageRequestHasError,
      code: takeFrontCameraImageRequestErrorCode,
    },
  } = useTakeFrontCameraImageRequestGroupScreenBehavior({localDispatch});

  const focusChangedCallback = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'useGroupModel()->WILL_UPDATE_GROUP_DATA',
    });
    setScreenFocused(true);

    dispatch(
      AppActions.surveillanceCommon.actions.getDevicesInGroup({
        groupName: currentGroupName,
        groupPassword: currentGroupPassword,
        deviceName: currentDeviceName,
      }),
    );

    const intervalId = setInterval(() => {
      SystemEventsHandler.onInfo({
        info: 'useGroupModel()->WILL_UPDATE_GROUP_DATA',
      });

      dispatch(
        AppActions.surveillanceCommon.actions.getDevicesInGroup({
          groupName: currentGroupName,
          groupPassword: currentGroupPassword,
          deviceName: currentDeviceName,
        }),
      );
    }, 30000);

    return () => {
      SystemEventsHandler.onInfo({
        info: 'useGroupModel()->WILL_STOP_UPDATE_GROUP_DATA',
      });
      setScreenFocused(false);

      clearInterval(intervalId);
    };
  }, [currentGroupName, currentGroupPassword, currentDeviceName, dispatch]);
  useFocusEffect(focusChangedCallback);

  useEffect(() => {
    if (!loggedIn) {
      navigation.navigate(AppRoutes.Authorisation);
    }
  }, [loggedIn, navigation]);

  useEffect(() => {
    if (serviceRunning) {
      navigation.navigate(AppRoutes.Service);
    }
  }, [serviceRunning, navigation]);

  return {
    data: {
      deviceRequestTypes,
      currentRequestType,
      localState,
      currentGroupName,
      currentGroupPassword,
      currentDeviceName,
      loggedIn,
      loadingDevicesInGroup,
      devicesInGroupArray,
      isDeviceAliveRequestInProgress,
      selectedDeviceAlive,
      // takeBackCameraImageRequestInProgress,
      // selectedDeviceBackCameraImage,
    },
    setters: {
      setCurrentRequestType,
    },
    dispatch,
    localDispatch,
    navigation,
  };
};

export default useGroupModel;

// import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
// import {useNavigation, useFocusEffect} from '@react-navigation/core';
// import {useDispatch, useSelector} from 'react-redux';
// import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
// import AppRoutes from '../../../data/common/routes/AppRoutes';
// import AppActions from '../../../store/actions/AppActions';
// import groupLocalReducer from '../store/groupLocalReducer';
// import groupLocalState from '../store/groupLocalState';
// import GroupLocalActions from '../store/GroupLocalActions';
//
// const useGroupModel = () => {
//   const navigation = useNavigation();
//
//   const dispatch = useDispatch();
//
//   const [localState, localDispatch] = useReducer(
//     groupLocalReducer,
//     groupLocalState,
//   );
//
//   const [currentRequestType, setCurrentRequestType] = useState(null);
//   const [screenFocused, setScreenFocused] = useState(false);
//
//   const deviceRequestTypes = {
//     TAKE_BACK_CAMERA_IMAGE: 'TAKE_BACK_CAMERA_IMAGE',
//   };
//
//   const {
//     groupName: currentGroupName,
//     groupPassword: currentGroupPassword,
//     deviceName: currentDeviceName,
//     loggedIn,
//   } = useSelector((state) => state.auth.authInfo);
//
//   const {
//     inProgress: loadingDevicesInGroup,
//     devices: devicesInGroupArray,
//     error: {
//       hasError: devicesInGroupHasError,
//       code: devicesInGroupErrorCode,
//       message: devicesInGroupErrorMessage,
//     },
//   } = useSelector((state) => state.surveillanceCommon.devicesInGroup);
//
//   const {running: serviceRunning} = useSelector(
//     (state) => state.surveillanceCommon.service,
//   );
//
//   const {
//     inProgress: isDeviceAliveRequestInProgress,
//     response: {
//       payload: {isAlive: selectedDeviceAlive},
//     },
//     error: {
//       hasError: isDeviceAliveRequestHasError,
//       code: isDeviceAliveRequestErrorCode,
//     },
//   } = useSelector(
//     (state) => state.surveillanceIsDeviceAliveRequest.isDeviceAliveRequest,
//   );
//
//   const {
//     inProgress: takeBackCameraImageRequestInProgress,
//     completed: takeBackCameraImageRequestCompleted,
//     response: {
//       payload: {image: selectedDeviceBackCameraImage},
//     },
//     error: {
//       hasError: takeBackCameraImageRequestHasError,
//       code: takeBackCameraImageRequestErrorCode,
//     },
//   } = useSelector(
//     (state) =>
//       state.surveillanceTakeBackCameraImageRequest.takeBackCameraImageRequest,
//   );
//   // ===
//   // =====
//   useEffect(() => {
//     if (!screenFocused) {
//       return;
//     }
//
//     if (takeBackCameraImageRequestInProgress) {
//       localDispatch(GroupLocalActions.actions.clearRequestStatusDialogData());
//       localDispatch(
//         GroupLocalActions.actions.setRequestStatusDialogVisibility({
//           visible: true,
//         }),
//       );
//     }
//   }, [screenFocused, takeBackCameraImageRequestInProgress, localDispatch]);
//
//   useEffect(() => {
//     if (!screenFocused) {
//       return;
//     }
//
//     if (takeBackCameraImageRequestCompleted) {
//       localDispatch(
//         GroupLocalActions.actions.setRequestStatusDialogResponseData({
//           data: selectedDeviceBackCameraImage,
//           canViewResponse: true,
//           responseViewerCallback: () => {
//             SystemEventsHandler.onInfo({info: 'responseViewerCallback'});
//
//             localDispatch(
//               GroupLocalActions.actions.setRequestStatusDialogVisibility({
//                 visible: false,
//               }),
//             );
//
//             localDispatch(
//               GroupLocalActions.actions.openImageViewer({
//                 image: selectedDeviceBackCameraImage,
//               }),
//             );
//           },
//         }),
//       );
//     }
//   }, [
//     screenFocused,
//     takeBackCameraImageRequestCompleted,
//     selectedDeviceBackCameraImage,
//     localDispatch,
//   ]);
//   // =====
//   // ===
//
//   const focusChangedCallback = useCallback(() => {
//     SystemEventsHandler.onInfo({
//       info: 'useGroupModel()->WILL_UPDATE_GROUP_DATA',
//     });
//
//     // ===
//     setScreenFocused(true);
//     // ===
//
//     dispatch(
//       AppActions.surveillanceCommon.actions.getDevicesInGroup({
//         groupName: currentGroupName,
//         groupPassword: currentGroupPassword,
//         deviceName: currentDeviceName,
//       }),
//     );
//
//     const intervalId = setInterval(() => {
//       SystemEventsHandler.onInfo({
//         info: 'useGroupModel()->WILL_UPDATE_GROUP_DATA',
//       });
//
//       dispatch(
//         AppActions.surveillanceCommon.actions.getDevicesInGroup({
//           groupName: currentGroupName,
//           groupPassword: currentGroupPassword,
//           deviceName: currentDeviceName,
//         }),
//       );
//     }, 30000);
//
//     return () => {
//       SystemEventsHandler.onInfo({
//         info: 'useGroupModel()->WILL_STOP_UPDATE_GROUP_DATA',
//       });
//
//       setScreenFocused(false);
//
//       clearInterval(intervalId);
//     };
//   }, [currentGroupName, currentGroupPassword, currentDeviceName, dispatch]);
//   useFocusEffect(focusChangedCallback);
//
//   useEffect(() => {
//     if (!loggedIn) {
//       navigation.navigate(AppRoutes.Authorisation);
//     }
//   }, [loggedIn, navigation]);
//
//   useEffect(() => {
//     if (serviceRunning) {
//       navigation.navigate(AppRoutes.Service);
//     }
//   }, [serviceRunning, navigation]);
//
//   return {
//     data: {
//       deviceRequestTypes,
//       currentRequestType,
//       localState,
//       currentGroupName,
//       currentGroupPassword,
//       currentDeviceName,
//       loggedIn,
//       loadingDevicesInGroup,
//       devicesInGroupArray,
//       isDeviceAliveRequestInProgress,
//       selectedDeviceAlive,
//       takeBackCameraImageRequestInProgress,
//       selectedDeviceBackCameraImage,
//     },
//     setters: {
//       setCurrentRequestType,
//     },
//     dispatch,
//     localDispatch,
//     navigation,
//   };
// };
//
// export default useGroupModel;
