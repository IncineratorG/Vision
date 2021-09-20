import React from 'react';
import {useCallback} from 'react';
import {useFocusEffect} from '@react-navigation/core';
import ServiceView from './views/ServiceView';
import useServiceModel from './models/useServiceModel';
import useServiceController from './controllers/useServiceController';
import {BackHandler} from 'react-native';

const Service = () => {
  const model = useServiceModel();
  const controller = useServiceController(model);

  const {backButtonPressHandler} = controller;

  const setBackButtonPressHandler = useCallback(() => {
    const backHandler = BackHandler.addEventListener(
      'hardwareBackPress',
      backButtonPressHandler,
    );

    return () => {
      backHandler.remove();
    };
  }, [backButtonPressHandler]);
  useFocusEffect(setBackButtonPressHandler);

  return <ServiceView model={model} controller={controller} />;
};

export default React.memo(Service);
