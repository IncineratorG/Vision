const SurveillanceActions = () => {
  const types = {
    GET_DEVICES_IN_GROUP: 'SA_GET_DEVICES_IN_GROUP',
    GET_DEVICES_IN_GROUP_BEGIN: 'SA_GET_DEVICES_IN_GROUP_BEGIN',
    GET_DEVICES_IN_GROUP_FINISHED: 'SA_GET_DEVICES_IN_GROUP_FINISHED',
    GET_DEVICES_IN_GROUP_ERROR: 'SA_GET_DEVICES_IN_GROUP_ERROR',

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

  const sendTestRequestWithPayloadCompleted = ({requestId, resultOne}) => {
    return {
      type: types.SEND_TEST_REQUEST_WITH_PAYLOAD_COMPLETED,
      payload: {requestId, resultOne},
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

export default SurveillanceActions;
