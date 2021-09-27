import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import {useNavigation, useFocusEffect} from '@react-navigation/core';
import {useDispatch, useSelector} from 'react-redux';

const useSettingsModel = () => {
  const navigation = useNavigation();

  const dispatch = useDispatch();

  const [
    backCameraImageQualityDialogVisible,
    setBackCameraImageQualityVisible,
  ] = useState(false);
  const [
    frontCameraImageQualityDialogVisible,
    setFrontCameraImageQualityVisible,
  ] = useState(false);

  const backCameraImageQuality = useSelector(
    (state) => state.appSettings.surveillance.backCameraImage.quality,
  );
  const frontCameraImageQuality = useSelector(
    (state) => state.appSettings.surveillance.frontCameraImage.quality,
  );

  return {
    data: {
      backCameraImageQuality,
      backCameraImageQualityDialogVisible,
      frontCameraImageQuality,
      frontCameraImageQualityDialogVisible,
    },
    setters: {
      setBackCameraImageQualityVisible,
      setFrontCameraImageQualityVisible,
    },
    navigation,
    dispatch,
  };
};

export default useSettingsModel;
