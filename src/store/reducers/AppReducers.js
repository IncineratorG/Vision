import tempReducer from './temp/tempReducer';

const AppReducers = () => {
  const temp = tempReducer;

  return {
    temp,
  };
};

export default AppReducers();
