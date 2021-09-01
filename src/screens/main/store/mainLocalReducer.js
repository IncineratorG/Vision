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

    default: {
      return state;
    }
  }
};

export default mainLocalReducer;
