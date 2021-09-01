import FirebaseService from './firebase/FirebaseService';
import AuthService from './auth/AuthService';

const Services = () => {
  const firebaseService = FirebaseService();
  const authService = AuthService();

  const init = async () => {
    firebaseService.init();
  };

  const dispose = () => {};

  const services = () => {
    return {
      firebaseService,
      authService,
    };
  };

  return {
    init,
    dispose,
    services,
  };
};

export default Services();
