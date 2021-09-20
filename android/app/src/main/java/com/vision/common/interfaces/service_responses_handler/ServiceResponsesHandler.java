package com.vision.common.interfaces.service_responses_handler;


import android.content.Context;

import com.vision.common.data.service_request_callbacks.ServiceRequestCallbacks;

import java.util.Map;
import java.util.Timer;

public interface ServiceResponsesHandler {
    void handle(Context context,
                String stringifiedResponse,
                Map<String, ServiceRequestCallbacks> requestCallbacksMap,
                Map<String, Timer> requestTimeoutsMap,
                Map<String, Object> params);
}
