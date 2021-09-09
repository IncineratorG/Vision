package com.vision.modules.surveillance_foreground_service.module_actions_executor.handlers;


import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.common.services.surveillance.data.request.Request;
import com.vision.common.services.surveillance.data.request_sender.OnDeliveredCallback;
import com.vision.common.services.surveillance.data.request_sender.OnErrorCallback;
import com.vision.common.services.surveillance.data.request_sender.OnResponseCallback;
import com.vision.common.services.surveillance.data.response.Response;
import com.vision.common.services.surveillance.data.service_error.ServiceError;
import com.vision.common.services.surveillance.data.service_requests.SurveillanceServiceRequests;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;

public class TestRequestHandler implements JSActionHandler {
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "TestRequestHandler");

        SurveillanceService service = SurveillanceService.get();

        Request testRequest = service.requests().testRequest();

        service.sendRequest(testRequest,
                () -> {
                    Log.d("tag", "TestRequestHandler->handle(): REQUEST_DELIVERED_CALLBACK");
                },
                response -> {
                    Log.d("tag", "TestRequestHandler->handle()->RESPONSE_RECEIVED_CALLBACK)");
                },
                error -> {
                    Log.d("tag", "TestRequestHandler->handle()->ERROR: " + error.code() + " - " + error.message());
                }
        );

        result.resolve(true);
    }
}
