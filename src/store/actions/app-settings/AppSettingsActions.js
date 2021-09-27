const AppSettingsActions = () => {
  const types = {
    SET_BACK_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY:
      'ASA_SET_BACK_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY',
    SET_FRONT_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY:
      'ASA_SET_FRONT_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY',
  };

  const setBackCameraImageRequestImageQuality = ({quality}) => {
    return {
      type: types.SET_BACK_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY,
      payload: {quality},
    };
  };

  const setFrontCameraImageRequestImageQuality = ({quality}) => {
    return {
      type: types.SET_FRONT_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY,
      payload: {quality},
    };
  };

  return {
    types,
    actions: {
      setBackCameraImageRequestImageQuality,
      setFrontCameraImageRequestImageQuality,
    },
  };
};

export default AppSettingsActions;
