package com.vision.modules.surveillance.module_actions_executor.handlers.send_request.response_handlers;


import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.services.surveillance.data.responses.payloads.SurveillanceServiceResponsePayloads;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.TakeBackCameraImageResponsePayload;
import com.vision.modules.modules_common.interfaces.response_handler.ResponseHandler;
import com.vision.modules.surveillance.module_events.payloads.SurveillanceEventsJSPayloads;
import com.vision.modules.surveillance.module_events.types.SurveillanceEventTypes;

public class TakeBackCameraImageResponseHandler implements ResponseHandler {
    @Override
    public void handle(ReactApplicationContext context, ServiceResponse response) {
        TakeBackCameraImageResponsePayload responsePayload =
                SurveillanceServiceResponsePayloads.takeBackCameraImageResponsePayload(response.payload());

        context
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        SurveillanceEventTypes.RESPONSE_RECEIVED,
                        SurveillanceEventsJSPayloads.takeBackCameraImageResponseEventPayload(
                                response.requestId(),
                                responsePayload.image()
                        )
                );
    }
}