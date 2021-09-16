import AppActions from '../../actions/AppActions';

const initialState = {
  deviceInfo: {
    deviceName: '',
    lastLoginTimestamp: -1,
  },
  devicesInGroup: {
    inProgress: false,
    devices: [],
    error: {
      hasError: false,
      code: '',
      message: '',
    },
  },
};

const surveillanceReducer = (state = initialState, action) => {
  switch (action.type) {
    case AppActions.surveillance.types.GET_DEVICES_IN_GROUP_BEGIN: {
      return {
        ...state,
        devicesInGroup: {
          ...state.devicesInGroup,
          inProgress: true,
          error: {
            ...state.devicesInGroup.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillance.types.GET_DEVICES_IN_GROUP_FINISHED: {
      const {devicesArray} = action.payload;

      return {
        ...state,
        devicesInGroup: {
          ...state.devicesInGroup,
          inProgress: false,
          devices: devicesArray ? [...devicesArray] : [],
          error: {
            ...state.devicesInGroup.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillance.types.GET_DEVICES_IN_GROUP_ERROR: {
      const {code, message} = action.payload;

      return {
        ...state,
        devicesInGroup: {
          ...state.devicesInGroup,
          inProgress: false,
          devices: [],
          error: {
            ...state.devicesInGroup.error,
            hasError: true,
            code,
            message,
          },
        },
      };
    }

    default: {
      return state;
    }
  }
};

export default surveillanceReducer;
