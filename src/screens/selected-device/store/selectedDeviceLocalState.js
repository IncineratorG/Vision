const selectedDeviceLocalState = {
  device: null,
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
  imageViewer: {
    visible: false,
    image: null,
  },
  cameraRecognizePersonSettingsDialog: {
    visible: false,
    image: null,
    confirmSettingsButtonPressHandler: null,
    cancelButtonPressHandler: null,
    onCancel: null,
  },
};

export default selectedDeviceLocalState;
