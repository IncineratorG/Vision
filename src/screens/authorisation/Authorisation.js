import React from 'react';
import AuthorisationView from './views/AuthorisationView';
import useAuthorisationModel from './models/useAuthorisationModel';
import useAuthorisationController from './controllers/useAuthorisationController';

const Authorisation = () => {
  const model = useAuthorisationModel();
  const controller = useAuthorisationController(model);

  return <AuthorisationView model={model} controller={controller} />;
};

export default React.memo(Authorisation);
