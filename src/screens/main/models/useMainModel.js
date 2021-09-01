import {useCallback, useReducer} from 'react';
import {useNavigation} from '@react-navigation/core';
import {useDispatch} from 'react-redux';
import mainLocalReducer from '../store/mainLocalReducer';
import mainLocalState from '../store/mainLocalState';

const useMainModel = () => {
  const navigation = useNavigation();

  const dispatch = useDispatch();

  const [localState, localDispatch] = useReducer(
    mainLocalReducer,
    mainLocalState,
  );

  return {
    data: {localState},
    setters: {},
    dispatch,
    localDispatch,
    navigation,
  };
};

export default useMainModel;
