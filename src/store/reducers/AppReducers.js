import tempReducer from './temp/tempReducer';
import authReducer from './auth/authReducer';
import surveillanceCommonReducer from './surveillance/surveillanceCommonReducer';
import surveillanceIsDeviceAliveRequestReducer from './surveillance/surveillanceIsDeviceAliveRequestReducer';
import surveillanceTakeBackCameraImageRequestReducer from './surveillance/surveillanceTakeBackCameraImageRequestReducer';

const AppReducers = () => {
  const temp = tempReducer;
  const auth = authReducer;
  const surveillanceCommon = surveillanceCommonReducer;
  const surveillanceIsDeviceAliveRequest =
    surveillanceIsDeviceAliveRequestReducer;
  const surveillanceTakeBackCameraImageRequest =
    surveillanceTakeBackCameraImageRequestReducer;

  return {
    temp,
    auth,
    surveillanceCommon,
    surveillanceIsDeviceAliveRequest,
    surveillanceTakeBackCameraImageRequest,
  };
};

export default AppReducers();
