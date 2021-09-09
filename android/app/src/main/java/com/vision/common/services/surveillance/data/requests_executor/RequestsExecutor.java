package com.vision.common.services.surveillance.data.requests_executor;


import android.content.Context;

public interface RequestsExecutor {
    void execute(Context context, String stringifiedRequest);
}
