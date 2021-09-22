import {useCallback} from 'react';
import {BackHandler} from 'react-native';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../store/actions/AppActions';

const useServiceController = (model) => {
  const {navigation, dispatch} = model;

  const backButtonPressHandler = useCallback(() => {
    BackHandler.exitApp();

    return true;
  }, []);

  const stopService = useCallback(() => {
    SystemEventsHandler.onInfo({info: 'useServiceController()->stopService()'});

    dispatch(AppActions.surveillanceCommon.actions.stopService());
  }, [dispatch]);

  const testCameraPressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'useServiceController()->testCameraPressHandler()',
    });

    dispatch(
      AppActions.surveillanceTakeBackCameraImageRequest.actions.sendTakeBackCameraImageRequest(
        {receiverDeviceName: 'c'},
      ),
    );
  }, [dispatch]);

  return {
    backButtonPressHandler,
    stopService,
    testCameraPressHandler,
  };
};

export default useServiceController;
