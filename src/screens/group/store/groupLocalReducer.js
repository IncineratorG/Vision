import GroupLocalActions from './GroupLocalActions';

const groupLocalReducer = (state, action) => {
  switch (action.type) {
    case GroupLocalActions.types.SET_DEVICE_REQUESTS_DIALOG_VISIBILITY: {
      return {
        ...state,
        deviceRequestsDialog: {
          ...state.deviceRequestsDialog,
          visible: action.payload.visible,
        },
      };
    }

    case GroupLocalActions.types.SET_DEVICE_REQUESTS_DIALOG_DATA: {
      const {device} = action.payload;

      return {
        ...state,
        deviceRequestsDialog: {
          ...state.deviceRequestsDialog,
          device: {...device},
        },
      };
    }

    case GroupLocalActions.types.SET_SELECTED_DEVICE_ERROR_DIALOG_VISIBILITY: {
      return {
        ...state,
        selectedDeviceErrorDialog: {
          ...state.selectedDeviceErrorDialog,
          visible: action.payload.visible,
        },
      };
    }

    case GroupLocalActions.types
      .SET_SELECTED_DEVICE_ERROR_DIALOG_ERROR_MESSAGE: {
      return {
        ...state,
        selectedDeviceErrorDialog: {
          ...state.selectedDeviceErrorDialog,
          message: action.payload.message,
        },
      };
    }

    case GroupLocalActions.types.SET_REQUEST_IN_PROGRESS_DIALOG_VISIBILITY: {
      return {
        ...state,
        requestInProgressDialog: {
          ...state.requestInProgressDialog,
          visible: action.payload.visible,
        },
      };
    }

    case GroupLocalActions.types.SET_REQUEST_IN_PROGRESS_DIALOG_DATA: {
      const {requestId, cancelRequestCallback} = action.payload;

      return {
        ...state,
        requestInProgressDialog: {
          ...state.requestInProgressDialog,
          requestId,
          cancelRequestCallback,
        },
      };
    }

    case GroupLocalActions.types.SET_REQUEST_STATUS_DIALOG_VISIBILITY: {
      const {visible} = action.payload;

      return {
        ...state,
        requestStatusDialog: {
          ...state.requestStatusDialog,
          visible,
          // completed: false,
          // responseData: null,
        },
      };
    }

    case GroupLocalActions.types.SET_REQUEST_STATUS_DIALOG_RESPONSE_DATA: {
      const {data, canViewResponse, responseViewerCallback} = action.payload;

      return {
        ...state,
        requestStatusDialog: {
          ...state.requestStatusDialog,
          completed: true,
          responseData: data,
          canViewResponse,
          responseViewerCallback,
        },
      };
    }

    case GroupLocalActions.types.CLEAR_REQUEST_STATUS_DIALOG_DATA: {
      return {
        ...state,
        requestStatusDialog: {
          ...state.requestStatusDialog,
          completed: false,
          responseData: null,
          canViewResponse: false,
          responseViewerCallback: null,
        },
      };
    }

    case GroupLocalActions.types.OPEN_IMAGE_VIEWER: {
      const {image} = action.payload;

      return {
        ...state,
        imageViewer: {
          ...state.imageViewer,
          visible: true,
          image,
        },
      };
    }

    case GroupLocalActions.types.CLOSE_IMAGE_VIEWER: {
      return {
        ...state,
        imageViewer: {
          ...state.imageViewer,
          visible: false,
          image: null,
        },
      };
    }

    default: {
      return state;
    }
  }
};

export default groupLocalReducer;
