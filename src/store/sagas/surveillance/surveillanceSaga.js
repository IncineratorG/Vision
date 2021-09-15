import {takeLatest} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../actions/AppActions';
import ss_getDevicesInGroupSagaHandler from './handlers/ss_getDevicesInGroupSagaHandler';

function* surveillanceSaga() {
  SystemEventsHandler.onInfo({info: 'surveillanceSaga'});

  yield takeLatest(
    AppActions.surveillance.types.GET_DEVICES_IN_GROUP,
    ss_getDevicesInGroupSagaHandler,
  );
}

export default surveillanceSaga;
