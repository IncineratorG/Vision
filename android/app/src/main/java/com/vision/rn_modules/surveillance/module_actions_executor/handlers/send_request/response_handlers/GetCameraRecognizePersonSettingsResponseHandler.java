package com.vision.rn_modules.surveillance.module_actions_executor.handlers.send_request.response_handlers;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.rn_modules.modules_common.interfaces.response_handler.ResponseHandler;
import com.vision.rn_modules.surveillance.module_events.payloads.SurveillanceEventsJSPayloads;
import com.vision.rn_modules.surveillance.module_events.types.SurveillanceEventTypes;
import com.vision.services.surveillance.data.responses.payloads.SurveillanceServiceResponsePayloads;
import com.vision.services.surveillance.data.responses.payloads.payloads.GetCameraRecognizePersonSettingsResponsePayload;

public class GetCameraRecognizePersonSettingsResponseHandler implements ResponseHandler {
    @Override
    public void handle(ReactApplicationContext context, ServiceResponse response) {
        GetCameraRecognizePersonSettingsResponsePayload responsePayload =
                SurveillanceServiceResponsePayloads.getCameraRecognizePersonSettingsResponsePayload(response.payload());

        context
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        SurveillanceEventTypes.RESPONSE_RECEIVED,
                        SurveillanceEventsJSPayloads.getCameraRecognizePersonSettingsResponseEventPayload(
                                response.requestId(),
                                responsePayload.image()
                        )
                );
    }
}
