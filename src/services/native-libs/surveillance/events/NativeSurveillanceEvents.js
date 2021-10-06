import {NativeEventEmitter} from 'react-native';
import NativeSurveillanceLib from '../lib/NativeSurveillanceLib';
import NativeSurveillanceConstants from '../constants/NativeSurveillanceConstants';

const NativeSurveillanceEvents = () => {
  const eventEmitter = new NativeEventEmitter(NativeSurveillanceLib);

  const requestDeliveredEventPayload = (data) => {
    const {requestId} = data;
    return {
      requestId,
    };
  };

  const requestErrorEventPayload = (data) => {
    const {requestId, code, message} = data;
    return {
      requestId,
      code,
      message,
    };
  };

  const responseReceivedEventPayload = (data) => {
    const {requestId, payload} = data;
    return {requestId, payload};
  };

  return {
    types: NativeSurveillanceConstants.eventTypes,
    eventEmitter,
    payloads: {
      requestDeliveredEventPayload,
      requestErrorEventPayload,
      responseReceivedEventPayload,
    },
  };
};

export default NativeSurveillanceEvents();
