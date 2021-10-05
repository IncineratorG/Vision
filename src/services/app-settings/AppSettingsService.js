import NativeAppSettings from '../native-libs/app-settings/NativeAppSettings';

const AppSettingsService = () => {
  const nativeAppSettingsService = NativeAppSettings();

  const getAppSettings = async () => {
    return await nativeAppSettingsService.getAppSettings();
  };

  const updateAppSettings = async () => {
    return await nativeAppSettingsService.updateAppSettings();
  };

  return {
    getAppSettings,
    updateAppSettings,
  };
};

export default AppSettingsService;
