import AppActions from '../../actions/AppActions';

const initialState = {
  authInfo: {
    groupName: '',
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
  createGroupWithDevice: {
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
    case AppActions.auth.types.REGISTER_DEVICE_IN_GROUP_BEGIN: {
      return {
        ...state,
        registerDeviceInGroup: {
          ...state.registerDeviceInGroup,
          inProgress: true,
          error: {
            ...state.registerDeviceInGroup.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.auth.types.REGISTER_DEVICE_IN_GROUP_FINISHED: {
      const {groupName, groupPassword, deviceName} = action.payload;

      return {
        ...state,
        authInfo: {
          ...state.authInfo,
          groupName,
          groupPassword,
          deviceName,
          loggedIn: true,
        },
        registerDeviceInGroup: {
          ...state.registerDeviceInGroup,
          inProgress: false,
          error: {
            ...state.registerDeviceInGroup.error,
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.auth.types.REGISTER_DEVICE_IN_GROUP_ERROR: {
      const {code, message} = action.payload;

      return {
        ...state,
        registerDeviceInGroup: {
          ...state.registerDeviceInGroup,
          inProgress: false,
          error: {
            ...state.registerDeviceInGroup.error,
            hasError: true,
            code,
            message,
          },
        },
      };
    }

    case AppActions.auth.types.CREATE_GROUP_WITH_DEVICE_BEGIN: {
      return {
        ...state,
        createGroupWithDevice: {
          ...state.createGroupWithDevice,
          inProgress: true,
          error: {
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.auth.types.CREATE_GROUP_WITH_DEVICE_FINISHED: {
      const {groupName, groupPassword, deviceName} = action.payload;

      return {
        ...state,
        authInfo: {
          ...state.authInfo,
          groupName,
          groupPassword,
          deviceName,
          loggedIn: true,
        },
        createGroupWithDevice: {
          ...state.createGroupWithDevice,
          inProgress: false,
          error: {
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.auth.types.CREATE_GROUP_WITH_DEVICE_ERROR: {
      const {code, message} = action.payload;

      return {
        ...state,
        createGroupWithDevice: {
          ...state.createGroupWithDevice,
          inProgress: false,
          error: {
            hasError: true,
            code,
            message,
          },
        },
      };
    }

    case AppActions.auth.types.LOGIN_DEVICE_BEGIN: {
      return {
        ...state,
        loginDeviceInGroup: {
          ...state.loginDeviceInGroup,
          inProgress: true,
          error: {
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.auth.types.LOGIN_DEVICE_FINISHED: {
      const {groupName, groupPassword, deviceName} = action.payload;

      return {
        ...state,
        authInfo: {
          ...state.authInfo,
          groupName,
          groupPassword,
          deviceName,
          loggedIn: true,
        },
        loginDeviceInGroup: {
          ...state.loginDeviceInGroup,
          inProgress: false,
          error: {
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.auth.types.LOGIN_DEVICE_ERROR: {
      const {code, message} = action.payload;

      return {
        ...state,
        loginDeviceInGroup: {
          ...state.loginDeviceInGroup,
          inProgress: false,
          error: {
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
