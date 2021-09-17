package com.vision.modules.surveillance_service.module_actions_executor.handlers;


import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.interfaces.service_request_sender.callbacks.OnDeliveredCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnErrorCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnResponseCallback;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.common.services.surveillance.data.service_requests.response_payloads.SurveillanceServiceResponsePayloads;
import com.vision.common.services.surveillance.data.service_requests.response_payloads.payloads.TestRequestWithPayloadResponsePayload;
import com.vision.modules.modules_common.data.error.ModuleError;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.modules.surveillance_service.module_actions.payloads.SurveillanceServiceJSActionsPayloads;
import com.vision.modules.surveillance_service.module_actions.payloads.payloads.SendRequestPayload;
import com.vision.modules.surveillance_service.module_errors.SurveillanceServiceModuleErrors;
import com.vision.modules.surveillance_service.module_events.payloads.SurveillanceServiceEventsJSPayloads;
import com.vision.modules.surveillance_service.module_events.types.SurveillanceServiceEventTypes;

public class SendRequestHandler implements JSActionHandler {
    private final String ACTION_PAYLOAD = "payload";

    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "SendRequestHandler->handle()");

        ReadableMap payloadMap = action.getMap(ACTION_PAYLOAD);
        if (payloadMap == null) {
            ModuleError error = SurveillanceServiceModuleErrors.badPayload();
            result.reject(error.code(), error.message());
            return;
        }

        SendRequestPayload payload = SurveillanceServiceJSActionsPayloads
                .sendRequestPayload(payloadMap);
        if (!payload.isValid()) {
            ModuleError error = SurveillanceServiceModuleErrors.badPayload();
            result.reject(error.code(), error.message());
            return;
        }

        SurveillanceService surveillanceService = SurveillanceService.get();
        if (!surveillanceService.isInitialized()) {
            ModuleError error = SurveillanceServiceModuleErrors.serviceNotInitialized();
            result.reject(error.code(), error.message());
            return;
        }

        String groupName = surveillanceService.currentGroupName();
        String groupPassword = surveillanceService.currentGroupPassword();
        String deviceName = surveillanceService.currentDeviceName();
        String receiverDeviceName = payload.receiverDeviceName();

        ServiceRequest request = new ServiceRequest(
                deviceName,
                payload.requestType(),
                payload.requestPayload()
        );

        Log.d("tag", "SendRequestHandler->handle(): " + payload.receiverDeviceName() + " - " + payload.requestType() + " - " + payload.requestPayload());

        OnDeliveredCallback onDeliveredCallback = () -> {
            Log.d("tag", "SendRequestHandler->onDeliveredCallback()");
        };
        OnResponseCallback onResponseCallback = response -> {
            Log.d("tag", "SendRequestHandler->onResponseCallback()");

            if (response != null) {
                TestRequestWithPayloadResponsePayload responsePayload =
                        SurveillanceServiceResponsePayloads.testRequestWithPayloadResponsePayload(response.payload());

                Log.d("tag", "SendRequestHandler->onResponseCallback()->RESULT_ONE: " + responsePayload.resultOne());

                context
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit(
                                SurveillanceServiceEventTypes.RESPONSE_RECEIVED,
                                SurveillanceServiceEventsJSPayloads.testRequestResponseEventPayload(
                                        response.requestId(),
                                        responsePayload.resultOne()
                                )
                        );
            } else {
                Log.d("tag", "SendRequestHandler->onResponseCallback(): RESPONSE_IS_NULL");
            }
        };
        OnErrorCallback onErrorCallback = error -> {
            Log.d("tag", "SendRequestHandler->onErrorCallback()");

            context
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(
                            SurveillanceServiceEventTypes.REQUEST_ERROR,
                            SurveillanceServiceEventsJSPayloads.requestError(
                                    request.id(),
                                    error.code(),
                                    error.message()
                            )
                    );
        };

        surveillanceService.sendRequest(
                groupName,
                groupPassword,
                receiverDeviceName,
                request,
                onDeliveredCallback,
                onResponseCallback,
                onErrorCallback
        );

        result.resolve(request.id());
    }
}
