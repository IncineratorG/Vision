import tempReducer from './temp/tempReducer';
import authReducer from './auth/authReducer';
import surveillanceCommonReducer from './surveillance/surveillanceCommonReducer';
import surveillanceIsDeviceAliveRequestReducer from './surveillance/surveillanceIsDeviceAliveRequestReducer';
import surveillanceTakeBackCameraImageRequestReducer from './surveillance/surveillanceTakeBackCameraImageRequestReducer';
import appSettingsReducer from './app-settings/appSettingsReducer';

const AppReducers = () => {
  const temp = tempReducer;
  const auth = authReducer;
  const appSettings = appSettingsReducer;
  const surveillanceCommon = surveillanceCommonReducer;
  const surveillanceIsDeviceAliveRequest =
    surveillanceIsDeviceAliveRequestReducer;
  const surveillanceTakeBackCameraImageRequest =
    surveillanceTakeBackCameraImageRequestReducer;

  return {
    temp,
    auth,
    appSettings,
    surveillanceCommon,
    surveillanceIsDeviceAliveRequest,
    surveillanceTakeBackCameraImageRequest,
  };
};

export default AppReducers();
