const AppSettingsActions = () => {
  const types = {
    SET_BACK_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY:
      'ASA_SET_BACK_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY',
  };

  const setBackCameraImageRequestImageQuality = ({quality}) => {
    return {
      type: types.SET_BACK_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY,
      payload: {quality},
    };
  };

  return {
    types,
    actions: {
      setBackCameraImageRequestImageQuality,
    },
  };
};

export default AppSettingsActions;
