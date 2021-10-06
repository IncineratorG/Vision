import React, {useCallback} from 'react';
import {useFocusEffect} from '@react-navigation/core';
import {BackHandler} from 'react-native';
import GroupView from './views/GroupView';
import useGroupModel from './models/useGroupModel';
import useGroupRootController from './controllers/useGroupRootController';
import GroupHeaderRightButton from '../../components/specific/group/header-right-button/GroupHeaderRightButton';

const Group = () => {
  const model = useGroupModel();
  const controller = useGroupRootController(model);

  const {navigation} = model;

  const {
    groupController: {backButtonPressHandler},
    headerController: {headerRightButtonPressHandler},
  } = controller;

  const setScreenHeaderBar = useCallback(() => {
    navigation.setOptions({
      headerRight: (props) => {
        return (
          <GroupHeaderRightButton onPress={headerRightButtonPressHandler} />
        );
      },
    });
  }, [headerRightButtonPressHandler, navigation]);
  useFocusEffect(setScreenHeaderBar);

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
