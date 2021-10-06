import FirebaseService from './firebase/FirebaseService';
import AuthService from './auth/AuthService';
import SurveillanceService from './surveillance/SurveillanceService';
import AppSettingsService from './app-settings/AppSettingsService';

const Services = () => {
  const firebaseService = FirebaseService();
  const authService = AuthService();
  const surveillanceService = SurveillanceService();
  const appSettingsService = AppSettingsService();

  const init = async () => {
    // firebaseService.init();
  };

  const dispose = () => {};

  const services = () => {
    return {
      firebaseService,
      authService,
      surveillanceService,
      appSettingsService,
    };
  };

  return {
    init,
    dispose,
    services,
  };
};

export default Services();
