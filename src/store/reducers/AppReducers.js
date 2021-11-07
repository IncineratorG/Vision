import tempReducer from './temp/tempReducer';
import authReducer from './auth/authReducer';
import surveillanceCommonReducer from './surveillance/surveillanceCommonReducer';
import surveillanceIsDeviceAliveRequestReducer from './surveillance/surveillanceIsDeviceAliveRequestReducer';
import surveillanceTakeBackCameraImageRequestReducer from './surveillance/surveillanceTakeBackCameraImageRequestReducer';
import appSettingsReducer from './app-settings/appSettingsReducer';
import surveillanceTakeFrontCameraImageRequestReducer from './surveillance/surveillanceTakeFrontCameraImageRequestReducer';
import surveillanceToggleDetectDeviceMovementRequestReducer from './surveillance/surveillanceToggleDetectDeviceMovementRequestReducer';
import surveillanceToggleRecognizePersonRequestReducer from './surveillance/surveillanceToggleRecognizePersonRequestReducer';
import surveillanceGetCameraRecognizePersonSettingsRequestReducer from './surveillance/surveillanceGetCameraRecognizePersonSettingsRequestReducer';

const AppReducers = () => {
  const temp = tempReducer;
  const auth = authReducer;
  const appSettings = appSettingsReducer;
  const surveillanceCommon = surveillanceCommonReducer;
  const surveillanceIsDeviceAliveRequest =
    surveillanceIsDeviceAliveRequestReducer;
  const surveillanceTakeBackCameraImageRequest =
    surveillanceTakeBackCameraImageRequestReducer;
  const surveillanceTakeFrontCameraImageRequest =
    surveillanceTakeFrontCameraImageRequestReducer;
  const surveillanceToggleDetectDeviceMovementRequest =
    surveillanceToggleDetectDeviceMovementRequestReducer;
  const surveillanceToggleRecognizePersonRequest =
    surveillanceToggleRecognizePersonRequestReducer;
  const surveillanceGetCameraRecognizePersonSettingsRequest =
    surveillanceGetCameraRecognizePersonSettingsRequestReducer;

  return {
    temp,
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

export default AppReducers();
