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
};

export default selectedDeviceLocalState;
