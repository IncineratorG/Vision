import {call, put} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../actions/AppActions';
import Services from '../../../../services/Services';

const SS_cancelTestRequestWithPayloadHandler = ({channel}) => {
  const handler = function* (action) {
    SystemEventsHandler.onInfo({
      info:
        'SS_cancelTestRequestWithPayloadHandler->handler(): ' +
        JSON.stringify(action),
    });

    // yield put(AppActions.surveillance.actions.getDevicesInGroupBegin());
    //
    // const {groupName, groupPassword, deviceName} = action.payload;

    try {
      // const surveillanceService = Services.services().surveillanceService;
      //
      // const result = yield call(surveillanceService.getDevicesInGroup, {
      //   groupName,
      //   groupPassword,
      //   deviceName,
      // });
      //
      // SystemEventsHandler.onInfo({
      //   info:
      //     'SS_cancelTestRequestWithPayloadHandler->handler()->RESULT: ' +
      //     JSON.stringify(result),
      // });
      //
      // yield put(
      //   AppActions.surveillance.actions.getDevicesInGroupFinished({
      //     groupName,
      //     groupPassword,
      //     deviceName,
      //     devicesArray: [...result],
      //   }),
      // );
    } catch (e) {
      SystemEventsHandler.onError({
        err:
          'SS_cancelTestRequestWithPayloadHandler->handler()->ERROR: ' +
          e.toString(),
      });

      // const {code, message} = e;
      //
      // yield put(
      //   AppActions.surveillance.actions.getDevicesInGroupError({code, message}),
      // );
    }
  };

  return {
    handler,
  };
};

export default SS_cancelTestRequestWithPayloadHandler;
