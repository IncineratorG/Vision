import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import Services from '../../../services/Services';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';

const useServiceModel = () => {
  const navigation = useNavigation();

  const dispatch = useDispatch();

  const [groupName, setGroupName] = useState('');
  const [groupPassword, setGroupPassword] = useState('');
  const [deviceName, setDeviceName] = useState('');

  const focusChangedCallback = useCallback(() => {
    const getCurrentAuthenticationData = async () => {
      const currentAuthenticationData =
        await Services.services().authService.getCurrentAuthenticationData();

      if (currentAuthenticationData) {
        setGroupName(currentAuthenticationData.groupName);
        setGroupPassword(currentAuthenticationData.groupPassword);
        setDeviceName(currentAuthenticationData.deviceName);
      }
    };

    getCurrentAuthenticationData();
  }, []);
  useFocusEffect(focusChangedCallback);

  return {
    data: {
      groupName,
      groupPassword,
      deviceName,
    },
    navigation,
    dispatch,
  };
};

export default useServiceModel;

// import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
// import {useNavigation, useFocusEffect} from '@react-navigation/core';
// import {useDispatch, useSelector} from 'react-redux';
// import AppRoutes from '../../../data/common/routes/AppRoutes';
// import useGainFocus from '../../../utils/common/hooks/common/useGainFocus';
// import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
// import Services from '../../../services/Services';
//
// const useServiceModel = () => {
//   // ===
//   useGainFocus();
//   // ===
//
//   const navigation = useNavigation();
//
//   const dispatch = useDispatch();
//
//   const {groupName, groupPassword, deviceName, loggedIn} = useSelector(
//     (store) => store.auth.authInfo,
//   );
//
//   const {running: serviceRunning} = useSelector(
//     (state) => state.surveillanceCommon.service,
//   );
//
//   useEffect(() => {
//     if (!serviceRunning) {
//       navigation.navigate(AppRoutes.Group);
//     }
//   }, [serviceRunning, navigation]);
//
//   // ===
//   useEffect(() => {
//     SystemEventsHandler.onInfo({info: 'useServiceModel()->HERE'});
//
//     const getCurrentAuthenticationData = async () => {
//       const authenticationData =
//         await Services.services().authService.getCurrentAuthenticationData();
//
//       SystemEventsHandler.onInfo({
//         info:
//           'useServiceModel()->HERE->authenticationData: ' +
//           JSON.stringify(authenticationData),
//       });
//     };
//
//     getCurrentAuthenticationData();
//   }, []);
//   // ===
//
//   return {
//     data: {
//       groupName,
//       groupPassword,
//       deviceName,
//     },
//     navigation,
//     dispatch,
//   };
// };
//
// export default useServiceModel;
