import NativeAppSettingsLib from './lib/NativeAppSettingsLib';
import NativeAppSettingsActions from './actions/NativeAppSettingsActions';

const NativeAppSettings = () => {
  const nativeAppSettingsService = NativeAppSettingsLib;

  const getAppSettings = async () => {
    const action = NativeAppSettingsActions.getAppSettingsAction();
    return await nativeAppSettingsService.execute(action);
  };

  const updateAppSettings = async ({appSettings}) => {
    const action = NativeAppSettingsActions.updateAppSettings({appSettings});
    return await nativeAppSettingsService.execute(action);
  };

  return {
    getAppSettings,
    updateAppSettings,
  };
};

export default NativeAppSettings;
