import {call, put, select} from '@redux-saga/core/effects';
import AppActions from '../../../actions/AppActions';
import Services from '../../../../services/Services';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';

const AS_logoutFromGroupHandler = ({channel}) => {
  const handler = function* (action) {
    yield put(AppActions.auth.actions.logoutDeviceBegin());

    try {
      const authService = Services.services().authService;
      yield call(authService.logoutFromGroup);

      yield put(AppActions.auth.actions.logoutDeviceFinished());
    } catch (e) {
      SystemEventsHandler.onError({
        err: 'AS_logoutFromGroupHandler()->ERROR: ' + e.toString(),
      });

      const {code, message} = e;

      yield put(AppActions.auth.actions.logoutDeviceError({code, message}));
    }
  };

  return {
    handler,
  };
};

export default AS_logoutFromGroupHandler;
