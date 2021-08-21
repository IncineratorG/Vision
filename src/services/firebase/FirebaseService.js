import NativeFirebase from '../native-libs/firebase/NativeFirebase';

const FirebaseService = () => {
  const nativeFirebaseService = NativeFirebase();

  const init = () => {};

  const test = async () => {
    return await nativeFirebaseService.test();
  };

  return {
    init,
    test,
  };
};

export default FirebaseService;
