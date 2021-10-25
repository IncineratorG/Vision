import AppActions from '../../actions/AppActions';

const initialState = {
  toggleRecognizePersonRequest: {
    requestId: null,
    inProgress: false,
    completed: false,
    cancellable: true,
    cancelInProgress: false,
    cancelled: false,
    response: {
      payload: {
        frontCameraRecognizePersonServiceRunning: false,
        backCameraRecognizePersonServiceRunning: false,
      },
    },
    error: {
      hasError: false,
      code: '',
      message: '',
    },
  },
};

const surveillanceToggleRecognizePersonRequestReducer = (
  state = initialState,
  action,
) => {
  switch (action.type) {
    case AppActions.surveillanceToggleRecognizePersonRequest.types.CLEAR: {
      return {
        ...state,
        toggleRecognizePersonRequest: {
          ...state.toggleRecognizePersonRequest,
          requestId: null,
          inProgress: false,
          completed: false,
          cancellable: true,
          cancelInProgress: false,
          cancelled: false,
          error: {
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceToggleRecognizePersonRequest.types
      .SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_BEGIN: {
      return {
        ...state,
        toggleRecognizePersonRequest: {
          ...state.toggleRecognizePersonRequest,
          requestId: null,
          inProgress: true,
          completed: false,
          cancellable: true,
          cancelInProgress: false,
          cancelled: false,
          error: {
            ...state.toggleRecognizePersonRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceToggleRecognizePersonRequest.types
      .SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_SENDED: {
      const {requestId} = action.payload;

      return {
        ...state,
        toggleRecognizePersonRequest: {
          ...state.toggleRecognizePersonRequest,
          requestId,
          inProgress: true,
          completed: false,
          cancellable: true,
          cancelInProgress: false,
          cancelled: false,
          error: {
            ...state.toggleRecognizePersonRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceToggleRecognizePersonRequest.types
      .SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_COMPLETED: {
      const {
        frontCameraRecognizePersonServiceRunning,
        backCameraRecognizePersonServiceRunning,
      } = action.payload;

      return {
        ...state,
        toggleRecognizePersonRequest: {
          ...state.toggleRecognizePersonRequest,
          inProgress: false,
          completed: true,
          cancellable: true,
          cancelInProgress: false,
          cancelled: false,
          response: {
            ...state.toggleRecognizePersonRequest.response,
            frontCameraRecognizePersonServiceRunning,
            backCameraRecognizePersonServiceRunning,
          },
          error: {
            ...state.toggleRecognizePersonRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceToggleRecognizePersonRequest.types
      .SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_ERROR: {
      const {code, message} = action.payload;

      return {
        ...state,
        toggleRecognizePersonRequest: {
          ...state.toggleRecognizePersonRequest,
          inProgress: false,
          completed: false,
          cancellable: true,
          cancelInProgress: false,
          cancelled: false,
          error: {
            ...state.toggleRecognizePersonRequest.error,
            hasError: true,
            code,
            message,
          },
        },
      };
    }

    case AppActions.surveillanceToggleRecognizePersonRequest.types
      .CANCEL_SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST: {
      return {
        ...state,
        toggleRecognizePersonRequest: {
          ...state.toggleRecognizePersonRequest,
          cancelInProgress: true,
          cancelled: false,
        },
      };
    }

    case AppActions.surveillanceToggleRecognizePersonRequest.types
      .SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST_CANCELLED: {
      return {
        ...state,
        toggleRecognizePersonRequest: {
          ...state.toggleRecognizePersonRequest,
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

export default surveillanceToggleRecognizePersonRequestReducer;
