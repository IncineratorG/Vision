package com.vision.common.services.surveillance.data.requests_handler.firebase;


import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.interfaces.service_request_handler.ServiceRequestHandler;
import com.vision.common.services.surveillance.data.request_handlers.test.TestRequestServiceHandler;
import com.vision.common.services.surveillance.data.request_handlers.unknown.UnknownRequestServiceHandler;
import com.vision.common.interfaces.service_requests_handler.ServiceRequestsHandler;
import com.vision.common.services.surveillance.data.service_request_types.SurveillanceServiceRequestTypes;

import java.util.HashMap;
import java.util.Map;

public class FBSRequestsHandler implements ServiceRequestsHandler {
    private final String UNKNOWN_REQUEST_HANDLER_KEY = "unknown";
    private Map<String, ServiceRequestHandler> mHandlers;

    public FBSRequestsHandler() {
        mHandlers = new HashMap<>();
        mHandlers.put(UNKNOWN_REQUEST_HANDLER_KEY, new UnknownRequestServiceHandler());
        mHandlers.put(SurveillanceServiceRequestTypes.TEST_REQUEST, new TestRequestServiceHandler());
    }

    @Override
    public void handle(Context context, String stringifiedRequest, Map<String, Object> params) {
        Log.d("tag", "FirebaseRequestsExecutor->execute(): " + stringifiedRequest + " - " + (context == null));

        if (context == null) {
            Log.d("tag", "FirebaseRequestsExecutor->execute(): CONTEXT_IS_NULL");
            return;
        }

        if (stringifiedRequest == null) {
            Log.d("tag", "FirebaseRequestsExecutor->execute(): STRINGIFIED_REQUEST_IS_NULL");
            return;
        }

        if (params == null) {
            Log.d("tag", "FirebaseRequestsExecutor->execute(): PARAMS_IS_NULL");
            return;
        }

        if (!params.containsKey("requestKey")) {
            Log.d("tag", "FirebaseRequestsExecutor->execute(): PARAMS_HAS_NO_KEY");
        }

        ServiceRequest request = new ServiceRequest(stringifiedRequest);

        String requestKey = (String) params.get("requestKey");
        if (requestKey != null) {
            request.setKey(requestKey);
        } else {
            Log.d("tag", "FirebaseRequestsExecutor->execute(): BAD_REQUEST_KEY");
        }

        if (mHandlers.containsKey(request.type())) {
            mHandlers.get(request.type()).handle(context, request);
        } else {
            mHandlers.get(UNKNOWN_REQUEST_HANDLER_KEY).handle(context, request);
        }
    }
}
