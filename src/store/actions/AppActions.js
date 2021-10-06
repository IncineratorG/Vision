import AuthActions from './auth/AuthActions';
import SurveillanceCommonActions from './surveillance/SurveillanceCommonActions';
import SurveillanceIsDeviceAliveRequestActions from './surveillance/SurveillanceIsDeviceAliveRequestActions';
import SurveillanceTakeBackCameraImageRequestActions from './surveillance/SurveillanceTakeBackCameraImageRequestActions';
import AppSettingsActions from './app-settings/AppSettingsActions';
import SurveillanceTakeFrontCameraImageRequestActions from './surveillance/SurveillanceTakeFrontCameraImageRequestActions';

const AppActions = () => {
  const auth = AuthActions();
  const appSettings = AppSettingsActions();
  const surveillanceCommon = SurveillanceCommonActions();
  const surveillanceIsDeviceAliveRequest =
    SurveillanceIsDeviceAliveRequestActions();
  const surveillanceTakeBackCameraImageRequest =
    SurveillanceTakeBackCameraImageRequestActions();
  const surveillanceTakeFrontCameraImageRequest =
    SurveillanceTakeFrontCameraImageRequestActions();

  return {
    auth,
    appSettings,
    surveillanceCommon,
    surveillanceIsDeviceAliveRequest,
    surveillanceTakeBackCameraImageRequest,
    surveillanceTakeFrontCameraImageRequest,
  };
};

export default AppActions();
