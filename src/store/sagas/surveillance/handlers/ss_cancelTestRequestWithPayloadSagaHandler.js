import {call, put} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import Services from '../../../../services/Services';
import AppActions from '../../../actions/AppActions';

function* ss_cancelTestRequestWithPayloadSagaHandler(action) {
  SystemEventsHandler.onInfo({
    info:
      'ss_cancelTestRequestWithPayloadSagaHandler: ' + JSON.stringify(action),
  });

  // yield put(AppActions.surveillance.actions.getDevicesInGroupBegin());
  //
  // const {groupName, groupPassword, deviceName} = action.payload;
  //
  // try {
  //     const surveillanceService = Services.services().surveillanceService;
  //
  //     const result = yield call(surveillanceService.getDevicesInGroup, {
  //         groupName,
  //         groupPassword,
  //         deviceName,
  //     });
  //
  //     SystemEventsHandler.onInfo({
  //         info:
  //             'ss_getDevicesInGroupSagaHandler()->RESULT: ' + JSON.stringify(result),
  //     });
  //
  //     yield put(
  //         AppActions.surveillance.actions.getDevicesInGroupFinished({
  //             groupName,
  //             groupPassword,
  //             deviceName,
  //             devicesArray: [...result],
  //         }),
  //     );
  // } catch (e) {
  //     SystemEventsHandler.onError({
  //         err: 'ss_getDevicesInGroupSagaHandler()->ERROR: ' + e.toString(),
  //     });
  //
  //     const {code, message} = e;
  //
  //     yield put(
  //         AppActions.surveillance.actions.getDevicesInGroupError({code, message}),
  //     );
  // }
}

export default ss_cancelTestRequestWithPayloadSagaHandler;
