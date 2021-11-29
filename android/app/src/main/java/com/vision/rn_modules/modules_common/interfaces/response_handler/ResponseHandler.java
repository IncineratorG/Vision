package com.vision.rn_modules.modules_common.interfaces.response_handler;


import com.facebook.react.bridge.ReactApplicationContext;
import com.vision.common.data.service_response.ServiceResponse;

public interface ResponseHandler {
    void handle(ReactApplicationContext context, ServiceResponse response);
}
