import {useCallback} from 'react';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../store/actions/AppActions';

const useSettingsController = (model) => {
  const {
    navigation,
    dispatch,
    setters: {setBackCameraImageQualityVisible},
  } = model;

  const backCameraImageQualityPressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'useSettingsController()->backCameraImageQualityPressHandler()',
    });

    setBackCameraImageQualityVisible(true);
  }, [setBackCameraImageQualityVisible]);

  const backCameraImageQualityDialogCancelPressHandler = useCallback(() => {
    setBackCameraImageQualityVisible(false);
  }, [setBackCameraImageQualityVisible]);

  const backCameraImageQualityDialogImageQualitySelectHandler = useCallback(
    ({quality}) => {
      dispatch(
        AppActions.appSettings.actions.setBackCameraImageRequestImageQuality({
          quality,
        }),
      );
      setBackCameraImageQualityVisible(false);
    },
    [dispatch, setBackCameraImageQualityVisible],
  );

  return {
    backCameraImageQualityPressHandler,
    backCameraImageQualityDialogCancelPressHandler,
    backCameraImageQualityDialogImageQualitySelectHandler,
  };
};

export default useSettingsController;
