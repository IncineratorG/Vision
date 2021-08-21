import React from 'react';
import MainView from './views/MainView';
import useMainModel from './models/useMainModel';
import useMainController from './controllers/useMainController';

const Main = () => {
  const model = useMainModel();
  const controller = useMainController(model);

  return <MainView model={model} controller={controller} />;
};

export default React.memo(Main);
