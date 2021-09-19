import {call, put} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../actions/AppActions';
import Services from '../../../../services/Services';

const SS_stopServiceHandler = ({channel}) => {
  const handler = function* (action) {
    yield put(AppActions.surveillance.actions.stopServiceBegin());

    try {
      const surveillanceService = Services.services().surveillanceService;

      yield call(surveillanceService.stopService);

      yield put(AppActions.surveillance.actions.stopServiceFinished());
    } catch (e) {
      SystemEventsHandler.onError({
        err: 'SS_stopServiceHandler->handler()->ERROR: ' + e.toString(),
      });

      const {code, message} = e;

      yield put(
        AppActions.surveillance.actions.stopServiceError({code, message}),
      );
    }
  };

  return {
    handler,
  };
};

export default SS_stopServiceHandler;
