import {call, put} from '@redux-saga/core/effects';
import AppActions from '../../../actions/AppActions';
import Services from '../../../../services/Services';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';

const AS_createGroupWithDeviceHandler = ({channel}) => {
  const handler = function* (action) {
    yield put(AppActions.auth.actions.createGroupWithDeviceBegin());

    const {groupName, groupPassword, deviceName} = action.payload;

    try {
      const authService = Services.services().authService;

      yield call(authService.createGroupWithDevice, {
        groupName,
        groupPassword,
        deviceName,
      });

      yield put(
        AppActions.auth.actions.createGroupWithDeviceFinished({
          groupName,
          groupPassword,
          deviceName,
        }),
      );
    } catch (e) {
      SystemEventsHandler.onError({
        err: 'AS_createGroupWithDeviceHandler()->ERROR: ' + e.toString(),
      });

      const {code, message} = e;

      yield put(
        AppActions.auth.actions.createGroupWithDeviceError({code, message}),
      );
    }
  };

  return {
    handler,
  };
};

export default AS_createGroupWithDeviceHandler;
