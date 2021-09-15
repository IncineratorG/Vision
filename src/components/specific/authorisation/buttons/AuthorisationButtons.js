import React, {useCallback, useMemo, useState} from 'react';
import {View, Text, TouchableOpacity, StyleSheet} from 'react-native';
import AuthorisationButton from './button/AuthorisationButton';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import useTranslation from '../../../../utils/common/localization';

const AuthorisationButtons = () => {
  const {t} = useTranslation();

  const modes = useMemo(() => {
    return {
      login: 'login',
      register: 'register',
    };
  }, []);

  const [currentMode, setCurrentMode] = useState(modes.login);

  const loginButtonPressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({info: 'loginButtonPressHandler()'});
  }, []);
  const loginTextPressHandler = useCallback(() => {
    setCurrentMode(modes.login);
  }, [modes.login]);

  const registerButtonPressHandler = useCallback(() => {
    SystemEventsHandler.onInfo({info: 'registerButtonPressHandler()'});
  }, []);
  const registerTextPressHandler = useCallback(() => {
    setCurrentMode(modes.register);
  }, [modes.register]);

  const loginButton = (
    <AuthorisationButton
      text={t('AuthorisationButtons_loginButtonText')}
      onPress={loginButtonPressHandler}
    />
  );
  const loginText = (
    <TouchableOpacity activeOpacity={0.99} onPress={loginTextPressHandler}>
      <View style={styles.loginTextContainer}>
        <Text style={styles.loginText}>
          {t('AuthorisationButtons_loginText')}
        </Text>
      </View>
    </TouchableOpacity>
  );

  const registerButton = (
    <AuthorisationButton
      text={t('AuthorisationButtons_registerButtonText')}
      onPress={registerButtonPressHandler}
    />
  );
  const registerText = (
    <TouchableOpacity activeOpacity={0.99} onPress={registerTextPressHandler}>
      <View style={styles.registerTextContainer}>
        <Text style={styles.registerText}>
          {t('AuthorisationButtons_registerText')}
        </Text>
      </View>
    </TouchableOpacity>
  );

  const loginButtonComponent =
    currentMode === modes.login ? loginButton : loginText;
  const registerButtonComponent =
    currentMode === modes.register ? registerButton : registerText;

  return (
    <View style={styles.mainContainer}>
      <View style={styles.buttonsContainer}>
        {loginButtonComponent}
        {registerButtonComponent}
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    // backgroundColor: 'deepskyblue',
    backgroundColor: 'white',
  },
  buttonsContainer: {
    flex: 1,
    alignSelf: 'stretch',
    margin: 10,
    backgroundColor: 'white',
    alignItems: 'center',
    // justifyContent: 'space-evenly',
    justifyContent: 'center',
  },
  loginTextContainer: {
    width: 300,
    height: 50,
    // backgroundColor: 'red',
    justifyContent: 'center',
    alignItems: 'center',
  },
  loginText: {
    fontSize: 14,
    color: 'grey',
  },
  registerTextContainer: {
    width: 300,
    height: 50,
    // backgroundColor: 'red',
    justifyContent: 'center',
    alignItems: 'center',
  },
  registerText: {
    fontSize: 14,
    color: 'grey',
  },
});

export default React.memo(AuthorisationButtons);
