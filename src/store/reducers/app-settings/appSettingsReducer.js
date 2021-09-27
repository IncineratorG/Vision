import CameraImageQuality from '../../../data/common/camera-image-quality/CameraImageQuality';
import AppActions from '../../actions/AppActions';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';

const initialState = {
  surveillance: {
    backCameraImage: {
      quality: CameraImageQuality.LOW,
    },
  },
};

const appSettingsReducer = (state = initialState, action) => {
  switch (action.type) {
    case AppActions.appSettings.types
      .SET_BACK_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY: {
      const {quality} = action.payload;
      const imageQuality = quality ? quality : CameraImageQuality.LOW;

      return {
        ...state,
        surveillance: {
          ...state.surveillance,
          backCameraImage: {
            ...state.surveillance.backCameraImage,
            quality: imageQuality,
          },
        },
      };
    }

    default: {
      return state;
    }
  }
};

export default appSettingsReducer;
