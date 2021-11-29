import AuthActions from './auth/AuthActions';
import SurveillanceCommonActions from './surveillance/SurveillanceCommonActions';
import SurveillanceIsDeviceAliveRequestActions from './surveillance/SurveillanceIsDeviceAliveRequestActions';
import SurveillanceTakeBackCameraImageRequestActions from './surveillance/SurveillanceTakeBackCameraImageRequestActions';
import AppSettingsActions from './app-settings/AppSettingsActions';
import SurveillanceTakeFrontCameraImageRequestActions from './surveillance/SurveillanceTakeFrontCameraImageRequestActions';
import SurveillanceToggleDetectDeviceMovementRequestActions from './surveillance/SurveillanceToggleDetectDeviceMovementRequestActions';
import SurveillanceToggleRecognizePersonRequestActions from './surveillance/SurveillanceToggleRecognizePersonRequestActions';
import SurveillanceGetCameraRecognizePersonSettingsRequestActions from './surveillance/SurveillanceGetCameraRecognizePersonSettingsRequestActions';

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
  const surveillanceToggleDetectDeviceMovementRequest =
    SurveillanceToggleDetectDeviceMovementRequestActions();
  const surveillanceToggleRecognizePersonRequest =
    SurveillanceToggleRecognizePersonRequestActions();
  const surveillanceGetCameraRecognizePersonSettingsRequest =
    SurveillanceGetCameraRecognizePersonSettingsRequestActions();

  return {
    auth,
    appSettings,
    surveillanceCommon,
    surveillanceIsDeviceAliveRequest,
    surveillanceTakeBackCameraImageRequest,
    surveillanceTakeFrontCameraImageRequest,
    surveillanceToggleDetectDeviceMovementRequest,
    surveillanceToggleRecognizePersonRequest,
    surveillanceGetCameraRecognizePersonSettingsRequest,
  };
};

export default AppActions();
