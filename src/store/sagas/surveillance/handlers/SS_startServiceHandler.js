import {call, put} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../actions/AppActions';
import Services from '../../../../services/Services';

const SS_startServiceHandler = ({channel}) => {
  const handler = function* (action) {
    yield put(AppActions.surveillanceCommon.actions.startServiceBegin());

    try {
      const surveillanceService = Services.services().surveillanceService;

      yield call(surveillanceService.startService);

      yield put(AppActions.surveillanceCommon.actions.startServiceFinished());
    } catch (e) {
      SystemEventsHandler.onError({
        err: 'SS_startServiceHandler->handler()->ERROR: ' + e.toString(),
      });

      const {code, message} = e;

      yield put(
        AppActions.surveillanceCommon.actions.startServiceError({
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

export default SS_startServiceHandler;
