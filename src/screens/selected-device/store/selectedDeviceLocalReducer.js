import SelectedDeviceLocalActions from './SelectedDeviceLocalActions';

const selectedDeviceLocalReducer = (state, action) => {
  switch (action.type) {
    case SelectedDeviceLocalActions.types.SET_DEVICE_DATA: {
      const {device} = action.payload;

      return {
        ...state,
        device: {...device},
      };
    }

    case SelectedDeviceLocalActions.types
      .SET_CURRENT_REQUEST_STATUS_DIALOG_DATA: {
      return {
        ...state,
        currentRequestStatusDialog: {
          ...state.currentRequestStatusDialog,
          visible: action.payload.visible,
          statusText: action.payload.statusText,
          leftButtonVisible: action.payload.leftButtonVisible,
          leftButtonText: action.payload.leftButtonText,
          leftButtonPressHandler: action.payload.leftButtonPressHandler,
          rightButtonVisible: action.payload.rightButtonVisible,
          rightButtonText: action.payload.rightButtonText,
          rightButtonPressHandler: action.payload.rightButtonPressHandler,
          onCancel: action.payload.onCancel,
        },
      };
    }

    case SelectedDeviceLocalActions.types
      .CLEAR_CURRENT_REQUEST_STATUS_DIALOG_DATA: {
      return {
        ...state,
        currentRequestStatusDialog: {
          ...state.currentRequestStatusDialog,
          visible: false,
          statusText: '',
          leftButtonVisible: false,
          leftButtonText: '',
          leftButtonPressHandler: null,
          rightButtonVisible: false,
          rightButtonText: '',
          rightButtonPressHandler: null,
          onCancel: null,
        },
      };
    }

    case SelectedDeviceLocalActions.types.OPEN_IMAGE_VIEWER: {
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

    case SelectedDeviceLocalActions.types.CLOSE_IMAGE_VIEWER: {
      return {
        ...state,
        imageViewer: {
          ...state.imageViewer,
          visible: false,
          image: null,
        },
      };
    }

    case SelectedDeviceLocalActions.types
      .SET_CAMERA_RECOGNIZE_PERSON_SETTINGS_DIALOG_DATA: {
      return {
        ...state,
        cameraRecognizePersonSettingsDialog: {
          ...state.cameraRecognizePersonSettingsDialog,
          visible: action.payload.visible,
          image: action.payload.image,
          confirmSettingsButtonPressHandler:
            action.payload.confirmSettingsButtonPressHandler,
          cancelButtonPressHandler: action.payload.cancelButtonPressHandler,
          onCancel: action.payload.onCancel,
        },
      };
    }

    case SelectedDeviceLocalActions.types
      .CLEAR_CAMERA_RECOGNIZE_PERSON_SETTINGS_DIALOG_DATA: {
      return {
        ...state,
        cameraRecognizePersonSettingsDialog: {
          ...state.cameraRecognizePersonSettingsDialog,
          visible: false,
          image: null,
          confirmSettingsButtonPressHandler: null,
          cancelButtonPressHandler: null,
          onCancel: null,
        },
      };
    }

    default: {
      return state;
    }
  }
};

export default selectedDeviceLocalReducer;
