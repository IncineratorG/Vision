import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import {SystemEventsHandler} from '../system-events-handler/SystemEventsHandler';

const useGainFocus = () => {
  const gainFocusCallback = useCallback(() => {
    SystemEventsHandler.onInfo({info: 'useGainFocus->gainFocusCallback()'});
  }, []);
  useFocusEffect(gainFocusCallback);
};

export default useGainFocus;
