import React, {useMemo} from 'react';
import {View} from 'react-native';
import {SafeAreaProvider} from 'react-native-safe-area-context';
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import Main from '../../../screens/main/Main';
import Authorisation from '../../../screens/authorisation/Authorisation';
import Group from '../../../screens/group/Group';
import AppRoutes from '../../../data/common/routes/AppRoutes';
import Loader from '../../../screens/loader/Loader';
import Service from '../../../screens/service/Service';
import Settings from '../../../screens/settings/Settings';
import CameraTest from '../../../screens/camera-test/CameraTest';

const MainStack = createNativeStackNavigator();

const AppNavigation = () => {
  /*
            <MainStack.Screen
          name={'CameraTest'}
          component={CameraTest}
          options={({navigation, route}) => ({
            headerShown: false,
            headerLeft: (props) => {
              return null;
            },
            title: 'Camera Test',
            headerStatusBarHeight: 0,
          })}
        />
     */

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
          name={AppRoutes.Service}
          component={Service}
          options={({navigation, route}) => ({
            headerShown: false,
            headerLeft: (props) => {
              return null;
            },
            title: 'Service',
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
          name={AppRoutes.Settings}
          component={Settings}
          options={({navigation, route}) => ({
            headerShown: true,
            // headerLeft: (props) => {
            //   return <View />;
            // },
            title: 'Settings',
            headerStatusBarHeight: 0,
          })}
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
