package com.vision.common.interfaces.service_responses_handler;


import android.content.Context;

import java.util.Map;

public interface ServiceResponsesHandler {
    void handle(Context context, String stringifiedResponse, Map<String, Object> params);
}
