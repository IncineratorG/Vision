package com.vision.common.interfaces.service_request_sender.callbacks;


import com.vision.common.data.service_response.ServiceResponse;

public interface OnRequestResponseCallback {
    void handle(ServiceResponse response);
}
