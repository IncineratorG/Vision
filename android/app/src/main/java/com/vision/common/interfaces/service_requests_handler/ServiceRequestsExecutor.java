package com.vision.common.interfaces.service_requests_handler;


import android.content.Context;

import java.util.Map;

public interface ServiceRequestsExecutor {
    void execute(Context context, String stringifiedRequest, Map<String, Object> params);
}
