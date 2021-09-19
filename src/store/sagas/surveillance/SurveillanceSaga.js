import {channel} from 'redux-saga';
import {takeLatest, take, put} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../actions/AppActions';
import SS_sendTestRequestWithPayloadHandler from './handlers/SS_sendTestRequestWithPayloadHandler';
import SS_getDevicesInGroupSaga from './handlers/SS_getDevicesInGroupHandler';
import SS_cancelTestRequestWithPayloadHandler from './handlers/SS_cancelTestRequestWithPayloadHandler';
import SS_startServiceHandler from './handlers/SS_startServiceHandler';
import SS_stopServiceHandler from './handlers/SS_stopServiceHandler';
import SS_checkServiceStatusHandler from './handlers/SS_checkServiceStatusHandler';

const SurveillanceSaga = () => {
  const sagaChannel = channel();

  const {handler: getDevicesInGroupHandler} = SS_getDevicesInGroupSaga({
    channel: sagaChannel,
  });
  const {handler: startServiceHandler} = SS_startServiceHandler({
    channel: sagaChannel,
  });
  const {handler: stopServiceHandler} = SS_stopServiceHandler({
    channel: sagaChannel,
  });
  const {handler: checkServiceStatusHandler} = SS_checkServiceStatusHandler({
    channel: sagaChannel,
  });
  const {handler: sendTestRequestWithPayloadHandler} =
    SS_sendTestRequestWithPayloadHandler({channel: sagaChannel});
  const {handler: cancelTestRequestWithPayloadHandler} =
    SS_cancelTestRequestWithPayloadHandler({channel: sagaChannel});

  const handlers = function* () {
    SystemEventsHandler.onInfo({info: 'SurveillanceSaga->handlers()'});

    yield takeLatest(
      AppActions.surveillance.types.GET_DEVICES_IN_GROUP,
      getDevicesInGroupHandler,
    );
    yield takeLatest(
      AppActions.surveillance.types.START_SERVICE,
      startServiceHandler,
    );
    yield takeLatest(
      AppActions.surveillance.types.STOP_SERVICE,
      stopServiceHandler,
    );
    yield takeLatest(
      AppActions.surveillance.types.CHECK_SERVICE_STATUS,
      checkServiceStatusHandler,
    );
    yield takeLatest(
      AppActions.surveillance.types.SEND_TEST_REQUEST_WITH_PAYLOAD,
      sendTestRequestWithPayloadHandler,
    );
    yield takeLatest(
      AppActions.surveillance.types.CANCEL_TEST_REQUEST_WITH_PAYLOAD,
      cancelTestRequestWithPayloadHandler,
    );
  };

  const events = function* () {
    SystemEventsHandler.onInfo({info: 'SurveillanceSaga->events()'});

    while (true) {
      const action = yield take(sagaChannel);
      yield put(action);
    }
  };

  return {
    handlers,
    events,
  };
};

export default SurveillanceSaga();
