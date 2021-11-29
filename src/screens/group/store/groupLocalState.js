const groupLocalState = {
  deviceRequestsDialog: {
    visible: false,
    device: null,
  },
  selectedDeviceErrorDialog: {
    visible: false,
    message: '',
  },
  requestInProgressDialog: {
    visible: false,
    requestId: null,
    cancelRequestCallback: null,
  },
  requestStatusDialog: {
    visible: false,
    completed: false,
    responseData: null,
    canViewResponse: false,
    responseViewerCallback: null,
  },
  currentRequestStatusDialog: {
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
  cameraRecognizePersonSettingsDialog: {
    visible: false,
    image: null,
    confirmSettingsButtonPressHandler: null,
    cancelButtonPressHandler: null,
    onCancel: null,
  },
  imageViewer: {
    visible: false,
    image: null,
  },
};

export default groupLocalState;
