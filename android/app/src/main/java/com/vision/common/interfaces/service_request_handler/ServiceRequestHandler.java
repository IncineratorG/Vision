package com.vision.common.interfaces.service_request_handler;


import android.content.Context;

import com.vision.common.data.service_request.ServiceRequest;

public interface ServiceRequestHandler {
    void handle(Context context, ServiceRequest request);
}
