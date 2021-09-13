import {call, put} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import Services from '../../../../services/Services';
import AppActions from '../../../actions/AppActions';

function* as_createGroupWithDeviceSagaHandler(action) {
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
      err: 'as_createGroupWithDeviceSagaHandler()->ERROR: ' + e.toString(),
    });

    const {code, message} = e;

    yield put(
      AppActions.auth.actions.createGroupWithDeviceError({code, message}),
    );
  }
}

export default as_createGroupWithDeviceSagaHandler;
