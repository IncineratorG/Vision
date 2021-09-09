package com.vision.common.services.surveillance.data.requests_executor;


import android.content.Context;

import java.util.Map;

public interface RequestsExecutor {
    void execute(Context context, String stringifiedRequest, Map<String, Object> params);
}
