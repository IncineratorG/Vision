import NativeSurveillanceForegroundLib from './lib/NativeSurveillanceForegroundLib';
import NativeSurveillanceForegroundActions from './actions/NativeSurveillanceForegroundActions';

const NativeSurveillanceForeground = () => {
  const nativeService = NativeSurveillanceForegroundLib;

  const isServiceRunning = async () => {
    const action = NativeSurveillanceForegroundActions.isRunningAction();
    return await nativeService.execute(action);
  };

  const startService = async () => {
    const action = NativeSurveillanceForegroundActions.startServiceAction();
    return await nativeService.execute(action);
  };

  const stopService = async () => {
    const action = NativeSurveillanceForegroundActions.stopServiceAction();
    return await nativeService.execute(action);
  };

  return {
    isServiceRunning,
    startService,
    stopService,
  };
};

export default NativeSurveillanceForeground;
