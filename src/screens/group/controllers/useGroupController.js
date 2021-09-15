import {useCallback} from 'react';
import {BackHandler} from 'react-native';

const useGroupController = (model) => {
  const backButtonPressHandler = useCallback(() => {
    BackHandler.exitApp();
  }, []);

  return {
    backButtonPressHandler,
  };
};

export default useGroupController;
