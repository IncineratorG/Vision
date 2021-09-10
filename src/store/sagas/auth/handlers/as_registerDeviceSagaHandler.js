import {call, put} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import Services from '../../../../services/Services';
import AppActions from '../../../actions/AppActions';

function* as_registerDeviceSagaHandler(action) {
  yield put(AppActions.auth.actions.registerDeviceBegin());

  const {groupLogin, groupPassword, deviceName} = action.payload;

  try {
    const authService = Services.services().authService;

    yield call(authService.registerDevice, {
      groupLogin,
      groupPassword,
      deviceName,
    });

    yield put(
      AppActions.auth.actions.registerDeviceFinished({
        groupLogin,
        groupPassword,
        deviceName,
      }),
    );
  } catch (e) {
    SystemEventsHandler.onError({
      err: 'as_registerDeviceSagaHandler()->ERROR: ' + e.toString(),
    });

    const {code, message} = e;

    yield put(AppActions.auth.actions.registerDeviceError({code, message}));
  }
}

export default as_registerDeviceSagaHandler;
