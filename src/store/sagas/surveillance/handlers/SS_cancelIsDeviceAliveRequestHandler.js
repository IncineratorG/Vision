import {call, put, select} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../actions/AppActions';
import Services from '../../../../services/Services';

const SS_cancelIsDeviceAliveRequestHandler = ({channel}) => {
  const handler = function* (action) {
    const {requestId, inProgress} = yield select(
      (state) => state.surveillanceIsDeviceAliveRequest.isDeviceAliveRequest,
    );

    try {
      if (inProgress && requestId) {
        const surveillanceService = Services.services().surveillanceService;
        yield call(surveillanceService.cancelRequest, {requestId});
      }

      yield put(
        AppActions.surveillanceIsDeviceAliveRequest.actions.sendIsAliveRequestCancelled(),
      );
    } catch (e) {
      SystemEventsHandler.onError({
        err:
          'SS_cancelIsDeviceAliveRequestHandler->handler()->ERROR: ' +
          e.toString(),
      });
    }
  };

  return {
    handler,
  };
};

export default SS_cancelIsDeviceAliveRequestHandler;
