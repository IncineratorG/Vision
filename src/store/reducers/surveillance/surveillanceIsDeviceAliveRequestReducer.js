import AppActions from '../../actions/AppActions';

const initialState = {
  isDeviceAliveRequest: {
    requestId: '',
    inProgress: false,
    completed: false,
    cancellable: true,
    cancelInProgress: false,
    cancelled: false,
    response: {
      payload: {
        isAlive: false,
      },
    },
    error: {
      hasError: false,
      code: '',
      message: '',
    },
  },
};

const surveillanceIsDeviceAliveRequestReducer = (
  state = initialState,
  action,
) => {
  switch (action.type) {
    case AppActions.surveillanceIsDeviceAliveRequest.types
      .SEND_IS_ALIVE_REQUEST_BEGIN: {
      return {
        ...state,
        isDeviceAliveRequest: {
          ...state.isDeviceAliveRequest,
          requestId: null,
          inProgress: true,
          completed: false,
          cancelInProgress: false,
          cancelled: false,
          error: {
            ...state.isDeviceAliveRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceIsDeviceAliveRequest.types
      .SEND_IS_ALIVE_REQUEST_SENDED: {
      const {requestId} = action.payload;

      return {
        ...state,
        isDeviceAliveRequest: {
          ...state.isDeviceAliveRequest,
          requestId,
          inProgress: true,
          completed: false,
          cancelInProgress: false,
          cancelled: false,
          error: {
            ...state.isDeviceAliveRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceIsDeviceAliveRequest.types
      .SEND_IS_ALIVE_REQUEST_COMPLETED: {
      const {isAlive} = action.payload;

      return {
        ...state,
        isDeviceAliveRequest: {
          ...state.isDeviceAliveRequest,
          inProgress: false,
          completed: true,
          cancelInProgress: false,
          cancelled: false,
          response: {
            ...state.isDeviceAliveRequest.response,
            payload: {
              ...state.isDeviceAliveRequest.response.payload,
              isAlive,
            },
          },
          error: {
            ...state.isDeviceAliveRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceIsDeviceAliveRequest.types
      .SEND_IS_ALIVE_REQUEST_ERROR: {
      const {code, message} = action.payload;

      return {
        ...state,
        isDeviceAliveRequest: {
          ...state.isDeviceAliveRequest,
          inProgress: false,
          completed: false,
          cancelInProgress: false,
          cancelled: false,
          error: {
            ...state.isDeviceAliveRequest.error,
            hasError: true,
            code,
            message,
          },
        },
      };
    }

    case AppActions.surveillanceIsDeviceAliveRequest.types
      .CANCEL_SEND_IS_ALIVE_REQUEST: {
      return {
        ...state,
        isDeviceAliveRequest: {
          ...state.isDeviceAliveRequest,
          inProgress: true,
          completed: false,
          cancelInProgress: true,
          cancelled: false,
          error: {
            ...state.isDeviceAliveRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceIsDeviceAliveRequest.types
      .SEND_IS_ALIVE_REQUEST_CANCELLED: {
      return {
        ...state,
        isDeviceAliveRequest: {
          ...state.isDeviceAliveRequest,
          inProgress: false,
          completed: false,
          cancelInProgress: false,
          cancelled: true,
          error: {
            ...state.isDeviceAliveRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    default: {
      return state;
    }
  }
};

export default surveillanceIsDeviceAliveRequestReducer;
