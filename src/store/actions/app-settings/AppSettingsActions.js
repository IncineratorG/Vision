const AppSettingsActions = () => {
  const types = {
    SET_BACK_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY:
      'ASA_SET_BACK_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY',
    SET_FRONT_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY:
      'ASA_SET_FRONT_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY',
    SET_RECEIVE_NOTIFICATIONS_FROM_CURRENT_GROUP:
      'ASA_SET_RECEIVE_NOTIFICATIONS_FROM_CURRENT_GROUP',
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

  const setReceiveNotificationsFromCurrentGroup = ({receive}) => {
    return {
      type: types.SET_RECEIVE_NOTIFICATIONS_FROM_CURRENT_GROUP,
      payload: {receive},
    };
  };

  return {
    types,
    actions: {
      setBackCameraImageRequestImageQuality,
      setFrontCameraImageRequestImageQuality,
      setReceiveNotificationsFromCurrentGroup,
    },
  };
};

export default AppSettingsActions;
