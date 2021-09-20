import AppActions from '../../actions/AppActions';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';

const initialState = {
  deviceInfo: {
    lastLoginTimestamp: -1,
    lastUpdateTimestamp: -1,
    deviceName: '',
    deviceMode: '',
    hasFrontCamera: false,
    hasBackCamera: false,
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
  service: {
    running: false,
    startInProgress: false,
    stopInProgress: false,
    checkInProgress: false,
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

    case AppActions.surveillance.types.START_SERVICE_BEGIN: {
      return {
        ...state,
        service: {
          ...state.service,
          // running: false,
          startInProgress: true,
          stopInProgress: false,
          // checkInProgress: false,
          error: {
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillance.types.START_SERVICE_FINISHED: {
      return {
        ...state,
        service: {
          ...state.service,
          running: true,
          startInProgress: false,
          stopInProgress: false,
          // checkInProgress: false,
          error: {
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillance.types.START_SERVICE_ERROR: {
      const {code, message} = action.payload;

      return {
        ...state,
        service: {
          ...state.service,
          // running: false,
          startInProgress: false,
          stopInProgress: false,
          // checkInProgress: false,
          error: {
            hasError: true,
            code,
            message,
          },
        },
      };
    }

    case AppActions.surveillance.types.STOP_SERVICE_BEGIN: {
      return {
        ...state,
        service: {
          ...state.service,
          // running: false,
          startInProgress: false,
          stopInProgress: true,
          // checkInProgress: false,
          error: {
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillance.types.STOP_SERVICE_FINISHED: {
      return {
        ...state,
        service: {
          ...state.service,
          running: false,
          startInProgress: false,
          stopInProgress: false,
          // checkInProgress: false,
          error: {
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillance.types.STOP_SERVICE_ERROR: {
      const {code, message} = action.payload;

      return {
        ...state,
        service: {
          ...state.service,
          // running: false,
          startInProgress: false,
          stopInProgress: false,
          // checkInProgress: false,
          error: {
            hasError: true,
            code,
            message,
          },
        },
      };
    }

    case AppActions.surveillance.types.CHECK_SERVICE_STATUS_BEGIN: {
      return {
        ...state,
        service: {
          ...state.service,
          // running: false,
          // startInProgress: false,
          // stopInProgress: false,
          checkInProgress: true,
          error: {
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillance.types.CHECK_SERVICE_STATUS_FINISHED: {
      const {isRunning} = action.payload;

      return {
        ...state,
        service: {
          ...state.service,
          running: isRunning,
          // startInProgress: false,
          // stopInProgress: false,
          checkInProgress: false,
          error: {
            hasError: false,
            code: '',
            message: '',
          },
        },
      };
    }

    case AppActions.surveillance.types.CHECK_SERVICE_STATUS_ERROR: {
      const {code, message} = action.payload;

      return {
        ...state,
        service: {
          ...state.service,
          // running: false,
          // startInProgress: false,
          // stopInProgress: false,
          checkInProgress: false,
          error: {
            hasError: true,
            code,
            message,
          },
        },
      };
    }

    case AppActions.surveillance.types.SEND_TEST_REQUEST_WITH_PAYLOAD_BEGIN: {
      SystemEventsHandler.onInfo({
        info: 'surveillanceReducer->SEND_TEST_REQUEST_WITH_PAYLOAD_BEGIN',
      });

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

      SystemEventsHandler.onInfo({
        info:
          'surveillanceReducer->SEND_TEST_REQUEST_WITH_PAYLOAD_SENDED: ' +
          JSON.stringify(action),
      });

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
      const {resultOne} = action.payload;

      SystemEventsHandler.onInfo({
        info:
          'surveillanceReducer->SEND_TEST_REQUEST_WITH_PAYLOAD_COMPLETED: ' +
          JSON.stringify(action),
      });

      return {
        ...state,
        testRequestWithPayloadPayload: {
          ...state.testRequestWithPayloadPayload,
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

      SystemEventsHandler.onInfo({
        info:
          'surveillanceReducer->SEND_TEST_REQUEST_WITH_PAYLOAD_ERROR: ' +
          JSON.stringify(action),
      });

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
      SystemEventsHandler.onInfo({
        info:
          'surveillanceReducer->CANCEL_TEST_REQUEST_WITH_PAYLOAD: ' +
          JSON.stringify(action),
      });

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
      SystemEventsHandler.onInfo({
        info:
          'surveillanceReducer->TEST_REQUEST_WITH_PAYLOAD_CANCELLED: ' +
          JSON.stringify(action),
      });

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
