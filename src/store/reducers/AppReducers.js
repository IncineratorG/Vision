import tempReducer from './temp/tempReducer';
import authReducer from './auth/authReducer';
import surveillanceReducer from './surveillance/surveillanceReducer';

const AppReducers = () => {
  const temp = tempReducer;
  const auth = authReducer;
  const surveillance = surveillanceReducer;

  return {
    temp,
    auth,
    surveillance,
  };
};

export default AppReducers();
