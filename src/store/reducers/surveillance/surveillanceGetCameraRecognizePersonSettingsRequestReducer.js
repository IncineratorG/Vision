import AppActions from '../../actions/AppActions';

const initialState = {
  getCameraRecognizePersonSettingsRequest: {
    requestId: '',
    inProgress: false,
    completed: false,
    cancellable: true,
    cancelInProgress: false,
    cancelled: false,
    response: {
      payload: {
        cameraType: null,
        image: null,
      },
    },
    error: {
      hasError: false,
      code: '',
      message: '',
    },
  },
};

const surveillanceGetCameraRecognizePersonSettingsRequestReducer = (
  state = initialState,
  action,
) => {
  switch (action.type) {
    case AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.types
      .CLEAR: {
      return {
        ...state,
        getCameraRecognizePersonSettingsRequest: {
          ...state.getCameraRecognizePersonSettingsRequest,
          requestId: null,
          inProgress: false,
          completed: false,
          cancellable: true,
          cancelInProgress: false,
          cancelled: false,
          error: {
            ...state.getCameraRecognizePersonSettingsRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.types
      .SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_BEGIN: {
      const {cameraType} = action.payload;

      return {
        ...state,
        getCameraRecognizePersonSettingsRequest: {
          ...state.getCameraRecognizePersonSettingsRequest,
          requestId: null,
          inProgress: true,
          completed: false,
          cancelInProgress: false,
          cancelled: false,
          response: {
            ...state.getCameraRecognizePersonSettingsRequest.response,
            payload: {
              ...state.getCameraRecognizePersonSettingsRequest.response.payload,
              cameraType,
            },
          },
          error: {
            ...state.getCameraRecognizePersonSettingsRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.types
      .SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_SENDED: {
      const {requestId} = action.payload;

      return {
        ...state,
        getCameraRecognizePersonSettingsRequest: {
          ...state.getCameraRecognizePersonSettingsRequest,
          requestId,
          inProgress: true,
          completed: false,
          cancellable: true,
          cancelInProgress: false,
          cancelled: false,
          error: {
            ...state.getCameraRecognizePersonSettingsRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.types
      .SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_COMPLETED: {
      const {image} = action.payload;

      return {
        ...state,
        getCameraRecognizePersonSettingsRequest: {
          ...state.getCameraRecognizePersonSettingsRequest,
          inProgress: false,
          completed: true,
          cancellable: true,
          cancelInProgress: false,
          cancelled: false,
          response: {
            ...state.getCameraRecognizePersonSettingsRequest.response,
            payload: {
              ...state.getCameraRecognizePersonSettingsRequest.response.payload,
              image,
            },
          },
          error: {
            ...state.getCameraRecognizePersonSettingsRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.types
      .SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_ERROR: {
      const {code, message} = action.payload;

      return {
        ...state,
        getCameraRecognizePersonSettingsRequest: {
          ...state.getCameraRecognizePersonSettingsRequest,
          inProgress: false,
          completed: false,
          cancellable: true,
          cancelInProgress: false,
          cancelled: false,
          error: {
            ...state.getCameraRecognizePersonSettingsRequest.error,
            hasError: true,
            code,
            message,
          },
        },
      };
    }

    case AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.types
      .CANCEL_SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST: {
      return {
        ...state,
        getCameraRecognizePersonSettingsRequest: {
          ...state.getCameraRecognizePersonSettingsRequest,
          cancelInProgress: true,
          cancelled: false,
        },
      };
    }

    case AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.types
      .SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST_CANCELLED: {
      return {
        ...state,
        getCameraRecognizePersonSettingsRequest: {
          ...state.getCameraRecognizePersonSettingsRequest,
          inProgress: false,
          completed: false,
          cancelInProgress: false,
          cancelled: true,
        },
      };
    }

    default: {
      return state;
    }
  }
};

export default surveillanceGetCameraRecognizePersonSettingsRequestReducer;
