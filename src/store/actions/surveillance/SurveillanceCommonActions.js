const SurveillanceCommonActions = () => {
  const types = {
    GET_DEVICES_IN_GROUP: 'SA_GET_DEVICES_IN_GROUP',
    GET_DEVICES_IN_GROUP_BEGIN: 'SA_GET_DEVICES_IN_GROUP_BEGIN',
    GET_DEVICES_IN_GROUP_FINISHED: 'SA_GET_DEVICES_IN_GROUP_FINISHED',
    GET_DEVICES_IN_GROUP_ERROR: 'SA_GET_DEVICES_IN_GROUP_ERROR',

    START_SERVICE: 'SA_START_SERVICE',
    START_SERVICE_BEGIN: 'SA_START_SERVICE_BEGIN',
    START_SERVICE_FINISHED: 'SA_START_SERVICE_FINISHED',
    START_SERVICE_ERROR: 'SA_START_SERVICE_ERROR',

    STOP_SERVICE: 'SA_STOP_SERVICE',
    STOP_SERVICE_BEGIN: 'SA_STOP_SERVICE_BEGIN',
    STOP_SERVICE_FINISHED: 'SA_STOP_SERVICE_FINISHED',
    STOP_SERVICE_ERROR: 'SA_STOP_SERVICE_ERROR',

    CHECK_SERVICE_STATUS: 'SA_CHECK_SERVICE_STATUS',
    CHECK_SERVICE_STATUS_BEGIN: 'SA_CHECK_SERVICE_STATUS_BEGIN',
    CHECK_SERVICE_STATUS_FINISHED: 'SA_CHECK_SERVICE_STATUS_FINISHED',
    CHECK_SERVICE_STATUS_ERROR: 'SA_CHECK_SERVICE_STATUS_ERROR',

    SEND_TEST_REQUEST_WITH_PAYLOAD: 'SA_SEND_TEST_REQUEST_WITH_PAYLOAD',
    SEND_TEST_REQUEST_WITH_PAYLOAD_BEGIN:
      'SA_SEND_TEST_REQUEST_WITH_PAYLOAD_BEGIN',
    SEND_TEST_REQUEST_WITH_PAYLOAD_SENDED:
      'SA_SEND_TEST_REQUEST_WITH_PAYLOAD_SENDED',
    SEND_TEST_REQUEST_WITH_PAYLOAD_COMPLETED:
      'SA_SEND_TEST_REQUEST_WITH_PAYLOAD_COMPLETED',
    SEND_TEST_REQUEST_WITH_PAYLOAD_ERROR:
      'SA_SEND_TEST_REQUEST_WITH_PAYLOAD_ERROR',
    CANCEL_TEST_REQUEST_WITH_PAYLOAD: 'SA_CANCEL_TEST_REQUEST_WITH_PAYLOAD',
    TEST_REQUEST_WITH_PAYLOAD_CANCELLED:
      'SA_TEST_REQUEST_WITH_PAYLOAD_CANCELLED',
  };

  const getDevicesInGroup = ({groupName, groupPassword, deviceName}) => {
    return {
      type: types.GET_DEVICES_IN_GROUP,
      payload: {groupName, groupPassword, deviceName},
    };
  };

  const getDevicesInGroupBegin = () => {
    return {
      type: types.GET_DEVICES_IN_GROUP_BEGIN,
    };
  };

  const getDevicesInGroupFinished = ({
    groupName,
    groupPassword,
    deviceName,
    devicesArray,
  }) => {
    return {
      type: types.GET_DEVICES_IN_GROUP_FINISHED,
      payload: {groupName, groupPassword, deviceName, devicesArray},
    };
  };

  const getDevicesInGroupError = ({code, message}) => {
    return {
      type: types.GET_DEVICES_IN_GROUP_ERROR,
      payload: {code, message},
    };
  };

  const startService = () => {
    return {
      type: types.START_SERVICE,
    };
  };

  const startServiceBegin = () => {
    return {
      type: types.START_SERVICE_BEGIN,
    };
  };

  const startServiceFinished = () => {
    return {
      type: types.START_SERVICE_FINISHED,
    };
  };

  const startServiceError = ({code, message}) => {
    return {
      type: types.START_SERVICE_ERROR,
      payload: {code, message},
    };
  };

  const stopService = () => {
    return {
      type: types.STOP_SERVICE,
    };
  };

  const stopServiceBegin = () => {
    return {
      type: types.STOP_SERVICE_BEGIN,
    };
  };

  const stopServiceFinished = () => {
    return {
      type: types.STOP_SERVICE_FINISHED,
    };
  };

  const stopServiceError = ({code, message}) => {
    return {
      type: types.STOP_SERVICE_ERROR,
      payload: {code, message},
    };
  };

  const checkServiceStatus = () => {
    return {
      type: types.CHECK_SERVICE_STATUS,
    };
  };

  const checkServiceStatusBegin = () => {
    return {
      type: types.CHECK_SERVICE_STATUS_BEGIN,
    };
  };

  const checkServiceStatusFinished = ({isRunning}) => {
    return {
      type: types.CHECK_SERVICE_STATUS_FINISHED,
      payload: {isRunning},
    };
  };

  const checkServiceStatusError = ({code, message}) => {
    return {
      type: types.CHECK_SERVICE_STATUS_ERROR,
      payload: {code, message},
    };
  };

  const sendTestRequestWithPayload = ({
    receiverDeviceName,
    valueOne,
    valueTwo,
  }) => {
    return {
      type: types.SEND_TEST_REQUEST_WITH_PAYLOAD,
      payload: {receiverDeviceName, valueOne, valueTwo},
    };
  };

  const sendTestRequestWithPayloadBegin = () => {
    return {
      type: types.SEND_TEST_REQUEST_WITH_PAYLOAD_BEGIN,
    };
  };

  const sendTestRequestWithPayloadSended = ({requestId}) => {
    return {
      type: types.SEND_TEST_REQUEST_WITH_PAYLOAD_SENDED,
      payload: {requestId},
    };
  };

  const sendTestRequestWithPayloadCompleted = ({resultOne}) => {
    return {
      type: types.SEND_TEST_REQUEST_WITH_PAYLOAD_COMPLETED,
      payload: {resultOne},
    };
  };

  const sendTestRequestWithPayloadError = ({code, message}) => {
    return {
      type: types.SEND_TEST_REQUEST_WITH_PAYLOAD_ERROR,
      payload: {code, message},
    };
  };

  const cancelTestRequestWithPayload = () => {
    return {
      type: types.CANCEL_TEST_REQUEST_WITH_PAYLOAD,
    };
  };

  const testRequestWithPayloadCancelled = () => {
    return {
      type: types.TEST_REQUEST_WITH_PAYLOAD_CANCELLED,
    };
  };

  return {
    types,
    actions: {
      getDevicesInGroup,
      getDevicesInGroupBegin,
      getDevicesInGroupFinished,
      getDevicesInGroupError,
      startService,
      startServiceBegin,
      startServiceFinished,
      startServiceError,
      stopService,
      stopServiceBegin,
      stopServiceFinished,
      stopServiceError,
      checkServiceStatus,
      checkServiceStatusBegin,
      checkServiceStatusFinished,
      checkServiceStatusError,
      sendTestRequestWithPayload,
      sendTestRequestWithPayloadBegin,
      sendTestRequestWithPayloadSended,
      sendTestRequestWithPayloadCompleted,
      sendTestRequestWithPayloadError,
      cancelTestRequestWithPayload,
      testRequestWithPayloadCancelled,
    },
  };
};

export default SurveillanceCommonActions;
