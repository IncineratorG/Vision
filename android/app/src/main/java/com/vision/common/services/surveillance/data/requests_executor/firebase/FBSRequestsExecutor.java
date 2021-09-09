package com.vision.common.services.surveillance.data.requests_executor.firebase;


import android.content.Context;
import android.util.Log;

import com.vision.common.services.surveillance.data.request.Request;
import com.vision.common.services.surveillance.data.request_handler.ServiceRequestHandler;
import com.vision.common.services.surveillance.data.request_handlers.test.TestRequestServiceHandler;
import com.vision.common.services.surveillance.data.request_handlers.unknown.UnknownRequestServiceHandler;
import com.vision.common.services.surveillance.data.requests_executor.RequestsExecutor;
import com.vision.common.services.surveillance.data.service_request_types.SurveillanceServiceRequestTypes;

import java.util.HashMap;
import java.util.Map;

public class FBSRequestsExecutor implements RequestsExecutor {
    private final String UNKNOWN_REQUEST_HANDLER_KEY = "unknown";
    private Map<String, ServiceRequestHandler> mHandlers;

    public FBSRequestsExecutor() {
        mHandlers = new HashMap<>();
        mHandlers.put(UNKNOWN_REQUEST_HANDLER_KEY, new UnknownRequestServiceHandler());
        mHandlers.put(SurveillanceServiceRequestTypes.TEST_REQUEST, new TestRequestServiceHandler());
    }

    @Override
    public void execute(Context context, String stringifiedRequest, Map<String, Object> params) {
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

        Request request = new Request(stringifiedRequest);

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
