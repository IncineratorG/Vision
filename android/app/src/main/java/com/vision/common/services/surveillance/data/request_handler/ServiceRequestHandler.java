package com.vision.common.services.surveillance.data.request_handler;


import android.content.Context;

import com.vision.common.services.surveillance.data.request.Request;

public interface ServiceRequestHandler {
    void handle(Context context, Request request);
}
