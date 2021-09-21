import tempReducer from './temp/tempReducer';
import authReducer from './auth/authReducer';
import surveillanceCommonReducer from './surveillance/surveillanceCommonReducer';
import surveillanceIsDeviceAliveRequestReducer from './surveillance/surveillanceIsDeviceAliveRequestReducer';

const AppReducers = () => {
  const temp = tempReducer;
  const auth = authReducer;
  const surveillanceCommon = surveillanceCommonReducer;
  const surveillanceIsDeviceAliveRequest =
    surveillanceIsDeviceAliveRequestReducer;

  return {
    temp,
    auth,
    surveillanceCommon,
    surveillanceIsDeviceAliveRequest,
  };
};

export default AppReducers();
