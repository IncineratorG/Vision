package com.vision.common.interfaces.service_request_sender.callbacks;


import com.vision.common.data.service_error.ServiceError;

public interface OnErrorCallback {
    void handle(ServiceError error);
}
