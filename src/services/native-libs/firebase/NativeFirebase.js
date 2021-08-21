import NativeFirebaseLib from './lib/NativeFirebaseLib';
import NativeFirebaseActions from './actions/NativeFirebaseActions';

const NativeFirebase = () => {
  const nativeFirebaseService = NativeFirebaseLib;

  const test = async () => {
    const action = NativeFirebaseActions.testAction();
    return await nativeFirebaseService.execute(action);
  };

  return {
    test,
  };
};

export default NativeFirebase;
