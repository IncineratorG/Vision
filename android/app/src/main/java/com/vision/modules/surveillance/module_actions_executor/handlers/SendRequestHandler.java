package com.vision.modules.surveillance.module_actions_executor.handlers;


import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_request_sender.callbacks.OnDeliveredCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnErrorCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnResponseCallback;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.common.services.surveillance.data.responses.payloads.SurveillanceServiceResponsePayloads;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.IsDeviceAliveResponsePayload;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.TakeBackCameraImageResponsePayload;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.TestRequestWithPayloadResponsePayload;
import com.vision.common.services.surveillance.data.requests.types.SurveillanceServiceRequestTypes;
import com.vision.modules.modules_common.data.error.ModuleError;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.modules.surveillance.module_actions.payloads.SurveillanceJSActionsPayloads;
import com.vision.modules.surveillance.module_actions.payloads.payloads.SendRequestPayload;
import com.vision.modules.surveillance.module_errors.SurveillanceModuleErrors;
import com.vision.modules.surveillance.module_events.payloads.SurveillanceEventsJSPayloads;
import com.vision.modules.surveillance.module_events.types.SurveillanceEventTypes;

public class SendRequestHandler implements JSActionHandler {
    private final String ACTION_PAYLOAD = "payload";

    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "SendRequestHandler->handle()");

        ReadableMap payloadMap = action.getMap(ACTION_PAYLOAD);
        if (payloadMap == null) {
            ModuleError error = SurveillanceModuleErrors.badPayload();
            result.reject(error.code(), error.message());
            return;
        }

        SendRequestPayload payload = SurveillanceJSActionsPayloads
                .sendRequestPayload(payloadMap);
        if (!payload.isValid()) {
            ModuleError error = SurveillanceModuleErrors.badPayload();
            result.reject(error.code(), error.message());
            return;
        }

        SurveillanceService surveillanceService = SurveillanceService.get();
        if (!surveillanceService.isInitialized()) {
            ModuleError error = SurveillanceModuleErrors.serviceNotInitialized();
            result.reject(error.code(), error.message());
            return;
        }

        String requestType = payload.requestType();

        String groupName = surveillanceService.currentGroupName();
        String groupPassword = surveillanceService.currentGroupPassword();
        String deviceName = surveillanceService.currentDeviceName();
        String receiverDeviceName = payload.receiverDeviceName();

        ServiceRequest request = new ServiceRequest(
                deviceName,
                requestType,
                payload.requestPayload()
        );

        Log.d("tag", "SendRequestHandler->handle(): " + payload.receiverDeviceName() + " - " + payload.requestType() + " - " + payload.requestPayload());

        OnDeliveredCallback onDeliveredCallback = () -> {
            Log.d("tag", "SendRequestHandler->onDeliveredCallback()");
        };
        OnResponseCallback onResponseCallback = response -> {
            Log.d("tag", "SendRequestHandler->onResponseCallback()");

            if (response != null) {
                processResponse(context, requestType, response);
            } else {
                Log.d("tag", "SendRequestHandler->onResponseCallback(): RESPONSE_IS_NULL");
            }
        };
        OnErrorCallback onErrorCallback = error -> {
            Log.d("tag", "SendRequestHandler->onErrorCallback()");

            context
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(
                            SurveillanceEventTypes.REQUEST_ERROR,
                            SurveillanceEventsJSPayloads.requestError(
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

    private void processResponse(ReactApplicationContext context, String requestType, ServiceResponse response) {
        switch (requestType) {
            case (SurveillanceServiceRequestTypes.TEST_REQUEST_WITH_PAYLOAD): {
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
                break;
            }

            case (SurveillanceServiceRequestTypes.IS_DEVICE_ALIVE): {
                IsDeviceAliveResponsePayload responsePayload =
                        SurveillanceServiceResponsePayloads.isDeviceAliveResponsePayload(response.payload());

                context
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit(
                                SurveillanceEventTypes.RESPONSE_RECEIVED,
                                SurveillanceEventsJSPayloads.isDeviceAliveResponseEventPayload(
                                        response.requestId(),
                                        responsePayload.isAlive()
                                )
                        );
                break;
            }

            case (SurveillanceServiceRequestTypes.TAKE_BACK_CAMERA_IMAGE): {
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
                break;
            }

            default: {
                Log.d("tag", "SendRequestHandler->processResponse()->UNKNOWN_REQUEST_TYPE: " + requestType);
            }
        }
    }
}
