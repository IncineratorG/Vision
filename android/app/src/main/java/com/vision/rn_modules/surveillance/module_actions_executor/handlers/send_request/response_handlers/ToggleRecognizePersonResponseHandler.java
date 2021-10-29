package com.vision.rn_modules.surveillance.module_actions_executor.handlers.send_request.response_handlers;


import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.services.surveillance.data.responses.payloads.SurveillanceServiceResponsePayloads;
import com.vision.services.surveillance.data.responses.payloads.payloads.ToggleRecognizePersonResponsePayload;
import com.vision.rn_modules.modules_common.interfaces.response_handler.ResponseHandler;
import com.vision.rn_modules.surveillance.module_events.payloads.SurveillanceEventsJSPayloads;
import com.vision.rn_modules.surveillance.module_events.types.SurveillanceEventTypes;

public class ToggleRecognizePersonResponseHandler implements ResponseHandler {
    @Override
    public void handle(ReactApplicationContext context, ServiceResponse response) {
        ToggleRecognizePersonResponsePayload responsePayload =
                SurveillanceServiceResponsePayloads.toggleRecognizePersonResponsePayload(response.payload());

        Log.d("tag", "==> ToggleRecognizePersonResponseHandler->handle(): " + responsePayload.frontCameraRecognizePersonServiceRunning() + " - " + responsePayload.backCameraRecognizePersonServiceRunning());

        context
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        SurveillanceEventTypes.RESPONSE_RECEIVED,
                        SurveillanceEventsJSPayloads.toggleRecognizePersonResponseEventPayload(
                                response.requestId(),
                                responsePayload.frontCameraRecognizePersonServiceRunning(),
                                responsePayload.backCameraRecognizePersonServiceRunning()
                        )
                );
    }
}
