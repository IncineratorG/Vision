import tempReducer from './temp/tempReducer';
import authReducer from './auth/authReducer';

const AppReducers = () => {
  const temp = tempReducer;
  const auth = authReducer;

  return {
    temp,
    auth,
  };
};

export default AppReducers();
