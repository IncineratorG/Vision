import {takeLatest} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../actions/AppActions';
import ss_getDevicesInGroupSagaHandler from './handlers/ss_getDevicesInGroupSagaHandler';
import ss_sendTestRequestWithPayloadSagaHandler from './handlers/ss_sendTestRequestWithPayloadSagaHandler';
import ss_cancelTestRequestWithPayloadSagaHandler from './handlers/ss_cancelTestRequestWithPayloadSagaHandler';

function* surveillanceSaga() {
  SystemEventsHandler.onInfo({info: 'surveillanceSaga'});

  yield takeLatest(
    AppActions.surveillance.types.GET_DEVICES_IN_GROUP,
    ss_getDevicesInGroupSagaHandler,
  );
  yield takeLatest(
    AppActions.surveillance.types.SEND_TEST_REQUEST_WITH_PAYLOAD,
    ss_sendTestRequestWithPayloadSagaHandler,
  );
  yield takeLatest(
    AppActions.surveillance.types.CANCEL_TEST_REQUEST_WITH_PAYLOAD,
    ss_cancelTestRequestWithPayloadSagaHandler,
  );
}

export default surveillanceSaga;
