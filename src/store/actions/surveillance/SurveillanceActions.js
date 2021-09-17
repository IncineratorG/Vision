const SurveillanceActions = () => {
  const types = {
    GET_DEVICES_IN_GROUP: 'SA_GET_DEVICES_IN_GROUP',
    GET_DEVICES_IN_GROUP_BEGIN: 'SA_GET_DEVICES_IN_GROUP_BEGIN',
    GET_DEVICES_IN_GROUP_FINISHED: 'SA_GET_DEVICES_IN_GROUP_FINISHED',
    GET_DEVICES_IN_GROUP_ERROR: 'SA_GET_DEVICES_IN_GROUP_ERROR',

    // ===
    // =====
    // TEST_SEND_REQUEST: 'SA_SEND_TEST_REQUEST',
    // TEST_SEND_REQUEST_BEGIN: 'SA_SEND_TEST_REQUEST_BEGIN',
    // TEST_SEND_REQUEST_REQUEST_SEND: 'SA_SEND_TEST_REQUEST_REQUEST_SEND',
    // TEST_SEND_REQUEST_REQUEST_COMPLETE: 'SA_SEND_TEST_REQUEST_REQUEST_COMPLETE',
    // TEST_SEND_REQUEST_REQUEST_CANCELLED:
    //   'SA_SEND_TEST_REQUEST_REQUEST_CANCELLED',
    // TEST_SEND_REQUEST_ERROR: 'SA_SEND_TEST_REQUEST_ERROR',
    //
    // TEST_CANCEL_REQUEST: 'SA_CANCEL_TEST_REQUEST',
    // TEST_CANCEL_REQUEST_BEGIN: 'SA_CANCEL_TEST_REQUEST_BEGIN',
    // TEST_CANCEL_REQUEST_FINISHED: 'SA_CANCEL_TEST_REQUEST_FINISHED',
    // TEST_CANCEL_REQUEST_ERROR: 'SA_CANCEL_TEST_REQUEST_ERROR',
    // =====
    // ===

    // SEND_TEST_REQUEST: 'SA_SEND_TEST_REQUEST',
    // SEND_TEST_REQUEST_BEGIN: 'SA_SEND_TEST_REQUEST_BEGIN',
    // SEND_TEST_REQUEST_REQUEST_SEND: 'SA_SEND_TEST_REQUEST_REQUEST_SEND',
    // SEND_TEST_REQUEST_REQUEST_COMPLETE: 'SA_SEND_TEST_REQUEST_REQUEST_COMPLETE',
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

  // ===
  // =====
  //  const testSendRequest = ({type, requestPayload}) => {
  //    return {
  //      type: types.TEST_SEND_REQUEST,
  //      payload: {type, requestPayload},
  //    };
  //  };
  //
  //  const testSendRequestBegin = () => {
  //    return {
  //      type: types.TEST_SEND_REQUEST_BEGIN,
  //    };
  //  };
  //
  //  const testSendRequestRequestSend = ({requestId}) => {
  //    return {
  //      type: types.TEST_SEND_REQUEST_REQUEST_SEND,
  //      payload: {requestId},
  //    };
  //  };
  //
  //  const testSendRequestRequestComplete = ({requestId, requestResult}) => {
  //    return {
  //      type: types.TEST_SEND_REQUEST_REQUEST_COMPLETE,
  //      payload: {requestId, requestResult},
  //    };
  //  };
  //
  //  const sendTestRequestRequestCancelled = ({requestId}) => {
  //    return {
  //      type: types.TEST_SEND_REQUEST_REQUEST_CANCELLED,
  //      payload: {requestId},
  //    };
  //  };
  //
  //  const sendTestRequestError = ({code, message}) => {
  //    return {
  //      type: types.TEST_SEND_REQUEST_ERROR,
  //      payload: {code, message},
  //    };
  //  };
  //
  //  /*
  //  TEST_CANCEL_REQUEST: 'SA_CANCEL_TEST_REQUEST',
  //  TEST_CANCEL_REQUEST_BEGIN: 'SA_CANCEL_TEST_REQUEST_START',
  //  TEST_CANCEL_REQUEST_FINISHED: 'SA_CANCEL_TEST_REQUEST_FINISHED',
  //  TEST_CANCEL_REQUEST_ERROR: 'SA_CANCEL_TEST_REQUEST_ERROR',
  // */
  //
  //  const testCancelRequest = ({requestId}) => {
  //    return {
  //      type: types.TEST_CANCEL_REQUEST,
  //    }
  //  };
  // =====
  // ===

  return {
    types,
    actions: {
      getDevicesInGroup,
      getDevicesInGroupBegin,
      getDevicesInGroupFinished,
      getDevicesInGroupError,
    },
  };
};

export default SurveillanceActions;
