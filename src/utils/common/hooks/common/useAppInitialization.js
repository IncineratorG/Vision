import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';
import wait from '../../wait/wait';

const useAppInitialization = () => {
  const [initialized, setInitialized] = useState(false);

  useEffect(() => {
    const initialize = async () => {
      await wait(500);
      setInitialized(true);
    };

    initialize();
  }, []);

  return {
    initialized,
  };
};
