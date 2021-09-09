package com.vision.common.services.surveillance.data.request_sender;


import com.vision.common.services.surveillance.data.service_error.ServiceError;

public interface OnErrorCallback {
    void handle(ServiceError error);
}
