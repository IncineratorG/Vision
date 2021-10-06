import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import {call, put} from '@redux-saga/core/effects';
import AppActions from '../../../actions/AppActions';
import Services from '../../../../services/Services';

const SS_sendTestRequestWithPayloadHandler = ({channel}) => {
  const actionsChannel = channel;

  const handler = function* (action) {
    const {receiverDeviceName, valueOne, valueTwo} = action.payload;

    yield put(
      AppActions.surveillanceCommon.actions.sendTestRequestWithPayloadBegin(),
    );

    try {
      const surveillanceService = Services.services().surveillanceService;

      const request = surveillanceService.requests.testRequestWithPayload({
        receiverDeviceName,
        valueOne,
        valueTwo,
      });

      const onComplete = (data) => {
        const {resultOne} =
          surveillanceService.responses.testRequestWithPayloadResponse(data);

        actionsChannel.put(
          AppActions.surveillanceCommon.actions.sendTestRequestWithPayloadCompleted(
            {
              resultOne,
            },
          ),
        );
      };

      const onCancel = () => {
        actionsChannel.put(
          AppActions.surveillanceCommon.actions.cancelTestRequestWithPayload(),
        );
      };

      const onError = ({code, message}) => {
        actionsChannel.put(
          AppActions.surveillanceCommon.actions.sendTestRequestWithPayloadError(
            {
              code,
              message,
            },
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
        AppActions.surveillanceCommon.actions.sendTestRequestWithPayloadSended({
          requestId,
        }),
      );
    } catch (e) {
      SystemEventsHandler.onError({
        err: 'SS_sendTestRequestWithPayloadHandler()->ERROR: ' + e.toString(),
      });

      const {code, message} = e;

      yield put(
        AppActions.surveillanceCommon.actions.sendTestRequestWithPayloadError({
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
