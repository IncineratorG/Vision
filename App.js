import React from 'react';
import {Provider as StoreProvider} from 'react-redux';
import AppLoader from './src/components/common/app-loader/AppLoader';
import store from './src/store';

const App = () => {
  return (
    <StoreProvider store={store}>
      <AppLoader />
    </StoreProvider>
  );
};

export default App;
