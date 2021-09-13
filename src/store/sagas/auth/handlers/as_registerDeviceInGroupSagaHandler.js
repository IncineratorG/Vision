import {call, put} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import Services from '../../../../services/Services';
import AppActions from '../../../actions/AppActions';

function* as_registerDeviceInGroupSagaHandler(action) {
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
      err: 'as_registerDeviceInGroupSagaHandler()->ERROR: ' + e.toString(),
    });

    const {code, message} = e;

    yield put(
      AppActions.auth.actions.registerDeviceInGroupError({code, message}),
    );
  }
}

export default as_registerDeviceInGroupSagaHandler;
