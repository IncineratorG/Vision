package com.vision.modules.surveillance.module_actions_executor.handlers.send_request;


import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestDeliveredCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestErrorCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestResponseCallback;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.common.services.surveillance.data.requests.types.SurveillanceServiceRequestTypes;
import com.vision.modules.modules_common.data.error.ModuleError;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.modules.modules_common.interfaces.response_handler.ResponseHandler;
import com.vision.modules.surveillance.module_actions.payloads.SurveillanceJSActionsPayloads;
import com.vision.modules.surveillance.module_actions.payloads.payloads.send_request.SendRequestPayload;
import com.vision.modules.surveillance.module_actions_executor.handlers.send_request.response_handlers.IsDeviceAliveResponseHandler;
import com.vision.modules.surveillance.module_actions_executor.handlers.send_request.response_handlers.StartRecognizePersonResponseHandler;
import com.vision.modules.surveillance.module_actions_executor.handlers.send_request.response_handlers.StopRecognizePersonResponseHandler;
import com.vision.modules.surveillance.module_actions_executor.handlers.send_request.response_handlers.TakeBackCameraImageResponseHandler;
import com.vision.modules.surveillance.module_actions_executor.handlers.send_request.response_handlers.TakeFrontCameraImageResponseHandler;
import com.vision.modules.surveillance.module_actions_executor.handlers.send_request.response_handlers.TestRequestWithPayloadResponseHandler;
import com.vision.modules.surveillance.module_actions_executor.handlers.send_request.response_handlers.ToggleDetectDeviceMovementResponseHandler;
import com.vision.modules.surveillance.module_errors.SurveillanceModuleErrors;
import com.vision.modules.surveillance.module_events.payloads.SurveillanceEventsJSPayloads;
import com.vision.modules.surveillance.module_events.types.SurveillanceEventTypes;

import java.util.HashMap;
import java.util.Map;

public class SendRequestHandler implements JSActionHandler {
    private final String ACTION_PAYLOAD = "payload";

    private Map<String, ResponseHandler> mRequestResponseHandlers;

    public SendRequestHandler() {
        mRequestResponseHandlers = new HashMap<>();
        mRequestResponseHandlers.put(SurveillanceServiceRequestTypes.TEST_REQUEST_WITH_PAYLOAD, new TestRequestWithPayloadResponseHandler());
        mRequestResponseHandlers.put(SurveillanceServiceRequestTypes.IS_DEVICE_ALIVE, new IsDeviceAliveResponseHandler());
        mRequestResponseHandlers.put(SurveillanceServiceRequestTypes.TAKE_BACK_CAMERA_IMAGE, new TakeBackCameraImageResponseHandler());
        mRequestResponseHandlers.put(SurveillanceServiceRequestTypes.TAKE_FRONT_CAMERA_IMAGE, new TakeFrontCameraImageResponseHandler());
        mRequestResponseHandlers.put(SurveillanceServiceRequestTypes.TOGGLE_DETECT_DEVICE_MOVEMENT, new ToggleDetectDeviceMovementResponseHandler());
        mRequestResponseHandlers.put(SurveillanceServiceRequestTypes.START_RECOGNIZE_PERSON, new StartRecognizePersonResponseHandler());
        mRequestResponseHandlers.put(SurveillanceServiceRequestTypes.STOP_RECOGNIZE_PERSON, new StopRecognizePersonResponseHandler());
    }

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

        OnRequestDeliveredCallback onDeliveredCallback = () -> {
            Log.d("tag", "SendRequestHandler->onDeliveredCallback()");

            context
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(
                            SurveillanceEventTypes.REQUEST_DELIVERED,
                            SurveillanceEventsJSPayloads.requestDelivered(request.id())
                    );
        };
        OnRequestResponseCallback onResponseCallback = response -> {
            Log.d("tag", "SendRequestHandler->onResponseCallback()");

            if (response != null) {
                handleResponse(context, requestType, response);
            } else {
                Log.d("tag", "SendRequestHandler->onResponseCallback(): RESPONSE_IS_NULL");
            }
        };
        OnRequestErrorCallback onErrorCallback = error -> {
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

    private void handleResponse(ReactApplicationContext context, String requestType, ServiceResponse response) {
        ResponseHandler responseHandler = mRequestResponseHandlers.get(requestType);
        if (responseHandler != null) {
            responseHandler.handle(context, response);
        } else {
            Log.d("tag", "SendRequestHandler->processResponse()->UNKNOWN_REQUEST_TYPE: " + requestType);
        }
    }
}
