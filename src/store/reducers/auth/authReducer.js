import AppActions from '../../actions/AppActions';

const initialState = {
  authInfo: {
    groupLogin: '',
    groupPassword: '',
    deviceName: '',
    loggedIn: false,
  },
  registerDeviceInGroup: {
    inProgress: false,
    error: {
      hasError: false,
      code: '',
      message: '',
    },
  },
  isDeviceGroupExist: {
    requestId: -1,
    inProgress: false,
    exist: false,
    error: {
      hasError: false,
      code: '',
      message: '',
    },
  },
  isDeviceNameAvailable: {
    requestId: -1,
    inProgress: false,
    available: false,
    error: {
      hasError: false,
      code: '',
      message: '',
    },
  },
  createDeviceGroup: {
    inProgress: false,
    error: {
      hasError: false,
      code: '',
      message: '',
    },
  },
  loginDeviceInGroup: {
    inProgress: false,
    error: {
      hasError: false,
      code: '',
      message: '',
    },
  },
};

const authReducer = (state = initialState, action) => {
  switch (action.types) {
    default: {
      return state;
    }
  }
};

export default authReducer;

// import AppActions from '../../actions/AppActions';
//
// const initialState = {
//   groupLogin: '',
//   groupPassword: '',
//   deviceName: '',
//   loggedIn: false,
//   deviceRegistrationRunning: false,
//   deviceLoginRunning: false,
//   errors: {
//     register: {
//       code: '',
//       message: '',
//     },
//     login: {
//       code: '',
//       message: '',
//     },
//   },
// };
//
// const authReducer = (state = initialState, action) => {
//   switch (action.types) {
//     case AppActions.auth.types.REGISTER_DEVICE_BEGIN: {
//       return {
//         ...state,
//         groupLogin: '',
//         groupPassword: '',
//         deviceName: '',
//         loggedIn: false,
//         deviceRegistrationRunning: true,
//         deviceLoginRunning: false,
//         errors: {
//           ...state.errors,
//           register: {
//             ...state.errors.register,
//             code: '',
//             message: '',
//           },
//           login: {
//             ...state.errors.login,
//             code: '',
//             message: '',
//           },
//         },
//       };
//     }
//
//     case AppActions.auth.types.REGISTER_DEVICE_FINISHED: {
//       const {groupLogin, groupPassword, deviceName} = action.payload;
//
//       return {
//         ...state,
//         groupLogin,
//         groupPassword,
//         deviceName,
//         loggedIn: true,
//         deviceRegistrationRunning: false,
//         deviceLoginRunning: false,
//         errors: {
//           ...state.errors,
//           register: {
//             ...state.errors.register,
//             code: '',
//             message: '',
//           },
//           login: {
//             ...state.errors.login,
//             code: '',
//             message: '',
//           },
//         },
//       };
//     }
//
//     case AppActions.auth.types.REGISTER_DEVICE_ERROR: {
//       const {code, message} = action.payload;
//
//       return {
//         ...state,
//         groupLogin: '',
//         groupPassword: '',
//         deviceName: '',
//         loggedIn: false,
//         deviceRegistrationRunning: false,
//         deviceLoginRunning: false,
//         errors: {
//           ...state.errors,
//           register: {
//             ...state.errors.register,
//             code,
//             message,
//           },
//           login: {
//             ...state.errors.login,
//             code: '',
//             message: '',
//           },
//         },
//       };
//     }
//
//     case AppActions.auth.types.LOGIN_DEVICE_BEGIN: {
//       return {
//         ...state,
//         groupLogin: '',
//         groupPassword: '',
//         deviceName: '',
//         loggedIn: false,
//         deviceRegistrationRunning: false,
//         deviceLoginRunning: true,
//         errors: {
//           ...state.errors,
//           register: {
//             ...state.errors.register,
//             code: '',
//             message: '',
//           },
//           login: {
//             ...state.errors.login,
//             code: '',
//             message: '',
//           },
//         },
//       };
//     }
//
//     case AppActions.auth.types.LOGIN_DEVICE_FINISHED: {
//       const {groupLogin, groupPassword, deviceName} = action.payload;
//
//       return {
//         ...state,
//         groupLogin,
//         groupPassword,
//         deviceName,
//         loggedIn: true,
//         deviceRegistrationRunning: false,
//         deviceLoginRunning: false,
//         errors: {
//           ...state.errors,
//           register: {
//             ...state.errors.register,
//             code: '',
//             message: '',
//           },
//           login: {
//             ...state.errors.login,
//             code: '',
//             message: '',
//           },
//         },
//       };
//     }
//
//     case AppActions.auth.types.LOGIN_DEVICE_ERROR: {
//       const {code, message} = action.payload;
//
//       return {
//         ...state,
//         groupLogin: '',
//         groupPassword: '',
//         deviceName: '',
//         loggedIn: false,
//         deviceRegistrationRunning: false,
//         deviceLoginRunning: false,
//         errors: {
//           ...state.errors,
//           register: {
//             ...state.errors.register,
//             code: '',
//             message: '',
//           },
//           login: {
//             ...state.errors.login,
//             code,
//             message,
//           },
//         },
//       };
//     }
//
//     default: {
//       return state;
//     }
//   }
// };
//
// export default authReducer;
