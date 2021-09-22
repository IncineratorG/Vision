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
      const {requestId} = action.payload;

      return {
        ...state,
        requestInProgressDialog: {
          ...state.requestInProgressDialog,
          requestId,
        },
      };
    }

    default: {
      return state;
    }
  }
};

export default groupLocalReducer;
