import {useCallback} from 'react';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import Services from '../../../services/Services';

const useMainController = (model) => {
  const callback1 = useCallback(async () => {
    SystemEventsHandler.onInfo({info: 'callback1'});

    Services.services().firebaseService.test();
  }, []);

  const callback2 = useCallback(async () => {
    SystemEventsHandler.onInfo({info: 'callback2'});
  }, []);

  return {
    callback1,
    callback2,
  };
};

export default useMainController;
