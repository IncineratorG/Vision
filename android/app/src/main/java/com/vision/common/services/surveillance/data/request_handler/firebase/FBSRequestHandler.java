package com.vision.common.services.surveillance.data.request_handler.firebase;


import android.util.Log;

import com.vision.common.services.surveillance.data.request.Request;
import com.vision.common.services.surveillance.data.request_handler.RequestHandler;

public class FBSRequestHandler implements RequestHandler {
    @Override
    public void handle(Request request) {
        Log.d("tag", "FBSRequestHandler->handle(): " + request.type());

        switch (request.type()) {
            default: {

            }
        }
    }
}
