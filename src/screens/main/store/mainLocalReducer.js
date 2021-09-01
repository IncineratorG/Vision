import MainLocalActions from './MainLocalActions';

const mainLocalReducer = (state, action) => {
  switch (action.type) {
    case MainLocalActions.types.SET_REGISTER_USER_GROUP_VISIBILITY_DIALOG: {
      return {
        ...state,
        registerUserGroupDialog: {
          ...state.registerUserGroupDialog,
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
