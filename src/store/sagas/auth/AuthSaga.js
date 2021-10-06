import {channel} from 'redux-saga';
import {takeLatest, take, put} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AS_createGroupWithDeviceHandler from './handlers/AS_createGroupWithDeviceHandler';
import AppActions from '../../actions/AppActions';
import AS_loginInGroupHandler from './handlers/AS_loginInGroupHandler';
import AS_logoutFromGroupHandler from './handlers/AS_logoutFromGroupHandler';
import AS_registerDeviceInGroupHandler from './handlers/AS_registerDeviceInGroupHandler';

const AuthSaga = () => {
  const sagaChannel = channel();

  const {handler: createGroupWithDeviceHandler} =
    AS_createGroupWithDeviceHandler({channel: sagaChannel});
  const {handler: loginInGroupHandler} = AS_loginInGroupHandler({
    channel: sagaChannel,
  });
  const {handler: logoutFromGroupHandler} = AS_logoutFromGroupHandler({
    channel: sagaChannel,
  });
  const {handler: registerDeviceInGroupHandler} =
    AS_registerDeviceInGroupHandler({channel: sagaChannel});

  const handlers = function* () {
    SystemEventsHandler.onInfo({info: 'AuthSaga->handlers()'});

    yield takeLatest(
      AppActions.auth.types.REGISTER_DEVICE_IN_GROUP,
      registerDeviceInGroupHandler,
    );
    yield takeLatest(
      AppActions.auth.types.CREATE_GROUP_WITH_DEVICE,
      createGroupWithDeviceHandler,
    );
    yield takeLatest(AppActions.auth.types.LOGIN_DEVICE, loginInGroupHandler);
    yield takeLatest(
      AppActions.auth.types.LOGOUT_DEVICE,
      logoutFromGroupHandler,
    );
  };

  const events = function* () {
    SystemEventsHandler.onInfo({info: 'AuthSaga->events()'});

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

export default AuthSaga();
