import React, {useCallback, useState, useMemo} from 'react';
import {View} from 'react-native';
import {SafeAreaProvider} from 'react-native-safe-area-context';
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import Main from '../../../screens/main/Main';
import Authorisation from '../../../screens/authorisation/Authorisation';
import Group from '../../../screens/group/Group';

const MainStack = createNativeStackNavigator();

const AppNavigation = () => {
  const mainStack = useMemo(() => {
    return (
      <MainStack.Navigator mode="card">
        <MainStack.Screen
          name={'Authorisation'}
          component={Authorisation}
          options={{
            title: 'Authorisation',
            headerStatusBarHeight: 0,
            headerShown: false,
          }}
        />
        <MainStack.Screen
          name={'Group'}
          component={Group}
          options={({navigation, route}) => ({
            headerShown: true,
            headerLeft: (props) => {
              return <View />;
            },
            title: 'Group',
            headerStatusBarHeight: 0,
          })}
          // options={{
          //   title: 'Group',
          //   headerStatusBarHeight: 0,
          //   headerShown: true,
          //   headerLeft: () => null,
          // }}
        />
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
