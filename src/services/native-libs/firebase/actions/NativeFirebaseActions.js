import NativeFirebaseConstants from '../constants/NativeFirebaseConstants';

const NativeFirebaseActions = () => {
  const {
    actionTypes: {TEST_ACTION},
  } = NativeFirebaseConstants;

  const testAction = () => {
    return {
      type: TEST_ACTION,
    };
  };

  return {
    testAction,
  };
};

export default NativeFirebaseActions();
