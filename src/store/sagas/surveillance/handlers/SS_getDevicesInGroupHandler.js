import {call, put} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../actions/AppActions';
import Services from '../../../../services/Services';

const SS_getDevicesInGroupSaga = ({channel}) => {
  const handler = function* (action) {
    SystemEventsHandler.onInfo({
      info: 'SS_getDevicesInGroupSaga->handler(): ' + JSON.stringify(action),
    });

    yield put(AppActions.surveillanceCommon.actions.getDevicesInGroupBegin());

    const {groupName, groupPassword, deviceName} = action.payload;

    try {
      const surveillanceService = Services.services().surveillanceService;

      const result = yield call(surveillanceService.getDevicesInGroup, {
        groupName,
        groupPassword,
        deviceName,
      });

      SystemEventsHandler.onInfo({
        info:
          'SS_getDevicesInGroupSaga->handler()->RESULT: ' +
          JSON.stringify(result),
      });

      yield put(
        AppActions.surveillanceCommon.actions.getDevicesInGroupFinished({
          groupName,
          groupPassword,
          deviceName,
          devicesArray: [...result],
        }),
      );
    } catch (e) {
      SystemEventsHandler.onError({
        err: 'SS_getDevicesInGroupSaga->handler()->ERROR: ' + e.toString(),
      });

      const {code, message} = e;

      yield put(
        AppActions.surveillanceCommon.actions.getDevicesInGroupError({
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

export default SS_getDevicesInGroupSaga;
