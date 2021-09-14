import React, {useCallback, useState} from 'react';
import {View, TextInput, StyleSheet} from 'react-native';
import {SystemEventsHandler} from '../../../../../utils/common/system-events-handler/SystemEventsHandler';

const AuthorisationInputField = ({
  icon,
  value,
  placeholder,
  onChangeText,
  onSubmitEditing,
}) => {
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

  return (
    <View style={styles.mainContainer}>
      <View style={styles.iconContainer}>
        <View
          style={{width: 20, height: 20, backgroundColor: 'mediumseagreen'}}
        />
      </View>
      <View style={styles.inputContainer}>
        <TextInput
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
    height: 50,
    width: 300,
    flexDirection: 'row',
    backgroundColor: 'white',
    // borderWidth: 1,
  },
  iconContainer: {
    width: 50,
    alignSelf: 'stretch',
    // backgroundColor: 'grey',
    justifyContent: 'center',
    alignItems: 'center',
  },
  inputContainer: {
    flex: 1,
    alignSelf: 'stretch',
    // backgroundColor: 'grey',
  },
  input: {
    fontSize: 18,
    color: '#000000',
    borderBottomColor: 'transparent',
  },
});

export default React.memo(AuthorisationInputField);
