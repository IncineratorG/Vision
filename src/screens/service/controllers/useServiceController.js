import {useCallback} from 'react';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../store/actions/AppActions';

const useServiceController = (model) => {
  const {navigation, dispatch} = model;

  const stopService = useCallback(() => {
    SystemEventsHandler.onInfo({info: 'useServiceController()->stopService()'});

    dispatch(AppActions.surveillance.actions.stopService());
  }, [dispatch]);

  return {
    stopService,
  };
};

export default useServiceController;
