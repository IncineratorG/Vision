import NativeSecureFlagLib from '../lib/NativeFirebaseLib';

const NativeFirebaseConstants = () => {
  const {
    actionTypes: {TEST_ACTION},
  } = NativeSecureFlagLib.getConstants();

  return {
    actionTypes: {
      TEST_ACTION,
    },
  };
};

export default NativeFirebaseConstants();
