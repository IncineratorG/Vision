import {call, put, select} from '@redux-saga/core/effects';
import AppActions from '../../../actions/AppActions';
import Services from '../../../../services/Services';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';

const AS_registerDeviceInGroupHandler = ({channel}) => {
  const handler = function* (action) {
    yield put(AppActions.auth.actions.registerDeviceInGroupBegin());

    const {groupName, groupPassword, deviceName} = action.payload;

    try {
      const authService = Services.services().authService;

      yield call(authService.registerDeviceInGroup, {
        groupName,
        groupPassword,
        deviceName,
      });

      yield put(
        AppActions.auth.actions.registerDeviceInGroupFinished({
          groupName,
          groupPassword,
          deviceName,
        }),
      );
    } catch (e) {
      SystemEventsHandler.onError({
        err: 'AS_registerDeviceInGroupHandler()->ERROR: ' + e.toString(),
      });

      const {code, message} = e;

      yield put(
        AppActions.auth.actions.registerDeviceInGroupError({code, message}),
      );
    }
  };

  return {
    handler,
  };
};

export default AS_registerDeviceInGroupHandler;
