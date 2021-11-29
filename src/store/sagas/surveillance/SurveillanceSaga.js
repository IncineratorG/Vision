import {channel} from 'redux-saga';
import {takeLatest, take, put} from '@redux-saga/core/effects';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../actions/AppActions';
import SS_sendTestRequestWithPayloadHandler from './handlers/test-request-with-payload/SS_sendTestRequestWithPayloadHandler';
import SS_getDevicesInGroupSaga from './handlers/SS_getDevicesInGroupHandler';
import SS_cancelTestRequestWithPayloadHandler from './handlers/test-request-with-payload/SS_cancelTestRequestWithPayloadHandler';
import SS_startServiceHandler from './handlers/SS_startServiceHandler';
import SS_stopServiceHandler from './handlers/SS_stopServiceHandler';
import SS_checkServiceStatusHandler from './handlers/SS_checkServiceStatusHandler';
import SS_sendIsDeviceAliveRequestHandler from './handlers/is-device-alive-request/SS_sendIsDeviceAliveRequestHandler';
import SS_sendTakeBackCameraImageRequestHandler from './handlers/take-back-camera-image-request/SS_sendTakeBackCameraImageRequestHandler';
import SS_cancelIsDeviceAliveRequestHandler from './handlers/is-device-alive-request/SS_cancelIsDeviceAliveRequestHandler';
import SS_cancelTakeBackCameraImageRequestHandler from './handlers/take-back-camera-image-request/SS_cancelTakeBackCameraImageRequestHandler';
import SS_sendTakeFrontCameraImageRequestHandler from './handlers/take-front-camera-image-request/SS_sendTakeFrontCameraImageRequestHandler';
import SS_cancelTakeFrontCameraImageRequestHandler from './handlers/take-front-camera-image-request/SS_cancelTakeFrontCameraImageRequestHandler';
import SS_sendToggleDetectDeviceMovementRequestHandler from './handlers/toggle-detect-device-movement-request/SS_sendToggleDetectDeviceMovementRequestHandler';
import SS_cancelToggleDetectDeviceMovementRequestHandler from './handlers/toggle-detect-device-movement-request/SS_cancelToggleDetectDeviceMovementRequestHandler';
import SS_sendToggleRecognizePersonRequestHandler from './handlers/toggle-recognize-person-request/SS_sendToggleRecognizePersonRequestHandler';
import SS_cancelToggleRecognizePersonRequestHandler from './handlers/toggle-recognize-person-request/SS_cancelToggleRecognizePersonRequestHandler';
import SS_getCameraRecognizePersonSettingsRequestHandler from './handlers/get-camera-recognize-person-settings-request/SS_getCameraRecognizePersonSettingsRequestHandler';
import SS_cancelGetCameraRecognizePersonSettingsRequestHandler from './handlers/get-camera-recognize-person-settings-request/SS_cancelGetCameraRecognizePersonSettingsRequestHandler';

const SurveillanceSaga = () => {
  const sagaChannel = channel();

  const {handler: getDevicesInGroupHandler} = SS_getDevicesInGroupSaga({
    channel: sagaChannel,
  });
  const {handler: startServiceHandler} = SS_startServiceHandler({
    channel: sagaChannel,
  });
  const {handler: stopServiceHandler} = SS_stopServiceHandler({
    channel: sagaChannel,
  });
  const {handler: checkServiceStatusHandler} = SS_checkServiceStatusHandler({
    channel: sagaChannel,
  });
  const {handler: sendTestRequestWithPayloadHandler} =
    SS_sendTestRequestWithPayloadHandler({channel: sagaChannel});
  const {handler: cancelTestRequestWithPayloadHandler} =
    SS_cancelTestRequestWithPayloadHandler({channel: sagaChannel});
  const {handler: sendIsDeviceAliveRequestHandler} =
    SS_sendIsDeviceAliveRequestHandler({channel: sagaChannel});
  const {handler: cancelIsDeviceAliveRequestHandler} =
    SS_cancelIsDeviceAliveRequestHandler({channel: sagaChannel});
  const {handler: sendTakeBackCameraImageRequestHandler} =
    SS_sendTakeBackCameraImageRequestHandler({channel: sagaChannel});
  const {handler: cancelTakeBackCameraImageRequestHandler} =
    SS_cancelTakeBackCameraImageRequestHandler({channel: sagaChannel});
  const {handler: sendTakeFrontCameraImageRequestHandler} =
    SS_sendTakeFrontCameraImageRequestHandler({channel: sagaChannel});
  const {handler: cancelTakeFrontCameraImageRequestHandler} =
    SS_cancelTakeFrontCameraImageRequestHandler({channel: sagaChannel});
  const {handler: sendToggleDetectDeviceMovementRequestHandler} =
    SS_sendToggleDetectDeviceMovementRequestHandler({channel: sagaChannel});
  const {handler: cancelToggleDetectDeviceMovementRequestHandler} =
    SS_cancelToggleDetectDeviceMovementRequestHandler({channel: sagaChannel});
  const {handler: sendToggleRecognizePersonRequestHandler} =
    SS_sendToggleRecognizePersonRequestHandler({channel: sagaChannel});
  const {handler: cancelToggleRecognizePersonRequestHandler} =
    SS_cancelToggleRecognizePersonRequestHandler({channel: sagaChannel});
  const {handler: sendGetCameraRecognizePersonSettingsRequestHandler} =
    SS_getCameraRecognizePersonSettingsRequestHandler({channel: sagaChannel});
  const {handler: cancelGetCameraRecognizePersonSettingsRequestHandler} =
    SS_cancelGetCameraRecognizePersonSettingsRequestHandler({
      channel: sagaChannel,
    });

  const handlers = function* () {
    SystemEventsHandler.onInfo({info: 'SurveillanceSaga->handlers()'});

    yield takeLatest(
      AppActions.surveillanceCommon.types.GET_DEVICES_IN_GROUP,
      getDevicesInGroupHandler,
    );
    yield takeLatest(
      AppActions.surveillanceCommon.types.START_SERVICE,
      startServiceHandler,
    );
    yield takeLatest(
      AppActions.surveillanceCommon.types.STOP_SERVICE,
      stopServiceHandler,
    );
    yield takeLatest(
      AppActions.surveillanceCommon.types.CHECK_SERVICE_STATUS,
      checkServiceStatusHandler,
    );
    yield takeLatest(
      AppActions.surveillanceCommon.types.SEND_TEST_REQUEST_WITH_PAYLOAD,
      sendTestRequestWithPayloadHandler,
    );
    yield takeLatest(
      AppActions.surveillanceCommon.types.CANCEL_TEST_REQUEST_WITH_PAYLOAD,
      cancelTestRequestWithPayloadHandler,
    );
    yield takeLatest(
      AppActions.surveillanceIsDeviceAliveRequest.types.SEND_IS_ALIVE_REQUEST,
      sendIsDeviceAliveRequestHandler,
    );
    yield takeLatest(
      AppActions.surveillanceIsDeviceAliveRequest.types
        .CANCEL_SEND_IS_ALIVE_REQUEST,
      cancelIsDeviceAliveRequestHandler,
    );
    yield takeLatest(
      AppActions.surveillanceTakeBackCameraImageRequest.types
        .SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST,
      sendTakeBackCameraImageRequestHandler,
    );
    yield takeLatest(
      AppActions.surveillanceTakeBackCameraImageRequest.types
        .CANCEL_SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST,
      cancelTakeBackCameraImageRequestHandler,
    );
    yield takeLatest(
      AppActions.surveillanceTakeFrontCameraImageRequest.types
        .SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST,
      sendTakeFrontCameraImageRequestHandler,
    );
    yield takeLatest(
      AppActions.surveillanceTakeFrontCameraImageRequest.types
        .CANCEL_SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST,
      cancelTakeFrontCameraImageRequestHandler,
    );
    yield takeLatest(
      AppActions.surveillanceToggleDetectDeviceMovementRequest.types
        .SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST,
      sendToggleDetectDeviceMovementRequestHandler,
    );
    yield takeLatest(
      AppActions.surveillanceToggleDetectDeviceMovementRequest.types
        .CANCEL_SEND_TOGGLE_DETECT_DEVICE_MOVEMENT_REQUEST,
      cancelToggleDetectDeviceMovementRequestHandler,
    );
    yield takeLatest(
      AppActions.surveillanceToggleRecognizePersonRequest.types
        .SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST,
      sendToggleRecognizePersonRequestHandler,
    );
    yield takeLatest(
      AppActions.surveillanceToggleRecognizePersonRequest.types
        .CANCEL_SEND_TOGGLE_RECOGNIZE_PERSON_REQUEST,
      cancelToggleRecognizePersonRequestHandler,
    );
    yield takeLatest(
      AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.types
        .SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST,
      sendGetCameraRecognizePersonSettingsRequestHandler,
    );
    yield takeLatest(
      AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.types
        .CANCEL_SEND_GET_CAMERA_RECOGNIZE_PERSON_SETTINGS_REQUEST,
      cancelGetCameraRecognizePersonSettingsRequestHandler,
    );
  };

  const events = function* () {
    SystemEventsHandler.onInfo({info: 'SurveillanceSaga->events()'});

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

export default SurveillanceSaga();
