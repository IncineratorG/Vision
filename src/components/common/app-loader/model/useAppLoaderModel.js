import {useCallback, useEffect} from 'react';
import {BackHandler} from 'react-native';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import Services from '../../../../services/Services';

const useAppLoaderModel = () => {
  const backButtonHandler = useCallback(() => {
    SystemEventsHandler.onInfo({info: 'BACK_BUTTON_HANDLER'});

    Services.dispose();

    // return true;
  }, []);

  useEffect(() => {
    SystemEventsHandler.onInfo({info: 'HERE'});

    const backHandler = BackHandler.addEventListener(
      'hardwareBackPress',
      backButtonHandler,
    );

    return () => {
      SystemEventsHandler.onInfo({info: 'THERE'});

      backHandler.remove();
    };
  }, [backButtonHandler]);

  return {
    data: {},
  };
};

export default useAppLoaderModel;
