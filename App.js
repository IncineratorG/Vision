import React from 'react';
import {Provider as StoreProvider} from 'react-redux';
import {DefaultTheme, Provider as PaperProvider} from 'react-native-paper';
import store from './src/store';
import AppNavigation from './src/components/common/app-navigation/AppNavigation';

const theme = {
  ...DefaultTheme,
  roundness: 2,
  colors: {
    ...DefaultTheme.colors,
    mode: 'exact',
    // primary: '#3498db',
    // accent: '#f1c40f',
    // text: '#000000',
  },
};

const App = () => {
  return (
    <StoreProvider store={store}>
      <PaperProvider theme={theme}>
        <AppNavigation />
      </PaperProvider>
    </StoreProvider>
  );
};

export default App;
