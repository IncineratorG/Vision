import {call, put} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import Services from '../../../../services/Services';
import AppActions from '../../../actions/AppActions';

function* as_loginDeviceSagaHandler(action) {
  yield put(AppActions.auth.actions.loginDeviceBegin());

  const {groupLogin, groupPassword, deviceName} = action.payload;

  try {
    const authService = Services.services().authService;

    yield call(authService.loginDevice, {
      groupLogin,
      groupPassword,
      deviceName,
    });

    yield put(
      AppActions.auth.actions.loginDeviceFinished({
        groupLogin,
        groupPassword,
        deviceName,
      }),
    );
  } catch (e) {
    SystemEventsHandler.onError({
      err: 'as_loginDeviceSagaHandler()->ERROR: ' + e.toString(),
    });

    const {code, message} = e;

    yield put(AppActions.auth.actions.loginDeviceError({code, message}));
  }
}

export default as_loginDeviceSagaHandler;
