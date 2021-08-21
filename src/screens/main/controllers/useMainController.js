import {useCallback} from 'react';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import wait from '../../../utils/common/wait/wait';

const useMainController = (model) => {
  const callback1 = useCallback(async () => {}, []);

  const callback2 = useCallback(async () => {}, []);

  return {
    callback1,
    callback2,
  };
};

export default useMainController;
