import React, {useState, useCallback, useEffect} from 'react';
import {View, TextInput, TouchableOpacity, StyleSheet} from 'react-native';

const MainView = ({model, controller}) => {
  const [noteText, setNoteText] = useState('');
  const [searchText, setSearchText] = useState('');

  const {
    data: {initialText},
  } = model;

  const {callback1, callback2} = controller;

  const onSearchTextChange = useCallback((text) => {
    setSearchText(text);
  }, []);

  const onNoteTextChange = useCallback((text) => {
    setNoteText(text);
  }, []);

  useEffect(() => {
    setNoteText(initialText);
  }, [initialText]);

  return (
    <View style={styles.mainContainer}>
      <View style={styles.searchTextArea}>
        <TextInput
          style={[styles.searchText, ,]}
          defaultValue={searchText}
          placeholder={'Search placeholder'}
          multiline={true}
          onChangeText={onSearchTextChange}
        />
      </View>
      <View style={styles.textArea}>
        <TextInput
          style={[styles.noteText, ,]}
          defaultValue={noteText}
          placeholder={'Placeholder'}
          multiline={true}
          onChangeText={onNoteTextChange}
        />
      </View>
      <View style={styles.buttonsContainer}>
        <TouchableOpacity
          style={styles.testButtonContainer}
          onPress={callback1}>
          <View style={styles.testButtonContainer} />
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.test2ButtonContainer}
          onPress={callback2}>
          <View style={styles.test2ButtonContainer} />
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
  },
  searchTextArea: {
    height: 50,
  },
  searchText: {
    fontSize: 20,
  },
  textArea: {
    flex: 1,
  },
  noteText: {
    fontSize: 20,
  },
  buttonsContainer: {
    flexDirection: 'row',
    position: 'absolute',
    bottom: 0,
    left: 0,
    right: 0,
    height: 50,
  },
  testButtonContainer: {
    alignSelf: 'stretch',
    width: 50,
    backgroundColor: 'grey',
    margin: 5,
  },
  test2ButtonContainer: {
    alignSelf: 'stretch',
    width: 50,
    backgroundColor: 'lightgrey',
    margin: 5,
  },
});

export default React.memo(MainView);
