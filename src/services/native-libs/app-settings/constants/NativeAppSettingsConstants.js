import NativeAppSettingsLib from '../lib/NativeAppSettingsLib';

const NativeAppSettingsConstants = () => {
  const {
    actionTypes: {GET_APP_SETTINGS, UPDATE_APP_SETTINGS},
  } = NativeAppSettingsLib.getConstants();

  return {
    actionTypes: {
      GET_APP_SETTINGS,
      UPDATE_APP_SETTINGS,
    },
  };
};

export default NativeAppSettingsConstants();
