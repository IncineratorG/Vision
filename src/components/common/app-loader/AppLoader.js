import React from 'react';
import AppNavigation from '../app-navigation/AppNavigation';
import useAppLoaderModel from './model/useAppLoaderModel';
import AppLoading from '../app-loading/AppLoading';

const AppLoader = () => {
  const model = useAppLoaderModel();

  return <AppNavigation />;

  // return <AppLoading />;
};

export default React.memo(AppLoader);
