import React from 'react';
import AppNavigation from '../app-navigation/AppNavigation';
import useAppLoaderModel from './model/useAppLoaderModel';
import AppLoading from '../app-loading/AppLoading';

const AppLoader = () => {
  const {
    data: {servicesReady, loggedIn},
  } = useAppLoaderModel();

  if (!servicesReady) {
    return <AppLoading />;
  } else {
    return <AppNavigation />;
  }
};

export default React.memo(AppLoader);
