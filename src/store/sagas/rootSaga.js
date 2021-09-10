import {all, spawn, call} from 'redux-saga/effects';
import {SystemEventsHandler} from '../../utils/common/system-events-handler/SystemEventsHandler';
import authSaga from './auth/authSaga';

function* rootSaga() {
  const sagas = [authSaga];

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
