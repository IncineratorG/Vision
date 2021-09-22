import AuthActions from './auth/AuthActions';
import SurveillanceCommonActions from './surveillance/SurveillanceCommonActions';
import SurveillanceIsDeviceAliveRequestActions from './surveillance/SurveillanceIsDeviceAliveRequestActions';
import SurveillanceTakeBackCameraImageRequestActions from './surveillance/SurveillanceTakeBackCameraImageRequestActions';

const AppActions = () => {
  const auth = AuthActions();
  const surveillanceCommon = SurveillanceCommonActions();
  const surveillanceIsDeviceAliveRequest =
    SurveillanceIsDeviceAliveRequestActions();
  const surveillanceTakeBackCameraImageRequest =
    SurveillanceTakeBackCameraImageRequestActions();

  return {
    auth,
    surveillanceCommon,
    surveillanceIsDeviceAliveRequest,
    surveillanceTakeBackCameraImageRequest,
  };
};

export default AppActions();
