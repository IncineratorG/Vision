import NativeSurveillanceLib from './lib/NativeSurveillanceLib';
import NativeSurveillanceActions from './actions/NativeSurveillanceActions';

const NativeSurveillance = () => {
  const nativeService = NativeSurveillanceLib;

  const isServiceRunning = async () => {
    const action = NativeSurveillanceActions.isRunningAction();
    return await nativeService.execute(action);
  };

  const startService = async () => {
    const action = NativeSurveillanceActions.startServiceAction();
    return await nativeService.execute(action);
  };

  const stopService = async () => {
    const action = NativeSurveillanceActions.stopServiceAction();
    return await nativeService.execute(action);
  };

  const testRequest = async () => {
    const action = NativeSurveillanceActions.testRequestAction();
    return await nativeService.execute(action);
  };

  const getDevicesInGroup = async ({groupName, groupPassword, deviceName}) => {
    const action = NativeSurveillanceActions.getDevicesInGroupAction({
      groupName,
      groupPassword,
      deviceName,
    });
    return await nativeService.execute(action);
  };

  const sendRequest = async ({
    receiverDeviceName,
    requestType,
    requestPayload,
  }) => {
    const action = NativeSurveillanceActions.sendRequest({
      receiverDeviceName,
      requestType,
      requestPayload,
    });
    return await nativeService.execute(action);
  };

  return {
    isServiceRunning,
    startService,
    stopService,
    testRequest,
    getDevicesInGroup,
    sendRequest,
  };
};

export default NativeSurveillance;
