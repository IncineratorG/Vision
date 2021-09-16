package com.vision.common.services.surveillance.data.responses_handler.firebase;


import android.content.Context;
import android.util.Log;

import com.vision.common.interfaces.service_responses_handler.ServiceResponsesHandler;

import java.util.Map;

public class FBSResponsesHandler implements ServiceResponsesHandler {
    public FBSResponsesHandler() {

    }

    @Override
    public void handle(Context context, String stringifiedResponse, Map<String, Object> params) {
        Log.d("tag", "FBSResponsesHandler->handle(): " + stringifiedResponse);
    }
}
