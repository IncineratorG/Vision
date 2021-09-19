import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import {call, put} from '@redux-saga/core/effects';
import AppActions from '../../../actions/AppActions';
import Services from '../../../../services/Services';
import NativeSurveillanceRequests from '../../../../services/native-libs/surveillance/requests/NativeSurveillanceRequests';
import NativeSurveillanceResponses from '../../../../services/native-libs/surveillance/responses/NativeSurveillanceResponses';

const SS_sendTestRequestWithPayloadHandler = ({channel}) => {
  const actionsChannel = channel;

  const handler = function* (action) {
    const {receiverDeviceName, valueOne, valueTwo} = action.payload;

    yield put(
      AppActions.surveillance.actions.sendTestRequestWithPayloadBegin(),
    );

    try {
      const surveillanceService = Services.services().surveillanceService;

      const request = NativeSurveillanceRequests.testRequestWithPayload({
        receiverDeviceName,
        valueOne,
        valueTwo,
      });

      const onComplete = (data) => {
        const {resultOne} =
          NativeSurveillanceResponses.testRequestWithPayloadResponse(data);

        actionsChannel.put(
          AppActions.surveillance.actions.sendTestRequestWithPayloadCompleted({
            resultOne,
          }),
        );
      };

      const onCancel = () => {
        actionsChannel.put(
          AppActions.surveillance.actions.cancelTestRequestWithPayload(),
        );
      };

      const onError = ({code, message}) => {
        actionsChannel.put(
          AppActions.surveillance.actions.sendTestRequestWithPayloadError({
            code,
            message,
          }),
        );
      };

      const requestId = yield call(surveillanceService.sendRequest, {
        request,
        onComplete,
        onCancel,
        onError,
      });

      yield put(
        AppActions.surveillance.actions.sendTestRequestWithPayloadSended({
          requestId,
        }),
      );
    } catch (e) {
      SystemEventsHandler.onError({
        err: 'SS_sendTestRequestWithPayloadHandler()->ERROR: ' + e.toString(),
      });

      const {code, message} = e;

      yield put(
        AppActions.surveillance.actions.sendTestRequestWithPayloadError({
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

export default SS_sendTestRequestWithPayloadHandler;
