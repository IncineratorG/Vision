import CameraImageQuality from '../../../data/common/camera-image-quality/CameraImageQuality';
import AppActions from '../../actions/AppActions';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';

const initialState = {
  getAppSettings: {
    inProgress: false,
    error: {
      hasError: false,
      code: '',
      message: '',
    },
  },
  surveillance: {
    backCameraImage: {
      quality: CameraImageQuality.LOW,
    },
    frontCameraImage: {
      quality: CameraImageQuality.LOW,
    },
    notifications: {
      receiveNotificationsFromCurrentGroup: true,
    },
  },
};

const appSettingsReducer = (state = initialState, action) => {
  switch (action.type) {
    case AppActions.appSettings.types.GET_APP_SETTINGS_BEGIN: {
      return {
        ...state,
        getAppSettings: {
          ...state.getAppSettings,
          inProgress: true,
          error: {
            ...state.getAppSettings.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.appSettings.types.GET_APP_SETTINGS_FINISHED: {
      const {appSettings} = action.payload;

      SystemEventsHandler.onInfo({
        info:
          'appSettingsReducer->GET_APP_SETTINGS_FINISHED: ' +
          JSON.stringify(appSettings),
      });

      return {
        ...state,
        getAppSettings: {
          ...state.getAppSettings,
          inProgress: false,
          error: {
            ...state.getAppSettings.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.appSettings.types.GET_APP_SETTINGS_ERROR: {
      const {code, message} = action.payload;

      return {
        ...state,
        getAppSettings: {
          ...state.getAppSettings,
          inProgress: false,
          error: {
            ...state.getAppSettings.error,
            hasError: true,
            code,
            message,
          },
        },
      };
    }

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

    case AppActions.appSettings.types
      .SET_RECEIVE_NOTIFICATIONS_FROM_CURRENT_GROUP: {
      const {receive} = action.payload;

      return {
        ...state,
        surveillance: {
          ...state.surveillance,
          notifications: {
            ...state.surveillance.notifications,
            receiveNotificationsFromCurrentGroup: receive,
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
