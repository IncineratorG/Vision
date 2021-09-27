import AuthActions from './auth/AuthActions';
import SurveillanceCommonActions from './surveillance/SurveillanceCommonActions';
import SurveillanceIsDeviceAliveRequestActions from './surveillance/SurveillanceIsDeviceAliveRequestActions';
import SurveillanceTakeBackCameraImageRequestActions from './surveillance/SurveillanceTakeBackCameraImageRequestActions';
import AppSettingsActions from './app-settings/AppSettingsActions';

const AppActions = () => {
  const auth = AuthActions();
  const appSettings = AppSettingsActions();
  const surveillanceCommon = SurveillanceCommonActions();
  const surveillanceIsDeviceAliveRequest =
    SurveillanceIsDeviceAliveRequestActions();
  const surveillanceTakeBackCameraImageRequest =
    SurveillanceTakeBackCameraImageRequestActions();

  return {
    auth,
    appSettings,
    surveillanceCommon,
    surveillanceIsDeviceAliveRequest,
    surveillanceTakeBackCameraImageRequest,
  };
};

export default AppActions();
