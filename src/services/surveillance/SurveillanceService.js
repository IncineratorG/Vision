import NativeSurveillance from '../native-libs/surveillance/NativeSurveillance';

const SurveillanceService = () => {
  const nativeService = NativeSurveillance();

  const isServiceRunning = async () => {
    return nativeService.isServiceRunning();
  };

  const startService = async () => {
    return await nativeService.startService();
  };

  const stopService = async () => {
    return await nativeService.stopService();
  };

  const testRequest = async () => {
    return await nativeService.testRequest();
  };

  return {
    isServiceRunning,
    startService,
    stopService,
    testRequest,
  };
};

export default SurveillanceService;
