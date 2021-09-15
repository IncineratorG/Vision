import AuthActions from './auth/AuthActions';
import SurveillanceActions from './surveillance/SurveillanceActions';

const AppActions = () => {
  const auth = AuthActions();
  const surveillance = SurveillanceActions();

  return {
    auth,
    surveillance,
  };
};

export default AppActions();
