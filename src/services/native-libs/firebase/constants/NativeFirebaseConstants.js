import NativeFirebaseLib from '../lib/NativeFirebaseLib';

const NativeFirebaseConstants = () => {
  const {
    actionTypes: {TEST_ACTION},
  } = NativeFirebaseLib.getConstants();

  return {
    actionTypes: {
      TEST_ACTION,
    },
  };
};

export default NativeFirebaseConstants();
