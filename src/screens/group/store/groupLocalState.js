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
  },
  requestStatusDialog: {
    visible: false,
    completed: false,
    responseData: null,
    canViewResponse: false,
    responseViewerCallback: null,
  },
};

export default groupLocalState;
