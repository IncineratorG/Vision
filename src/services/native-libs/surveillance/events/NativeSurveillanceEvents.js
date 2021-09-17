import {NativeEventEmitter} from 'react-native';
import NativeSurveillanceLib from '../lib/NativeSurveillanceLib';
import NativeSurveillanceConstants from '../constants/NativeSurveillanceConstants';

const NativeSurveillanceEvents = () => {
  const eventEmitter = new NativeEventEmitter(NativeSurveillanceLib);

  const requestErrorEventPayload = (data) => {
    const {requestId, code, message} = data;
  };

  const responseReceivedEventPayload = (data) => {
    return data;
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
