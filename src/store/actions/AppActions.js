import AuthActions from './auth/AuthActions';
import SurveillanceCommonActions from './surveillance/SurveillanceCommonActions';
import SurveillanceIsDeviceAliveRequestActions from './surveillance/SurveillanceIsDeviceAliveRequestActions';

const AppActions = () => {
  const auth = AuthActions();
  const surveillanceCommon = SurveillanceCommonActions();
  const surveillanceIsDeviceAliveRequest =
    SurveillanceIsDeviceAliveRequestActions();

  return {
    auth,
    surveillanceCommon,
    surveillanceIsDeviceAliveRequest,
  };
};

export default AppActions();
