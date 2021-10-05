import {channel} from 'redux-saga';
import {takeLatest, take, put} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import ASS_getAppSettingsHandler from './handlers/ASS_getAppSettingsHandler';
import AppActions from '../../actions/AppActions';
import ASS_updateAppSettingsHandler from './handlers/ASS_updateAppSettingsHandler';

const AppSettingsSaga = () => {
  const sagaChannel = channel();

  const {handler: getAppSettingsHandler} = ASS_getAppSettingsHandler({
    channel: sagaChannel,
  });
  const {handler: updateAppSettingsHandler} = ASS_updateAppSettingsHandler({
    channel: sagaChannel,
  });

  const handlers = function* () {
    SystemEventsHandler.onInfo({info: 'AppSettingsSaga->handlers()'});

    yield takeLatest(
      AppActions.appSettings.types.GET_APP_SETTINGS,
      getAppSettingsHandler,
    );
    yield takeLatest(
      AppActions.appSettings.types.SET_RECEIVE_NOTIFICATIONS_FROM_CURRENT_GROUP,
      updateAppSettingsHandler,
    );
    yield takeLatest(
      AppActions.appSettings.types.SET_FRONT_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY,
      updateAppSettingsHandler,
    );
    yield takeLatest(
      AppActions.appSettings.types.SET_BACK_CAMERA_IMAGE_REQUEST_IMAGE_QUALITY,
      updateAppSettingsHandler,
    );
  };

  const events = function* () {
    SystemEventsHandler.onInfo({info: 'AppSettingsSaga->events()'});

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

export default AppSettingsSaga();
