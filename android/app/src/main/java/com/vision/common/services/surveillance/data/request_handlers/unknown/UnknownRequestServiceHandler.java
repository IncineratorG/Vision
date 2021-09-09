package com.vision.common.services.surveillance.data.request_handlers.unknown;


import android.content.Context;
import android.util.Log;

import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.services.surveillance.data.request.Request;
import com.vision.common.services.surveillance.data.request_handler.ServiceRequestHandler;

import java.util.List;

public class UnknownRequestServiceHandler implements ServiceRequestHandler {
    @Override
    public void handle(Context context, Request request) {
        Log.d("tag", "UnknownRequestServiceHandler->handle(): " + request.type());

        List<String> requestsPath = FBSPathsService.get().currentRequestsPath();
        if (request.key() != null) {
            FBSService.get().removeValueFromList(requestsPath, request.key());
        } else {
            Log.d("tag", "TestRequestServiceHandler->handle()->BAD_REQUEST_KEY: " + request.stringify());
        }
    }
}
