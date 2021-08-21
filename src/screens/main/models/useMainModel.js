import {useCallback} from 'react';
import {useNavigation} from '@react-navigation/core';
import {useDispatch} from 'react-redux';

const useMainModel = () => {
  const navigation = useNavigation();

  const dispatch = useDispatch();

  return {
    data: {},
    setters: {},
    dispatch,
    navigation,
  };
};

export default useMainModel;
