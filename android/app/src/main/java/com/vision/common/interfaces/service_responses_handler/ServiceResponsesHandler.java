package com.vision.common.interfaces.service_responses_handler;


import android.content.Context;

import com.vision.common.data.service_request_callbacks.ServiceRequestCallbacks;

import java.util.Map;

public interface ServiceResponsesHandler {
    void handle(Context context,
                String stringifiedResponse,
                Map<String, ServiceRequestCallbacks> requestCallbacksMap,
                Map<String, Object> params);
}
