package com.vision.common.services.surveillance.data.responses_handler.firebase;


import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_responses_handler.ServiceResponsesHandler;

import java.util.Map;

public class FBSResponsesHandler implements ServiceResponsesHandler {
    public FBSResponsesHandler() {

    }

    @Override
    public void handle(Context context, String stringifiedResponse, Map<String, Object> params) {
        Log.d("tag", "FBSResponsesHandler->handle(): " + stringifiedResponse + " - " + (context == null));

        if (context == null) {
            Log.d("tag", "FBSResponsesHandler->handle(): CONTEXT_IS_NULL");
            return;
        }

        if (stringifiedResponse == null) {
            Log.d("tag", "FBSResponsesHandler->handle(): STRINGIFIED_RESPONSE_IS_NULL");
            return;
        }

        if (params == null) {
            Log.d("tag", "FBSResponsesHandler->handle(): PARAMS_IS_NULL");
            return;
        }

        if (!params.containsKey("responseKey")) {
            Log.d("tag", "FBSResponsesHandler->handle(): PARAMS_HAS_NO_KEY");
        }

        ServiceResponse response = new ServiceResponse(stringifiedResponse);

        String responseKey = (String) params.get("responseKey");
        if (responseKey != null) {
            response.setKey(responseKey);
        } else {
            Log.d("tag", "FBSResponsesHandler->handle(): BAD_RESPONSE_KEY");
        }
    }
}
