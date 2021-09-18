import {channel} from 'redux-saga';
import {call, take, put} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import Services from '../../../../services/Services';
import AppActions from '../../../actions/AppActions';
import NativeSurveillanceRequests from '../../../../services/native-libs/surveillance/requests/NativeSurveillanceRequests';

const downloadFileChannel = channel();

function* ss_sendTestRequestWithPayloadSagaHandler(action) {
  const {receiverDeviceName, valueOne, valueTwo} = action.payload;

  SystemEventsHandler.onInfo({
    info: 'ss_sendTestRequestWithPayloadSagaHandler: ' + JSON.stringify(action),
  });

  yield put(AppActions.surveillance.actions.sendTestRequestWithPayloadBegin());

  try {
    const surveillanceService = Services.services().surveillanceService;

    const request = NativeSurveillanceRequests.testRequestWithPayload({
      receiverDeviceName,
      valueOne,
      valueTwo,
    });

    const onComplete = (data) => {
      SystemEventsHandler.onInfo({
        info:
          'ss_sendTestRequestWithPayloadSagaHandler->onComplete(): ' +
          JSON.stringify(data),
      });

      downloadFileChannel.put(
        AppActions.surveillance.actions.sendTestRequestWithPayloadCompleted({
          requestId: 'NONE_requestId',
          resultOne: 'NONE_resultOne',
        }),
      );
    };

    const onCancel = () => {};
    const onError = (data) => {};

    yield call(surveillanceService.sendRequest, {
      request,
      onComplete,
      onCancel,
      onError,
    });
  } catch (e) {
    SystemEventsHandler.onError({
      err: 'ss_sendTestRequestWithPayloadSagaHandler()->ERROR: ' + e.toString(),
    });

    const {code, message} = e;

    yield put(
      AppActions.surveillance.actions.sendTestRequestWithPayloadError({
        code,
        message,
      }),
    );
  }

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

function* watchDownloadFileChannel() {
  while (true) {
    SystemEventsHandler.onInfo({info: 'watchDownloadFileChannel'});

    const action = yield take(downloadFileChannel);
    yield put(action);
  }
}

/*
Ok, for who is wondering how to wire the watchDownloadChannel.
you can simply export this code takeEvery(channel, watchDownloadChannel)
and import it into the rootSaga
 */

export default ss_sendTestRequestWithPayloadSagaHandler;
