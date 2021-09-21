import {call, put} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../actions/AppActions';
import Services from '../../../../services/Services';

const SS_checkServiceStatusHandler = ({channel}) => {
  const handler = function* (action) {
    yield put(AppActions.surveillanceCommon.actions.checkServiceStatusBegin());

    try {
      const surveillanceService = Services.services().surveillanceService;

      const isRunning = yield call(surveillanceService.isServiceRunning);

      yield put(
        AppActions.surveillanceCommon.actions.checkServiceStatusFinished({
          isRunning,
        }),
      );
    } catch (e) {
      SystemEventsHandler.onError({
        err: 'SS_checkServiceStatusHandler->handler()->ERROR: ' + e.toString(),
      });

      const {code, message} = e;

      yield put(
        AppActions.surveillanceCommon.actions.checkServiceStatusError({
          code,
          message,
        }),
      );
    }
  };

  return {
    handler,
  };
};

export default SS_checkServiceStatusHandler;
