import {useCallback} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import AppRoutes from '../../../data/common/routes/AppRoutes';
import Services from '../../../services/Services';

const useLoaderModel = () => {
  const navigation = useNavigation();

  const dispatch = useDispatch();

  const {groupName, groupPassword, deviceName, loggedIn} = useSelector(
    (store) => store.auth.authInfo,
  );

  const focusChangedCallback = useCallback(() => {
    const getCurrentAuthenticationData = async () => {
      const isServiceRunning =
        await Services.services().surveillanceService.isServiceRunning();

      if (isServiceRunning) {
        navigation.navigate(AppRoutes.Service);
      } else if (loggedIn) {
        navigation.navigate(AppRoutes.Group);
      } else {
        navigation.navigate(AppRoutes.Authorisation);
      }
    };

    getCurrentAuthenticationData();
  }, [loggedIn, navigation]);
  useFocusEffect(focusChangedCallback);

  return {
    dispatch,
    navigation,
  };
};

export default useLoaderModel;

// import {useEffect} from 'react';
// import {useNavigation, useFocusEffect} from '@react-navigation/core';
// import {useDispatch, useSelector} from 'react-redux';
// import AppRoutes from '../../../data/common/routes/AppRoutes';
// import useGainFocus from '../../../utils/common/hooks/common/useGainFocus';
// import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
// import Services from '../../../services/Services';
//
// const useLoaderModel = () => {
//   const navigation = useNavigation();
//
//   const dispatch = useDispatch();
//
//   const {groupName, groupPassword, deviceName, loggedIn} = useSelector(
//     (store) => store.auth.authInfo,
//   );
//
//   useEffect(() => {
//     if (loggedIn) {
//       navigation.navigate(AppRoutes.Group);
//     } else {
//       navigation.navigate(AppRoutes.Authorisation);
//     }
//   }, [loggedIn, navigation]);
//
//   // ===
//   // useEffect(() => {
//   //   const checkIsServiceRunning = async () => {
//   //     const isRunning =
//   //       await Services.services().surveillanceService.isServiceRunning();
//   //
//   //     SystemEventsHandler.onInfo({
//   //       info:
//   //         'useLoaderModel()->IS_SERVICE_RUNNING: ' + JSON.stringify(isRunning),
//   //     });
//   //   };
//   //
//   //   SystemEventsHandler.onInfo({info: 'useLoaderModel()'});
//   //
//   //   checkIsServiceRunning();
//   // }, []);
//   // ===
//
//   return {
//     dispatch,
//     navigation,
//   };
// };
//
// export default useLoaderModel;
//
// // ===
// // useEffect(() => {
// //   const checkIsLoggedIn = async () => {
// //     const result = await Services.services().authService.isLoggedIn();
// //   };
// //
// //   checkIsLoggedIn();
// // }, []);
// // ===
//
// // const gainFocusCallback = useCallback(() => {
// //   SystemEventsHandler.onInfo({info: 'useLoaderModel()->GAIN_FOCUS'});
// // }, []);
// // useFocusEffect(gainFocusCallback);
