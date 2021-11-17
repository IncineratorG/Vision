import React from 'react';
import SelectedDeviceView from './views/SelectedDeviceView';
import useSelectedDeviceModel from './models/useSelectedDeviceModel';
import useSelectedDeviceController from './controllers/useSelectedDeviceController';

const SelectedDevice = () => {
  const model = useSelectedDeviceModel();
  const controller = useSelectedDeviceController(model);

  return <SelectedDeviceView model={model} controller={controller} />;
};

export default React.memo(SelectedDevice);
