package com.vision.common.interfaces.service_responses_executor;


import android.content.Context;

import com.vision.common.data.service_request_callbacks.ServiceRequestCallbacks;

import java.util.Map;
import java.util.Timer;

public interface ServiceResponsesExecutor {
    void execute(Context context,
                 String stringifiedResponse,
                 Map<String, ServiceRequestCallbacks> requestCallbacksMap,
                 Map<String, Timer> requestTimeoutsMap,
                 Map<String, Object> params);
}
