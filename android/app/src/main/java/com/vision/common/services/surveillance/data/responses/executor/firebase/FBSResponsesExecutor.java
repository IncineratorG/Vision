package com.vision.common.services.surveillance.data.responses.executor.firebase;


import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_request_callbacks.ServiceRequestCallbacks;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_request_sender.callbacks.OnResponseCallback;
import com.vision.common.interfaces.service_responses_handler.ServiceResponsesExecutor;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.services.surveillance.SurveillanceService;

import java.util.List;
import java.util.Map;
import java.util.Timer;

public class FBSResponsesExecutor implements ServiceResponsesExecutor {
    public FBSResponsesExecutor() {

    }

    @Override
    public void execute(Context context,
                        String stringifiedResponse,
                        Map<String, ServiceRequestCallbacks> requestCallbacksMap,
                        Map<String, Timer> requestTimeoutsMap,
                        Map<String, Object> params) {
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

        // ===
        String requestId = response.requestId();

        ServiceRequestCallbacks requestCallbacks = requestCallbacksMap.get(requestId);
        if (requestCallbacks != null) {
            OnResponseCallback responseCallback = requestCallbacks.responseCallback();
            if (responseCallback != null) {
                responseCallback.handle(response);
            } else {
                Log.d("tag", "FBSResponsesHandler->handle(): RESPONSE_CALLBACK_IS_NULL");
            }
        } else {
            Log.d("tag", "FBSResponsesHandler->handle(): REQUEST_CALLBACKS_IS_NULL");
        }
        requestCallbacksMap.remove(requestId);

        Timer requestTimeout = requestTimeoutsMap.get(requestId);
        if (requestTimeout != null) {
            requestTimeout.cancel();
        }
        requestTimeoutsMap.remove(requestId);
        // ===

        SurveillanceService surveillanceService = SurveillanceService.get();

        String currentGroupName = surveillanceService.currentGroupName();
        String currentGroupPassword = surveillanceService.currentGroupPassword();
        String currentDeviceName = surveillanceService.currentDeviceName();

        List<String> responsesPath = FBSPathsService.get().responsesPath(currentGroupName, currentGroupPassword, currentDeviceName);
        if (response.key() != null) {
            FBSService.get().removeValueFromList(responsesPath, response.key());
        } else {
            Log.d("tag", "FBSResponsesHandler->handle()->BAD_RESPONSE_KEY: " + response.stringify());
        }
    }
}
