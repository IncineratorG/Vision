import {useCallback} from 'react';
import {BackHandler} from 'react-native';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../store/actions/AppActions';

const useServiceController = (model) => {
  const {navigation, dispatch} = model;

  const backButtonPressHandler = useCallback(() => {
    BackHandler.exitApp();
  }, []);

  const stopService = useCallback(() => {
    SystemEventsHandler.onInfo({info: 'useServiceController()->stopService()'});

    dispatch(AppActions.surveillance.actions.stopService());
  }, [dispatch]);

  return {
    backButtonPressHandler,
    stopService,
  };
};

export default useServiceController;
