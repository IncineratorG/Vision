package com.vision.modules.modules_common.interfaces.response_handler;


import com.facebook.react.bridge.ReactApplicationContext;
import com.vision.common.data.service_response.ServiceResponse;

public interface ResponseHandler {
    void handle(ReactApplicationContext context, ServiceResponse response);
}
