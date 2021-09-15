import {useCallback, useEffect} from 'react';
import {BackHandler} from 'react-native';
import {useDispatch, useSelector} from 'react-redux';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import Services from '../../../../services/Services';
import useAppStateChangeCallback from '../../../../utils/common/hooks/useAppStateChangeCallback';
import useAppServices from '../hooks/useAppServices';

const useAppLoaderModel = () => {
  const dispatch = useDispatch();

  const {servicesReady} = useAppServices();

  const {groupName, groupPassword, deviceName, loggedIn} = useSelector(
    (store) => store.auth.authInfo,
  );

  const backButtonHandler = useCallback(() => {
    Services.dispose();
  }, []);

  useEffect(() => {
    const backHandler = BackHandler.addEventListener(
      'hardwareBackPress',
      backButtonHandler,
    );

    return () => {
      backHandler.remove();
    };
  }, [backButtonHandler]);

  // ===
  useAppStateChangeCallback({
    callback: () => {
      SystemEventsHandler.onInfo({info: 'GOING_TO_FOREGROUND'});
    },
    runOnGoingToForeground: true,
  });

  useAppStateChangeCallback({
    callback: () => {
      SystemEventsHandler.onInfo({info: 'GOING_TO_BACKGROUND'});
    },
    runOnGoingToBackground: true,
  });
  // ===

  return {
    data: {
      servicesReady,
      loggedIn,
    },
  };
};

export default useAppLoaderModel;
