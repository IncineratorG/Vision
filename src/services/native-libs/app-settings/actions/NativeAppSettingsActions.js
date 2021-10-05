import NativeAppSettingsConstants from '../constants/NativeAppSettingsConstants';

const NativeAppSettingsActions = () => {
  const {
    actionTypes: {GET_APP_SETTINGS, UPDATE_APP_SETTINGS},
  } = NativeAppSettingsConstants;

  const getAppSettingsAction = () => {
    return {
      type: GET_APP_SETTINGS,
    };
  };

  const updateAppSettings = () => {
    return {
      type: UPDATE_APP_SETTINGS,
    };
  };

  return {
    getAppSettingsAction,
    updateAppSettings,
  };
};

export default NativeAppSettingsActions();
