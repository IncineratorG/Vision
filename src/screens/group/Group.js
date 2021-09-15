import React from 'react';
import {useCallback} from 'react';
import {useFocusEffect} from '@react-navigation/core';
import {BackHandler} from 'react-native';
import GroupView from './views/GroupView';
import useGroupModel from './models/useGroupModel';
import useGroupController from './controllers/useGroupController';

const Group = () => {
  const model = useGroupModel();
  const controller = useGroupController(model);

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

  return <GroupView model={model} controller={controller} />;
};

export default React.memo(Group);
