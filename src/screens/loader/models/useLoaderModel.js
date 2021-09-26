import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import AppRoutes from '../../../data/common/routes/AppRoutes';
import Services from '../../../services/Services';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import useGainFocus from '../../../utils/common/hooks/common/useGainFocus';

const useLoaderModel = () => {
  // ===
  useGainFocus();
  // ===

  const navigation = useNavigation();

  const dispatch = useDispatch();

  const {groupName, groupPassword, deviceName, loggedIn} = useSelector(
    (store) => store.auth.authInfo,
  );

  useEffect(() => {
    if (loggedIn) {
      navigation.navigate(AppRoutes.Group);
    } else {
      navigation.navigate(AppRoutes.Authorisation);
    }
  }, [loggedIn, navigation]);

  // ===
  // useEffect(() => {
  //   const checkIsLoggedIn = async () => {
  //     const result = await Services.services().authService.isLoggedIn();
  //   };
  //
  //   checkIsLoggedIn();
  // }, []);
  // ===

  // const gainFocusCallback = useCallback(() => {
  //   SystemEventsHandler.onInfo({info: 'useLoaderModel()->GAIN_FOCUS'});
  // }, []);
  // useFocusEffect(gainFocusCallback);

  return {
    dispatch,
    navigation,
  };
};

export default useLoaderModel;
