import {call, select} from '@redux-saga/core/effects';
import Services from '../../../../services/Services';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';

const ASS_updateAppSettingsHandler = ({channel}) => {
  const handler = function* (action) {
    const {surveillance} = yield select((state) => state.appSettings);

    const appSettings = {surveillance};

    SystemEventsHandler.onInfo({
      info: 'ASS_updateAppSettingsHandler(): ' + JSON.stringify(appSettings),
    });

    try {
      const appSettingsService = Services.services().appSettingsService;

      yield call(appSettingsService.updateAppSettings, {appSettings});
    } catch (e) {
      SystemEventsHandler.onError({
        err: 'ASS_updateAppSettingsHandler()->ERROR: ' + e.toString(),
      });
    }
  };

  return {
    handler,
  };
};

export default ASS_updateAppSettingsHandler;
