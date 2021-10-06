import AppActions from '../../actions/AppActions';

const initialState = {
  takeBackCameraImageRequest: {
    requestId: '',
    inProgress: false,
    completed: false,
    cancellable: true,
    cancelInProgress: false,
    cancelled: false,
    response: {
      payload: {
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

const surveillanceTakeBackCameraImageRequestReducer = (
  state = initialState,
  action,
) => {
  switch (action.type) {
    case AppActions.surveillanceTakeBackCameraImageRequest.types
      .SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_BEGIN: {
      return {
        ...state,
        takeBackCameraImageRequest: {
          ...state.takeBackCameraImageRequest,
          requestId: null,
          inProgress: true,
          completed: false,
          // cancellable: true,
          cancelInProgress: false,
          cancelled: false,
          // response: {
          //   payload: {
          //     image: null,
          //   },
          // },
          error: {
            ...state.takeBackCameraImageRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceTakeBackCameraImageRequest.types
      .SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_SENDED: {
      const {requestId} = action.payload;

      return {
        ...state,
        takeBackCameraImageRequest: {
          ...state.takeBackCameraImageRequest,
          requestId,
          inProgress: true,
          completed: false,
          // cancellable: true,
          cancelInProgress: false,
          cancelled: false,
          // response: {
          //   payload: {
          //     image: null,
          //   },
          // },
          error: {
            ...state.takeBackCameraImageRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceTakeBackCameraImageRequest.types
      .SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_COMPLETED: {
      const {image} = action.payload;

      return {
        ...state,
        takeBackCameraImageRequest: {
          ...state.takeBackCameraImageRequest,
          // requestId: null,
          inProgress: false,
          completed: true,
          // cancellable: true,
          cancelInProgress: false,
          cancelled: false,
          response: {
            ...state.takeBackCameraImageRequest.response,
            payload: {
              ...state.takeBackCameraImageRequest.response.payload,
              image,
            },
          },
          error: {
            ...state.takeBackCameraImageRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceTakeBackCameraImageRequest.types
      .SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_ERROR: {
      const {code, message} = action.payload;

      return {
        ...state,
        takeBackCameraImageRequest: {
          ...state.takeBackCameraImageRequest,
          // requestId: null,
          inProgress: false,
          completed: false,
          // cancellable: true,
          cancelInProgress: false,
          cancelled: false,
          // response: {
          //   payload: {
          //     image: null,
          //   },
          // },
          error: {
            ...state.takeBackCameraImageRequest.error,
            hasError: true,
            code,
            message,
          },
        },
      };
    }

    case AppActions.surveillanceTakeBackCameraImageRequest.types
      .CANCEL_SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST: {
      return {
        ...state,
        takeBackCameraImageRequest: {
          ...state.takeBackCameraImageRequest,
          // requestId: null,
          // inProgress: true,
          // completed: false,
          // cancellable: true,
          cancelInProgress: true,
          cancelled: false,
          // response: {
          //   payload: {
          //     image: null,
          //   },
          // },
          // error: {
          //   ...state.takeBackCameraImageRequest.error,
          //   hasError: false,
          //   code: '',
          //   message: '',
          // },
        },
      };
    }

    case AppActions.surveillanceTakeBackCameraImageRequest.types
      .SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_CANCELLED: {
      return {
        ...state,
        takeBackCameraImageRequest: {
          ...state.takeBackCameraImageRequest,
          // requestId: null,
          inProgress: false,
          completed: false,
          // cancellable: true,
          cancelInProgress: false,
          cancelled: true,
          // response: {
          //   payload: {
          //     image: null,
          //   },
          // },
          // error: {
          //   ...state.takeBackCameraImageRequest.error,
          //   hasError: false,
          //   code: '',
          //   message: '',
          // },
        },
      };
    }

    default: {
      return state;
    }
  }
};

export default surveillanceTakeBackCameraImageRequestReducer;
