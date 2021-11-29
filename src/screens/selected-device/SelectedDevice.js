import React, {useCallback} from 'react';
import SelectedDeviceView from './views/SelectedDeviceView';
import useSelectedDeviceModel from './models/useSelectedDeviceModel';
import useSelectedDeviceController from './controllers/useSelectedDeviceController';
import {useFocusEffect} from '@react-navigation/core';

const SelectedDevice = () => {
  const model = useSelectedDeviceModel();
  const controller = useSelectedDeviceController(model);

  const {
    navigation,
    data: {
      localState: {device},
    },
  } = model;

  const deviceName = device ? device.deviceName : '';

  const setScreenHeaderBar = useCallback(() => {
    navigation.setOptions({
      title: deviceName,
    });
  }, [deviceName, navigation]);
  useFocusEffect(setScreenHeaderBar);

  return <SelectedDeviceView model={model} controller={controller} />;
};

export default React.memo(SelectedDevice);
