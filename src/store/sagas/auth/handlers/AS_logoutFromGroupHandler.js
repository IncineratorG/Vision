import {call, put, select} from '@redux-saga/core/effects';
import AppActions from '../../../actions/AppActions';
import Services from '../../../../services/Services';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';

const AS_logoutFromGroupHandler = ({channel}) => {
  const handler = function* (action) {
    yield put(AppActions.auth.actions.logoutDeviceBegin());

    const {groupName, groupPassword, deviceName} = yield select(
      (state) => state.auth.authInfo,
    );

    try {
      const authService = Services.services().authService;
      yield call(authService.logoutFromGroup, {
        groupName,
        groupPassword,
        deviceName,
      });

      yield put(AppActions.auth.actions.logoutDeviceFinished());
    } catch (e) {
      SystemEventsHandler.onError({
        err: 'AS_logoutFromGroupHandler()->ERROR: ' + e.toString(),
      });

      const {code, message} = e;

      yield put(AppActions.auth.actions.logoutDeviceError({code, message}));
    }

    // yield put(AppActions.auth.actions.loginDeviceBegin());
    //
    // const {groupName, groupPassword, deviceName} = action.payload;
    //
    // try {
    //   const authService = Services.services().authService;
    //
    //   yield call(authService.loginInGroup, {
    //     groupName,
    //     groupPassword,
    //     deviceName,
    //   });
    //
    //   yield put(
    //     AppActions.auth.actions.loginDeviceFinished({
    //       groupName,
    //       groupPassword,
    //       deviceName,
    //     }),
    //   );
    // } catch (e) {
    //   SystemEventsHandler.onError({
    //     err: 'as_loginInGroupSagaHandler()->ERROR: ' + e.toString(),
    //   });
    //
    //   const {code, message} = e;
    //
    //   yield put(AppActions.auth.actions.loginDeviceError({code, message}));
    // }
  };

  return {
    handler,
  };
};

export default AS_logoutFromGroupHandler;
