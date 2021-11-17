import SelectedDeviceLocalActions from './SelectedDeviceLocalActions';

const selectedDeviceLocalReducer = (state, action) => {
  switch (action.type) {
    case SelectedDeviceLocalActions.types.SET_DEVICE_DATA: {
      const {device} = action.payload;

      return {
        ...state,
        device: {...device},
      };
    }

    default: {
      return state;
    }
  }
};

export default selectedDeviceLocalReducer;
