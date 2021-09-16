import {call, put, select} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import Services from '../../../../services/Services';
import AppActions from '../../../actions/AppActions';

function* as_logoutFromGroupSagaHandler(action) {
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
      err: 'as_logoutFromGroupSagaHandler()->ERROR: ' + e.toString(),
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
}

export default as_logoutFromGroupSagaHandler;
