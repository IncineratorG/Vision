import {useState, useCallback, useEffect, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';

const useGroupModel = () => {
  const navigation = useNavigation();

  const dispatch = useDispatch();

  return {
    data: {},
    setters: {},
    navigation,
    dispatch,
  };
};

export default useGroupModel;
