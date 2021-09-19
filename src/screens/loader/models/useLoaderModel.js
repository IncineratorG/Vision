import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import AppRoutes from '../../../data/common/routes/AppRoutes';

const useLoaderModel = () => {
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

  return {
    dispatch,
    navigation,
  };
};

export default useLoaderModel;
