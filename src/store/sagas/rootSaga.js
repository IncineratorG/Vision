import {all, spawn, call} from 'redux-saga/effects';
import {SystemEventsHandler} from '../../utils/common/system-events-handler/SystemEventsHandler';
import SurveillanceSaga from './surveillance/SurveillanceSaga';
import AuthSaga from './auth/AuthSaga';

function* rootSaga() {
  const {handlers: authSagaHandlers, events: authSagaEvents} = AuthSaga;
  const {handlers: surveillanceSagaHandlers, events: surveillanceSagaEvents} =
    SurveillanceSaga;

  const sagas = [
    authSagaHandlers,
    authSagaEvents,
    surveillanceSagaHandlers,
    surveillanceSagaEvents,
  ];

  yield all(
    sagas.map((saga) =>
      spawn(function* () {
        while (true) {
          try {
            yield call(saga);
            break;
          } catch (e) {
            SystemEventsHandler.onError({err: 'rootSaga()->ERROR: ' + e});
          }
        }
      }),
    ),
  );
}

export default rootSaga;
