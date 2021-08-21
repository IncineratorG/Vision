import React, {useCallback, useState, useMemo} from 'react';
import {SafeAreaProvider} from 'react-native-safe-area-context';
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import Main from '../../../screens/main/Main';

const MainStack = createNativeStackNavigator();

const AppNavigation = () => {
  const mainStack = useMemo(() => {
    return (
      <MainStack.Navigator mode="card">
        <MainStack.Screen
          name={'Main'}
          component={Main}
          options={{
            title: 'Main',
            headerStatusBarHeight: 0,
          }}
        />
      </MainStack.Navigator>
    );
  }, []);

  return (
    <SafeAreaProvider>
      <NavigationContainer>{mainStack}</NavigationContainer>
    </SafeAreaProvider>
  );
};

export default React.memo(AppNavigation);
