import FirebaseService from './firebase/FirebaseService';
import AuthService from './auth/AuthService';
import SurveillanceForegroundService from './surveillance-foreground/SurveillanceForegroundService';

const Services = () => {
  const firebaseService = FirebaseService();
  const authService = AuthService();
  const surveillanceForegroundService = SurveillanceForegroundService();

  const init = async () => {
    firebaseService.init();
  };

  const dispose = () => {};

  const services = () => {
    return {
      firebaseService,
      authService,
      surveillanceForegroundService,
    };
  };

  return {
    init,
    dispose,
    services,
  };
};

export default Services();
