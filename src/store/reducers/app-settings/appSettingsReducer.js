import CameraImageQuality from '../../../data/common/camera-image-quality/CameraImageQuality';
import AppActions from '../../actions/AppActions';

const initialState = {
  surveillance: {
    backCameraImage: {
      quality: CameraImageQuality.LOW,
    },
    frontCameraImage: {
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

    case AppActions.appSettings.types
      .SET_FRONT_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY: {
      const {quality} = action.payload;
      const imageQuality = quality ? quality : CameraImageQuality.LOW;

      return {
        ...state,
        surveillance: {
          ...state.surveillance,
          frontCameraImage: {
            ...state.surveillance.frontCameraImage,
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
