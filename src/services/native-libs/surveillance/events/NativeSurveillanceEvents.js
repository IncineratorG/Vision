import {NativeEventEmitter} from 'react-native';
import NativeSurveillanceLib from '../lib/NativeSurveillanceLib';
import NativeSurveillanceConstants from '../constants/NativeSurveillanceConstants';

const NativeSurveillanceEvents = () => {
  const eventEmitter = new NativeEventEmitter(NativeSurveillanceLib);

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
      requestErrorEventPayload,
      responseReceivedEventPayload,
    },
  };
};

export default NativeSurveillanceEvents();
