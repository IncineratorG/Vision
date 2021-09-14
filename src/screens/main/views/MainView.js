import React from 'react';
import {View, StyleSheet} from 'react-native';
import SimpleButton from '../../../components/common/simple-button/SimpleButton';
import RegisterDeviceInGroupDialog from '../../../components/specific/main/register-device-in-group-dialog/RegisterDeviceInGroupDialog';
import RegisteringDeviceInGroupDialog from '../../../components/specific/main/registering-device-in-group-dialog/RegisteringDeviceInGroupDialog';
import NeedCreateGroupDialog from '../../../components/specific/main/need-create-group-dialog/NeedCreateGroupDialog';
import CreatingGroupWithDeviceDialog from '../../../components/specific/main/creating-group-with-device-dialog/CreatingGroupWithDeviceDialog';
import LoginDeviceInGroupDialog from '../../../components/specific/main/login-device-in-group-dialog/LoginDeviceInGroupDialog';
import LoggingDeviceInGroupDialog from '../../../components/specific/main/logging-device-in-group-dialog/LoggingDeviceInGroupDialog';
import UserInfoBar from '../../../components/specific/main/user-info-bar/UserInfoBar';
import GroupDevicesList from '../../../components/specific/main/group-devices-list/GroupDevicesList';

const MainView = ({model, controller}) => {
  const {
    data: {
      currentGroupName,
      currentGroupPassword,
      currentDeviceName,
      loggedIn,
      registerDeviceInGroupInProgress,
      createGroupWithDeviceInProgress,
      loginDeviceInGroupInProgress,
      localState: {
        registerDeviceInGroupDialog: {
          visible: registerDeviceInGroupDialogVisible,
        },
        needCreateGroupDialog: {visible: needCreateGroupDialogVisible},
        loginDeviceInGroupDialog: {visible: loginDeviceInGroupDialogVisible},
      },
    },
  } = model;

  const {
    startServicePressHandler,
    stopServicePressHandler,
    isServiceRunningPressHandler,
    testRequestPressHandler,
    openRegisterDeviceInGroupDialog,
    registerDeviceInGroupDialogCancelHandler,
    registerDeviceInGroupRegisterPressHandler,
    registeringDeviceInGroupDialogCancelHandler,
    needCreateGroupDialogCreatePressHandler,
    needCreateGroupDialogCancelPressHandler,
    creatingGroupWithDeviceDialogCancelPressHandler,
    openLoginDeviceInGroupDialog,
    loginDeviceInGroupDialogCancelHandler,
    loginDeviceInGroupDialogLoginPressHandler,
    loggingDeviceInGroupDialogCancelHandler,
  } = controller;

  const registerDeviceInGroupDialog = (
    <RegisterDeviceInGroupDialog
      visible={registerDeviceInGroupDialogVisible}
      onRegisterPress={registerDeviceInGroupRegisterPressHandler}
      onCancelPress={registerDeviceInGroupDialogCancelHandler}
    />
  );

  const registeringDeviceInGroupDialog = (
    <RegisteringDeviceInGroupDialog
      visible={registerDeviceInGroupInProgress}
      onCancelPress={registeringDeviceInGroupDialogCancelHandler}
    />
  );

  const needCreateGroupDialog = (
    <NeedCreateGroupDialog
      visible={needCreateGroupDialogVisible}
      onCreatePress={needCreateGroupDialogCreatePressHandler}
      onCancelPress={needCreateGroupDialogCancelPressHandler}
    />
  );

  const creatingGroupWithDeviceDialog = (
    <CreatingGroupWithDeviceDialog
      visible={createGroupWithDeviceInProgress}
      onCancelPress={creatingGroupWithDeviceDialogCancelPressHandler}
    />
  );

  const loginDeviceInGroupDialog = (
    <LoginDeviceInGroupDialog
      visible={loginDeviceInGroupDialogVisible}
      onLoginPress={loginDeviceInGroupDialogLoginPressHandler}
      onCancelPress={loginDeviceInGroupDialogCancelHandler}
    />
  );

  const loggingDeviceInGroupDialog = (
    <LoggingDeviceInGroupDialog
      visible={loginDeviceInGroupInProgress}
      onCancelPress={loggingDeviceInGroupDialogCancelHandler}
    />
  );

  const currentUserInfoBarComponent = (
    <UserInfoBar
      groupName={currentGroupName}
      groupPassword={currentGroupPassword}
      deviceName={currentDeviceName}
    />
  );

  const groupDevicesListComponent = <GroupDevicesList />;

  return (
    <View style={styles.mainContainer}>
      <View style={styles.indicatorContainer}>
        <View style={styles.indicatorItemContainer} />
      </View>
      {currentUserInfoBarComponent}
      <View style={styles.buttonContainer}>
        <SimpleButton
          title={'Register device in group'}
          onPress={openRegisterDeviceInGroupDialog}
        />
      </View>
      <View style={styles.buttonContainer}>
        <SimpleButton
          title={'Login device in group'}
          onPress={openLoginDeviceInGroupDialog}
        />
      </View>
      <View style={styles.buttonContainer}>
        <SimpleButton
          title={'Start service'}
          onPress={startServicePressHandler}
        />
      </View>
      <View style={styles.buttonContainer}>
        <SimpleButton
          title={'Stop service'}
          onPress={stopServicePressHandler}
        />
      </View>
      <View style={styles.buttonContainer}>
        <SimpleButton
          title={'Is service running'}
          onPress={isServiceRunningPressHandler}
        />
      </View>
      <View style={styles.buttonContainer}>
        <SimpleButton
          title={'Test request'}
          onPress={testRequestPressHandler}
        />
      </View>
      <View style={styles.groupDevicesListContainer}>
        {groupDevicesListComponent}
      </View>
      {registerDeviceInGroupDialog}
      {registeringDeviceInGroupDialog}
      {needCreateGroupDialog}
      {creatingGroupWithDeviceDialog}
      {loginDeviceInGroupDialog}
      {loggingDeviceInGroupDialog}
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
  },
  indicatorContainer: {
    flexDirection: 'row',
    height: 50,
    backgroundColor: 'yellow',
    alignItems: 'center',
  },
  indicatorItemContainer: {
    width: 30,
    height: 30,
    borderWidth: 1,
    borderColor: 'grey',
    marginLeft: 8,
  },
  buttonContainer: {
    height: 50,
    padding: 8,
    backgroundColor: 'green',
    justifyContent: 'center',
    borderWidth: 1,
    borderColor: 'grey',
  },
  freeSpace: {
    height: 10,
    backgroundColor: 'red',
  },
  groupDevicesListContainer: {
    flex: 1,
    alignSelf: 'stretch',
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
