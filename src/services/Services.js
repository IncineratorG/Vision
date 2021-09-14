import FirebaseService from './firebase/FirebaseService';
import AuthService from './auth/AuthService';
import SurveillanceService from './surveillance/SurveillanceService';

const Services = () => {
  const firebaseService = FirebaseService();
  const authService = AuthService();
  const surveillanceService = SurveillanceService();

  const init = async () => {
    firebaseService.init();
  };

  const dispose = () => {};

  const services = () => {
    return {
      firebaseService,
      authService,
      surveillanceService,
    };
  };

  return {
    init,
    dispose,
    services,
  };
};

export default Services();
