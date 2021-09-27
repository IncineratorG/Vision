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
  imageViewer: {
    visible: false,
    image: null,
  },
};

export default groupLocalState;
