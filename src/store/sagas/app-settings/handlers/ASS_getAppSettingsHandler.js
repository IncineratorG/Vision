import {call, put} from '@redux-saga/core/effects';
import AppActions from '../../../actions/AppActions';
import Services from '../../../../services/Services';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';

const ASS_getAppSettingsHandler = ({channel}) => {
  const handler = function* (action) {
    yield put(AppActions.appSettings.actions.getAppSettingsBegin());

    try {
      const appSettingsService = Services.services().appSettingsService;

      const appSettings = yield call(appSettingsService.getAppSettings);

      yield put(
        AppActions.appSettings.actions.getAppSettingsFinished({appSettings}),
      );
    } catch (e) {
      SystemEventsHandler.onError({
        err: 'ASS_getAppSettingsHandler()->ERROR: ' + e.toString(),
      });

      const {code, message} = e;

      yield put(
        AppActions.appSettings.actions.getAppSettingsError({code, message}),
      );
    }
  };

  return {
    handler,
  };
};

export default ASS_getAppSettingsHandler;
