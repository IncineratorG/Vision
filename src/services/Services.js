import FirebaseService from './firebase/FirebaseService';

const Services = () => {
  const firebaseService = FirebaseService();

  const init = async () => {
    firebaseService.init();
  };

  const dispose = () => {};

  const services = () => {
    return {
      firebaseService,
    };
  };

  return {
    init,
    dispose,
    services,
  };
};

export default Services();
