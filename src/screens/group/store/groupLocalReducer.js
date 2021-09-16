import GroupLocalActions from './GroupLocalActions';

const groupLocalReducer = (state, action) => {
  switch (action.type) {
    case GroupLocalActions.types.SET_DEVICE_REQUESTS_DIALOG_VISIBILITY: {
      return {
        ...state,
        deviceRequestsDialog: {
          ...state.deviceRequestsDialog,
          visible: action.payload.visible,
        },
      };
    }

    case GroupLocalActions.types.SET_DEVICE_REQUESTS_DIALOG_DATA: {
      const {selectedDeviceName} = action.payload;

      return {
        ...state,
        deviceRequestsDialog: {
          ...state.deviceRequestsDialog,
          selectedDeviceName,
        },
      };
    }

    default: {
      return state;
    }
  }
};

export default groupLocalReducer;
