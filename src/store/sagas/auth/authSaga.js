import {takeLatest} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../actions/AppActions';
import as_registerDeviceSagaHandler from './handlers/as_registerDeviceSagaHandler';
import as_loginDeviceSagaHandler from './handlers/as_loginDeviceSagaHandler';

function* authSaga() {
  SystemEventsHandler.onInfo({info: 'authSaga'});

  yield takeLatest(
    AppActions.auth.types.REGISTER_DEVICE,
    as_registerDeviceSagaHandler,
  );
  yield takeLatest(
    AppActions.auth.types.LOGIN_DEVICE,
    as_loginDeviceSagaHandler,
  );
}

export default authSaga;
