import MainLocalActions from './MainLocalActions';

const mainLocalReducer = (state, action) => {
  switch (action.type) {
    case MainLocalActions.types.SET_REGISTER_USER_GROUP_DIALOG_VISIBILITY: {
      return {
        ...state,
        registerUserGroupDialog: {
          ...state.registerUserGroupDialog,
          visible: action.payload.visible,
        },
      };
    }

    case MainLocalActions.types.SET_LOGIN_USER_GROUP_DIALOG_VISIBILITY: {
      return {
        ...state,
        loginUserGroupDialog: {
          ...state.loginUserGroupDialog,
          visible: action.payload.visible,
        },
      };
    }

    case MainLocalActions.types.SET_REGISTER_DEVICE_IN_GROUP_DIALOG_DATA: {
      const {groupName, groupPassword, deviceName} = action.payload;

      return {
        ...state,
        registerDeviceInGroupDialog: {
          ...state.registerDeviceInGroupDialog,
          groupName,
          groupPassword,
          deviceName,
        },
      };
    }

    case MainLocalActions.types
      .SET_REGISTER_DEVICE_IN_GROUP_DIALOG_VISIBILITY: {
      return {
        ...state,
        registerDeviceInGroupDialog: {
          ...state.registerDeviceInGroupDialog,
          visible: action.payload.visible,
        },
      };
    }

    case MainLocalActions.types.SET_NEED_CREATE_GROUP_DIALOG_DATA: {
      const {groupName, groupPassword, deviceName} = action.payload;

      return {
        ...state,
        needCreateGroupDialog: {
          ...state.needCreateGroupDialog,
          groupName,
          groupPassword,
          deviceName,
        },
      };
    }

    case MainLocalActions.types.SET_NEED_CREATE_GROUP_DIALOG_VISIBILITY: {
      return {
        ...state,
        needCreateGroupDialog: {
          ...state.needCreateGroupDialog,
          visible: action.payload.visible,
        },
      };
    }

    default: {
      return state;
    }
  }
};

export default mainLocalReducer;
