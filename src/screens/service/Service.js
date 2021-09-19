import React from 'react';
import ServiceView from './views/ServiceView';
import useServiceModel from './models/useServiceModel';
import useServiceController from './controllers/useServiceController';

const Service = () => {
  const model = useServiceModel();
  const controller = useServiceController(model);

  return <ServiceView model={model} controller={controller} />;
};

export default React.memo(Service);
