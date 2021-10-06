import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import AppRoutes from '../../../data/common/routes/AppRoutes';
import useGainFocus from '../../../utils/common/hooks/common/useGainFocus';

const useServiceModel = () => {
  // ===
  useGainFocus();
  // ===

  const navigation = useNavigation();

  const dispatch = useDispatch();

  const {groupName, groupPassword, deviceName, loggedIn} = useSelector(
    (store) => store.auth.authInfo,
  );

  const {running: serviceRunning} = useSelector(
    (state) => state.surveillanceCommon.service,
  );

  useEffect(() => {
    if (!serviceRunning) {
      navigation.navigate(AppRoutes.Group);
    }
  }, [serviceRunning, navigation]);

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
