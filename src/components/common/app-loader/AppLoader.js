import React from 'react';
import AppNavigation from '../app-navigation/AppNavigation';
import useAppLoaderModel from './model/useAppLoaderModel';

const AppLoader = () => {
  const model = useAppLoaderModel();

  return <AppNavigation />;
};

export default React.memo(AppLoader);
