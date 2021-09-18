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
  testRequestWithPayloadPayload: {
    requestId: null,
    inProgress: false,
    completed: false,
    cancellable: true,
    cancelInProgress: false,
    cancelled: false,
    response: {
      payload: {
        resultOne: '',
      },
    },
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

    case AppActions.surveillance.types.SEND_TEST_REQUEST_WITH_PAYLOAD_BEGIN: {
      return {
        ...state,
        testRequestWithPayloadPayload: {
          ...state.testRequestWithPayloadPayload,
          requestId: null,
          inProgress: true,
          completed: false,
          cancelInProgress: false,
          cancelled: false,
          response: {
            payload: {
              resultOne: '',
            },
          },
          error: {
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillance.types.SEND_TEST_REQUEST_WITH_PAYLOAD_SENDED: {
      const {requestId} = action.payload;

      return {
        ...state,
        testRequestWithPayloadPayload: {
          ...state.testRequestWithPayloadPayload,
          requestId,
          inProgress: true,
          completed: false,
          cancelInProgress: false,
          cancelled: false,
          response: {
            payload: {
              resultOne: '',
            },
          },
          error: {
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillance.types
      .SEND_TEST_REQUEST_WITH_PAYLOAD_COMPLETED: {
      const {requestId, resultOne} = action.payload;

      return {
        ...state,
        testRequestWithPayloadPayload: {
          ...state.testRequestWithPayloadPayload,
          requestId,
          inProgress: false,
          completed: true,
          cancelInProgress: false,
          cancelled: false,
          response: {
            payload: {
              resultOne,
            },
          },
          error: {
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillance.types.SEND_TEST_REQUEST_WITH_PAYLOAD_ERROR: {
      const {code, message} = action.payload;

      return {
        ...state,
        testRequestWithPayloadPayload: {
          ...state.testRequestWithPayloadPayload,
          inProgress: false,
          completed: false,
          cancelInProgress: false,
          cancelled: false,
          response: {
            payload: {
              resultOne: '',
            },
          },
          error: {
            hasError: true,
            code,
            message,
          },
        },
      };
    }

    case AppActions.surveillance.types.CANCEL_TEST_REQUEST_WITH_PAYLOAD: {
      return {
        ...state,
        testRequestWithPayloadPayload: {
          ...state.testRequestWithPayloadPayload,
          inProgress: true,
          completed: false,
          cancelInProgress: true,
          cancelled: false,
          response: {
            payload: {
              resultOne: '',
            },
          },
          error: {
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillance.types.TEST_REQUEST_WITH_PAYLOAD_CANCELLED: {
      return {
        ...state,
        testRequestWithPayloadPayload: {
          ...state.testRequestWithPayloadPayload,
          inProgress: false,
          completed: false,
          cancelInProgress: false,
          cancelled: true,
          response: {
            payload: {
              resultOne: '',
            },
          },
          error: {
            hasError: false,
            code: '',
            message: '',
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
