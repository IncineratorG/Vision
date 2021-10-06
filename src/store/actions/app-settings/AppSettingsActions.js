const AppSettingsActions = () => {
  const types = {
    GET_APP_SETTINGS: 'ASA_GET_APP_SETTINGS',
    GET_APP_SETTINGS_BEGIN: 'ASA_GET_APP_SETTINGS_BEGIN',
    GET_APP_SETTINGS_FINISHED: 'ASA_GET_APP_SETTINGS_FINISHED',
    GET_APP_SETTINGS_ERROR: 'ASA_GET_APP_SETTINGS_ERROR',

    SET_BACK_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY:
      'ASA_SET_BACK_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY',
    SET_FRONT_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY:
      'ASA_SET_FRONT_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY',
    SET_RECEIVE_NOTIFICATIONS_FROM_CURRENT_GROUP:
      'ASA_SET_RECEIVE_NOTIFICATIONS_FROM_CURRENT_GROUP',
  };

  const getAppSettings = () => {
    return {
      type: types.GET_APP_SETTINGS,
    };
  };

  const getAppSettingsBegin = () => {
    return {
      type: types.GET_APP_SETTINGS_BEGIN,
    };
  };

  const getAppSettingsFinished = ({appSettings}) => {
    return {
      type: types.GET_APP_SETTINGS_FINISHED,
      payload: {appSettings},
    };
  };

  const getAppSettingsError = ({code, message}) => {
    return {
      type: types.GET_APP_SETTINGS_ERROR,
      payload: {code, message},
    };
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
      getAppSettings,
      getAppSettingsBegin,
      getAppSettingsFinished,
      getAppSettingsError,
      setBackCameraImageRequestImageQuality,
      setFrontCameraImageRequestImageQuality,
      setReceiveNotificationsFromCurrentGroup,
    },
  };
};

export default AppSettingsActions;
