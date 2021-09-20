import React, {useCallback} from 'react';
import AuthorisationView from './views/AuthorisationView';
import useAuthorisationModel from './models/useAuthorisationModel';
import useAuthorisationController from './controllers/useAuthorisationController';
import {BackHandler} from 'react-native';
import {useFocusEffect} from '@react-navigation/core';

const Authorisation = () => {
  const model = useAuthorisationModel();
  const controller = useAuthorisationController(model);

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

  return <AuthorisationView model={model} controller={controller} />;
};

export default React.memo(Authorisation);
