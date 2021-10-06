import AppActions from '../../actions/AppActions';

const initialState = {
  toggleDetectDeviceMovementRequest: {
    requestId: null,
    inProgress: false,
    completed: false,
    cancellable: true,
    cancelInProgress: false,
    cancelled: false,
    response: {
      payload: {
        detectDeviceMovementServiceRunning: false,
      },
    },
    error: {
      hasError: false,
      code: '',
      message: '',
    },
  },
};

const surveillanceToggleDetectDeviceMovementRequestReducer = (
  state = initialState,
  action,
) => {
  switch (action.type) {
    case AppActions.surveillanceToggleDetectDeviceMovementRequest.types
      .SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_BEGIN: {
      return {
        ...state,
        toggleDetectDeviceMovementRequest: {
          ...state.toggleDetectDeviceMovementRequest,
          requestId: null,
          inProgress: true,
          completed: false,
          cancellable: true,
          cancelInProgress: false,
          cancelled: false,
          error: {
            ...state.toggleDetectDeviceMovementRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceToggleDetectDeviceMovementRequest.types
      .SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_SENDED: {
      const {requestId} = action.payload;

      return {
        ...state,
        toggleDetectDeviceMovementRequest: {
          ...state.toggleDetectDeviceMovementRequest,
          requestId,
          inProgress: true,
          completed: false,
          cancellable: true,
          cancelInProgress: false,
          cancelled: false,
          error: {
            ...state.toggleDetectDeviceMovementRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceToggleDetectDeviceMovementRequest.types
      .SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_COMPLETED: {
      const {detectDeviceMovementServiceRunning} = action.payload;

      return {
        ...state,
        toggleDetectDeviceMovementRequest: {
          ...state.toggleDetectDeviceMovementRequest,
          inProgress: false,
          completed: true,
          cancelInProgress: false,
          cancelled: false,
          response: {
            ...state.toggleDetectDeviceMovementRequest.response,
            payload: {
              ...state.toggleDetectDeviceMovementRequest.response.payload,
              detectDeviceMovementServiceRunning,
            },
          },
          error: {
            ...state.toggleDetectDeviceMovementRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceToggleDetectDeviceMovementRequest.types
      .SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_ERROR: {
      const {code, message} = action.payload;

      return {
        ...state,
        toggleDetectDeviceMovementRequest: {
          ...state.toggleDetectDeviceMovementRequest,
          inProgress: false,
          completed: false,
          cancelInProgress: false,
          cancelled: false,
          error: {
            ...state.toggleDetectDeviceMovementRequest.error,
            hasError: true,
            code,
            message,
          },
        },
      };
    }

    case AppActions.surveillanceToggleDetectDeviceMovementRequest.types
      .CANCEL_SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST: {
      return {
        ...state,
        toggleDetectDeviceMovementRequest: {
          ...state.toggleDetectDeviceMovementRequest,
          cancelInProgress: true,
          cancelled: false,
        },
      };
    }

    case AppActions.surveillanceToggleDetectDeviceMovementRequest.types
      .SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST_CANCELLED: {
      return {
        ...state,
        toggleDetectDeviceMovementRequest: {
          ...state.toggleDetectDeviceMovementRequest,
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

export default surveillanceToggleDetectDeviceMovementRequestReducer;
