import React, {useCallback, useState, useMemo} from 'react';
import {View} from 'react-native';
import {SafeAreaProvider} from 'react-native-safe-area-context';
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import Main from '../../../screens/main/Main';
import Authorisation from '../../../screens/authorisation/Authorisation';
import Group from '../../../screens/group/Group';
import AppRoutes from '../../../data/common/routes/AppRoutes';
import Loader from '../../../screens/loader/Loader';

const MainStack = createNativeStackNavigator();

const AppNavigation = () => {
  const mainStack = useMemo(() => {
    return (
      <MainStack.Navigator mode="card">
        <MainStack.Screen
          name={AppRoutes.Loader}
          component={Loader}
          options={({navigation, route}) => ({
            headerShown: false,
            headerLeft: (props) => {
              return null;
            },
            title: 'Loader',
            headerStatusBarHeight: 0,
          })}
        />
        <MainStack.Screen
          name={AppRoutes.Authorisation}
          component={Authorisation}
          options={{
            title: 'Authorisation',
            headerStatusBarHeight: 0,
            headerShown: false,
          }}
        />
        <MainStack.Screen
          name={AppRoutes.Group}
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
          name={AppRoutes.Main}
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
