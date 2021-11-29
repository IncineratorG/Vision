package com.vision.rn_modules.surveillance.module_actions_executor.handlers.send_request.response_handlers;


import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.services.surveillance.responses.payloads.SurveillanceServiceResponsePayloads;
import com.vision.services.surveillance.responses.payloads.payloads.TestRequestWithPayloadResponsePayload;
import com.vision.rn_modules.modules_common.interfaces.response_handler.ResponseHandler;
import com.vision.rn_modules.surveillance.module_events.payloads.SurveillanceEventsJSPayloads;
import com.vision.rn_modules.surveillance.module_events.types.SurveillanceEventTypes;

public class TestRequestWithPayloadResponseHandler implements ResponseHandler {
    @Override
    public void handle(ReactApplicationContext context, ServiceResponse response) {
        TestRequestWithPayloadResponsePayload responsePayload =
                SurveillanceServiceResponsePayloads.testRequestWithPayloadResponsePayload(response.payload());

        context
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        SurveillanceEventTypes.RESPONSE_RECEIVED,
                        SurveillanceEventsJSPayloads.testRequestResponseEventPayload(
                                response.requestId(),
                                responsePayload.resultOne()
                        )
                );
    }
}
