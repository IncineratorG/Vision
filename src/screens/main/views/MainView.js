import React, {useState, useCallback, useEffect, useRef} from 'react';
import {
  View,
  TextInput,
  TouchableOpacity,
  Button,
  StyleSheet,
} from 'react-native';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';

const MainView = ({model, controller}) => {
  const {callback1} = controller;

  return (
    <View style={styles.mainContainer}>
      <View style={styles.buttonContainer}>
        <Button title={'Button 1'} onPress={callback1} />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
  },
  buttonContainer: {
    height: 50,
    padding: 8,
    backgroundColor: 'green',
    // alignItems: 'center',
    justifyContent: 'center',
  },
  freeSpace: {
    height: 10,
    backgroundColor: 'red',
  },
});

export default React.memo(MainView);

// import React, {useState, useCallback, useEffect, useRef} from 'react';
// import {View, TextInput, TouchableOpacity, StyleSheet} from 'react-native';
// import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';

// const MainView = ({model, controller}) => {
//   const noteTextInputRef = useRef(null);

//   const [noteText, setNoteText] = useState('');
//   const [searchText, setSearchText] = useState('');
//   // const [selection, setSelection] = useState({start: 0, end: 0});
//   const [selection, setSelection] = useState(undefined);
//   const [lastSelectionIndex, setLastSelectionIndex] = useState(-1);

//   const {
//     data: {initialText},
//   } = model;

//   const {callback1, callback2} = controller;

//   const onSearchTextChange = useCallback((text) => {
//     setSearchText(text);

//     // setSelection({start: 1, end: 2});
//     // noteTextInputRef.current.focus();
//   }, []);

//   const onNoteTextChange = useCallback((text) => {
//     setNoteText(text);
//   }, []);

//   const onNoteTextSelectionChange = useCallback((data) => {
//     const {
//       nativeEvent: {
//         selection: {start, end},
//       },
//     } = data;

//     SystemEventsHandler.onInfo({
//       info: 'onNoteTextSelectionChange: ' + start + ' - ' + end,
//     });
//   }, []);

//   const buttonPressHandler = useCallback(() => {
//     SystemEventsHandler.onInfo({info: 'buttonPressHandler(): ' + searchText});

//     let searchTextStartPosition = -1;
//     if (lastSelectionIndex) {
//       searchTextStartPosition = initialText
//         .toLowerCase()
//         .indexOf(searchText.toLowerCase(), lastSelectionIndex);
//     } else {
//       searchTextStartPosition = initialText
//         .toLowerCase()
//         .indexOf(searchText.toLowerCase());
//     }

//     SystemEventsHandler.onInfo({
//       info: 'buttonPressHandler()->POSITION: ' + searchTextStartPosition,
//     });

//     if (searchTextStartPosition >= 0) {
//       const selectionObject = {
//         start: searchTextStartPosition,
//         end: searchTextStartPosition + searchText.length,
//       };

//       setLastSelectionIndex(searchTextStartPosition + searchText.length);
//       setSelection(selectionObject);

//       SystemEventsHandler.onInfo({
//         info:
//           'buttonPressHandler()->WILL_FOCUS: ' +
//           JSON.stringify(selectionObject),
//       });

//       noteTextInputRef.current.focus();
//     } else {
//       SystemEventsHandler.onInfo({
//         info: 'buttonPressHandler()->WILL_NOT_FOCUS:',
//       });

//       setLastSelectionIndex(-1);
//       setSelection(undefined);
//     }
//   }, [searchText, initialText, lastSelectionIndex]);

//   useEffect(() => {
//     setNoteText(initialText);
//   }, [initialText]);

//   return (
//     <View style={styles.mainContainer}>
//       <View style={styles.searchTextArea}>
//         <TextInput
//           style={[styles.searchText, ,]}
//           defaultValue={searchText}
//           placeholder={'Search placeholder'}
//           multiline={true}
//           onChangeText={onSearchTextChange}
//         />
//       </View>
//       <View style={styles.textArea}>
//         <TextInput
//           ref={noteTextInputRef}
//           style={[styles.noteText]}
//           defaultValue={noteText}
//           placeholder={'Placeholder'}
//           multiline={true}
//           selection={selection}
//           onChangeText={onNoteTextChange}
//           onSelectionChange={onNoteTextSelectionChange}
//         />
//       </View>
//       <View style={styles.buttonsContainer}>
//         <TouchableOpacity
//           style={styles.testButtonContainer}
//           onPress={callback1}>
//           <View style={styles.testButtonContainer} />
//         </TouchableOpacity>
//         <TouchableOpacity
//           style={styles.test2ButtonContainer}
//           onPress={buttonPressHandler}>
//           <View style={styles.test2ButtonContainer} />
//         </TouchableOpacity>
//       </View>
//     </View>
//   );
// };

// const styles = StyleSheet.create({
//   mainContainer: {
//     flex: 1,
//   },
//   searchTextArea: {
//     height: 50,
//   },
//   searchText: {
//     fontSize: 20,
//   },
//   textArea: {
//     flex: 1,
//   },
//   noteText: {
//     fontSize: 20,
//   },
//   buttonsContainer: {
//     flexDirection: 'row',
//     position: 'absolute',
//     bottom: 0,
//     left: 0,
//     right: 0,
//     height: 50,
//   },
//   testButtonContainer: {
//     alignSelf: 'stretch',
//     width: 50,
//     backgroundColor: 'grey',
//     margin: 5,
//   },
//   test2ButtonContainer: {
//     alignSelf: 'stretch',
//     width: 50,
//     backgroundColor: 'lightgrey',
//     margin: 5,
//   },
// });

// export default React.memo(MainView);
