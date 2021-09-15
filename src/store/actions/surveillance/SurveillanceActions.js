const SurveillanceActions = () => {
  const types = {
    GET_DEVICES_IN_GROUP: 'SA_GET_DEVICES_IN_GROUP',
    GET_DEVICES_IN_GROUP_BEGIN: 'SA_GET_DEVICES_IN_GROUP_BEGIN',
    GET_DEVICES_IN_GROUP_FINISHED: 'SA_GET_DEVICES_IN_GROUP_FINISHED',
    GET_DEVICES_IN_GROUP_ERROR: 'SA_GET_DEVICES_IN_GROUP_ERROR',
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
