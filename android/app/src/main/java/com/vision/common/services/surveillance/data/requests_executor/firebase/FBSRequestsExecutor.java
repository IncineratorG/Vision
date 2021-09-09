package com.vision.common.services.surveillance.data.requests_executor.firebase;


import android.content.Context;
import android.util.Log;

import com.vision.common.services.surveillance.data.request.Request;
import com.vision.common.services.surveillance.data.request_handler.RequestHandler;
import com.vision.common.services.surveillance.data.request_handlers.unknown.UnknownRequestHandler;
import com.vision.common.services.surveillance.data.requests_executor.RequestsExecutor;

import java.util.HashMap;
import java.util.Map;

public class FBSRequestsExecutor implements RequestsExecutor {
    private final String UNKNOWN_REQUEST_HANDLER_KEY = "unknown";
    private Map<String, RequestHandler> mHandlers;

    public FBSRequestsExecutor() {
        mHandlers = new HashMap<>();
        mHandlers.put(UNKNOWN_REQUEST_HANDLER_KEY, new UnknownRequestHandler());
    }

    @Override
    public void execute(Context context, String stringifiedRequest) {
        Log.d("tag", "FirebaseRequestsExecutor->execute(): " + stringifiedRequest + " - " + (context == null));

        if (context == null) {
            Log.d("tag", "FirebaseRequestsExecutor->execute(): CONTEXT_IS_NULL");
            return;
        }

        if (stringifiedRequest == null) {
            Log.d("tag", "FirebaseRequestsExecutor->execute(): STRINGIFIED_REQUEST_IS_NULL");
            return;
        }

        Request request = new Request(stringifiedRequest);
        if (mHandlers.containsKey(request.type())) {
            mHandlers.get(request.type()).handle(context, request);
        } else {
            mHandlers.get(UNKNOWN_REQUEST_HANDLER_KEY).handle(context, request);
        }
    }
}
