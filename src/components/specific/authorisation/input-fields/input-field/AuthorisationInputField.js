import React, {useRef, useCallback, useEffect} from 'react';
import {View, TextInput, StyleSheet} from 'react-native';

const AuthorisationInputField = ({
  icon,
  value,
  placeholder,
  forceFocus,
  onChangeText,
  onSubmitEditing,
}) => {
  const inputFieldRef = useRef(null);

  const changeTextHandler = useCallback(
    (updatedText) => {
      if (onChangeText) {
        onChangeText(updatedText);
      }
    },
    [onChangeText],
  );

  const submitEditingHandler = useCallback(() => {
    if (onSubmitEditing) {
      onSubmitEditing();
    }
  }, [onSubmitEditing]);

  useEffect(() => {
    if (forceFocus) {
      inputFieldRef.current.focus();
    }
  }, [forceFocus]);

  return (
    <View style={styles.mainContainer}>
      <View style={styles.iconContainer}>
        <View style={styles.iconInnerContainer}>{icon}</View>
      </View>
      <View style={styles.inputContainer}>
        <TextInput
          ref={inputFieldRef}
          style={styles.input}
          placeholder={placeholder}
          defaultValue={value}
          underlineColorAndroid={'transparent'}
          spellCheck={false}
          autoCorrect={false}
          onChangeText={changeTextHandler}
          onSubmitEditing={submitEditingHandler}
        />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    height: 40,
    width: 250,
    flexDirection: 'row',
    backgroundColor: '#F5F5F5',
    // borderWidth: 1,
  },
  iconContainer: {
    width: 50,
    alignSelf: 'stretch',
    // backgroundColor: 'grey',
    justifyContent: 'center',
    alignItems: 'center',
  },
  iconInnerContainer: {
    width: 30,
    height: 30,
    justifyContent: 'center',
    alignItems: 'center',
    /*backgroundColor: 'mediumseagreen' backgroundColor: 'coral'*/
  },
  inputContainer: {
    flex: 1,
    alignSelf: 'stretch',
    // backgroundColor: 'grey',
    justifyContent: 'center',
  },
  input: {
    fontSize: 16,
    color: '#000000',
    borderBottomColor: 'transparent',
  },
});

export default React.memo(AuthorisationInputField);
