import {takeLatest} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../actions/AppActions';
import as_registerDeviceInGroupSagaHandler from './handlers/as_registerDeviceInGroupSagaHandler';
import as_createGroupWithDeviceSagaHandler from './handlers/as_createGroupWithDeviceSagaHandler';
import as_loginInGroupSagaHandler from './handlers/as_loginInGroupSagaHandler';
import as_logoutFromGroupSagaHandler from './handlers/as_logoutFromGroupSagaHandler';

function* authSaga() {
  SystemEventsHandler.onInfo({info: 'authSaga'});

  yield takeLatest(
    AppActions.auth.types.REGISTER_DEVICE_IN_GROUP,
    as_registerDeviceInGroupSagaHandler,
  );
  yield takeLatest(
    AppActions.auth.types.CREATE_GROUP_WITH_DEVICE,
    as_createGroupWithDeviceSagaHandler,
  );
  yield takeLatest(
    AppActions.auth.types.LOGIN_DEVICE,
    as_loginInGroupSagaHandler,
  );
  yield takeLatest(
    AppActions.auth.types.LOGOUT_DEVICE,
    as_logoutFromGroupSagaHandler,
  );
}

export default authSaga;
