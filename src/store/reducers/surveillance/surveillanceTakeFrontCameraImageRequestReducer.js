import AppActions from '../../actions/AppActions';

const initialState = {
  takeFrontCameraImageRequest: {
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

const surveillanceTakeFrontCameraImageRequestReducer = (
  state = initialState,
  action,
) => {
  switch (action.type) {
    case AppActions.surveillanceTakeFrontCameraImageRequest.types
      .SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_BEGIN: {
      return {
        ...state,
        takeFrontCameraImageRequest: {
          ...state.takeFrontCameraImageRequest,
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
            ...state.takeFrontCameraImageRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceTakeFrontCameraImageRequest.types
      .SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_SENDED: {
      const {requestId} = action.payload;

      return {
        ...state,
        takeFrontCameraImageRequest: {
          ...state.takeFrontCameraImageRequest,
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
            ...state.takeFrontCameraImageRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceTakeFrontCameraImageRequest.types
      .SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_COMPLETED: {
      const {image} = action.payload;

      return {
        ...state,
        takeFrontCameraImageRequest: {
          ...state.takeFrontCameraImageRequest,
          // requestId: null,
          inProgress: false,
          completed: true,
          // cancellable: true,
          cancelInProgress: false,
          cancelled: false,
          response: {
            ...state.takeFrontCameraImageRequest.response,
            payload: {
              ...state.takeFrontCameraImageRequest.response.payload,
              image,
            },
          },
          error: {
            ...state.takeFrontCameraImageRequest.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillanceTakeFrontCameraImageRequest.types
      .SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_ERROR: {
      const {code, message} = action.payload;

      return {
        ...state,
        takeFrontCameraImageRequest: {
          ...state.takeFrontCameraImageRequest,
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
            ...state.takeFrontCameraImageRequest.error,
            hasError: true,
            code,
            message,
          },
        },
      };
    }

    case AppActions.surveillanceTakeFrontCameraImageRequest.types
      .CANCEL_SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST: {
      return {
        ...state,
        takeFrontCameraImageRequest: {
          ...state.takeFrontCameraImageRequest,
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
          //   ...state.takeFrontCameraImageRequest.error,
          //   hasError: false,
          //   code: '',
          //   message: '',
          // },
        },
      };
    }

    case AppActions.surveillanceTakeFrontCameraImageRequest.types
      .SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_CANCELLED: {
      return {
        ...state,
        takeFrontCameraImageRequest: {
          ...state.takeFrontCameraImageRequest,
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
          //   ...state.takeFrontCameraImageRequest.error,
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

export default surveillanceTakeFrontCameraImageRequestReducer;
