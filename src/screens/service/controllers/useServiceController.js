import {useCallback} from 'react';
import {BackHandler} from 'react-native';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../store/actions/AppActions';
import Services from '../../../services/Services';

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

  const testNotificationPressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'useServiceController()->testNotificationPressHandler()',
    });

    Services.services().surveillanceService.sendTestNotification();
  }, []);

  const testMotionSensorPressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'useServiceController()->testMotionSensorPressHandler()',
    });

    Services.services().surveillanceService.testMotionSensor();
  }, []);

  const testCameraMotionDetectionPressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'useServiceController()->testCameraMotionDetectionPressHandler()',
    });

    Services.services().surveillanceService.testCameraMotionDetection();
  }, []);

  return {
    backButtonPressHandler,
    stopService,
    testCameraPressHandler,
    testNotificationPressHandler,
    testMotionSensorPressHandler,
    testCameraMotionDetectionPressHandler,
  };
};

export default useServiceController;
