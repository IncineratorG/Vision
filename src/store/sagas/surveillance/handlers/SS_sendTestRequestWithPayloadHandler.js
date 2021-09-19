import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import {call, put} from '@redux-saga/core/effects';
import AppActions from '../../../actions/AppActions';
import Services from '../../../../services/Services';
import NativeSurveillanceRequests from '../../../../services/native-libs/surveillance/requests/NativeSurveillanceRequests';

const SS_sendTestRequestWithPayloadHandler = ({channel}) => {
  const actionsChannel = channel;

  const handler = function* (action) {
    const {receiverDeviceName, valueOne, valueTwo} = action.payload;

    SystemEventsHandler.onInfo({
      info:
        'SS_sendTestRequestWithPayloadHandler->handler(): ' +
        JSON.stringify(action),
    });

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
        SystemEventsHandler.onInfo({
          info:
            'SS_sendTestRequestWithPayloadHandler->onComplete(): ' +
            JSON.stringify(data),
        });

        actionsChannel.put(
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
