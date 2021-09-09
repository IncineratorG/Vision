package com.vision.common.services.surveillance.data.request_handlers.unknown;


import android.content.Context;
import android.util.Log;

import com.vision.common.services.surveillance.data.request.Request;
import com.vision.common.services.surveillance.data.request_handler.RequestHandler;

public class UnknownRequestHandler implements RequestHandler {
    @Override
    public void handle(Context context, Request request) {
        Log.d("tag", "UnknownRequestHandler->handle(): " + request.type());
    }
}
