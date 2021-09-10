import AuthActions from './auth/AuthActions';

const AppActions = () => {
  const auth = AuthActions();

  return {
    auth,
  };
};

export default AppActions();
