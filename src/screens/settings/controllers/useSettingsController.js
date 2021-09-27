import {useCallback} from 'react';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../store/actions/AppActions';

const useSettingsController = (model) => {
  const {
    navigation,
    dispatch,
    setters: {
      setBackCameraImageQualityVisible,
      setFrontCameraImageQualityVisible,
    },
  } = model;

  const backCameraImageQualityPressHandler = useCallback(() => {
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

  const frontCameraImageQualityPressHandler = useCallback(() => {
    setFrontCameraImageQualityVisible(true);
  }, [setFrontCameraImageQualityVisible]);

  const frontCameraImageQualityDialogCancelPressHandler = useCallback(() => {
    setFrontCameraImageQualityVisible(false);
  }, [setFrontCameraImageQualityVisible]);

  const frontCameraImageQualityDialogImageQualitySelectHandler = useCallback(
    ({quality}) => {
      dispatch(
        AppActions.appSettings.actions.setFrontCameraImageRequestImageQuality({
          quality,
        }),
      );
      setFrontCameraImageQualityVisible(false);
    },
    [setFrontCameraImageQualityVisible, dispatch],
  );

  return {
    backCameraImageQualityPressHandler,
    backCameraImageQualityDialogCancelPressHandler,
    backCameraImageQualityDialogImageQualitySelectHandler,
    frontCameraImageQualityPressHandler,
    frontCameraImageQualityDialogCancelPressHandler,
    frontCameraImageQualityDialogImageQualitySelectHandler,
  };
};

export default useSettingsController;
