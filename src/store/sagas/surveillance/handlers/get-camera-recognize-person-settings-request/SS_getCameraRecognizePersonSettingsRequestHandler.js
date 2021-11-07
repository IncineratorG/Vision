import {SystemEventsHandler} from '../../../../../utils/common/system-events-handler/SystemEventsHandler';
import {call, put} from '@redux-saga/core/effects';
import AppActions from '../../../../actions/AppActions';
import Services from '../../../../../services/Services';

const SS_getCameraRecognizePersonSettingsRequestHandler = ({channel}) => {
  const actionsChannel = channel;

  const handler = function* (action) {
    const {receiverDeviceName, cameraType} = action.payload;

    SystemEventsHandler.onInfo({
      info:
        'SS_getCameraRecognizePersonSettingsRequestHandler: ' +
        receiverDeviceName,
    });

    yield put(
      AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.actions.sendGetCameraRecognizePersonSettingsRequestBegin(
        {cameraType},
      ),
    );

    try {
      const surveillanceService = Services.services().surveillanceService;

      const request =
        surveillanceService.requests.getCameraRecognizePersonSettings({
          receiverDeviceName,
          cameraType,
        });

      const onComplete = (data) => {
        const {image} =
          surveillanceService.responses.getCameraRecognizePersonSettings(data);

        actionsChannel.put(
          AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.actions.sendGetCameraRecognizePersonSettingsRequestCompleted(
            {
              image,
            },
          ),
        );
      };

      const onCancel = () => {
        actionsChannel.put(
          AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.actions.sendGetCameraRecognizePersonSettingsRequestCancelled(),
        );
      };

      const onError = ({code, message}) => {
        actionsChannel.put(
          AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.actions.sendGetCameraRecognizePersonSettingsRequestError(
            {code, message},
          ),
        );
      };

      const requestId = yield call(surveillanceService.sendRequest, {
        request,
        onComplete,
        onCancel,
        onError,
      });

      yield put(
        AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.actions.sendGetCameraRecognizePersonSettingsRequestSended(
          {requestId},
        ),
      );
    } catch (e) {
      SystemEventsHandler.onError({
        err:
          'SS_getCameraRecognizePersonSettingsRequestHandler()->ERROR: ' +
          e.toString(),
      });

      const {code, message} = e;

      yield put(
        AppActions.surveillanceGetCameraRecognizePersonSettingsRequest.actions.sendGetCameraRecognizePersonSettingsRequestError(
          {
            code,
            message,
          },
        ),
      );
    }
  };

  return {
    handler,
  };
};

export default SS_getCameraRecognizePersonSettingsRequestHandler;
