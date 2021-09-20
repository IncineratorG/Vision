package com.vision.common.services.surveillance.data.service_errors;


import com.vision.common.data.service_error.ServiceError;

public class SurveillanceServiceErrors {
    public static ServiceError requestFailure() {
        return new ServiceError("1", "REQUEST_FAILURE");
    }

    public static ServiceError requestTimeout() {
        return new ServiceError("2", "REQUEST_TIMEOUT");
    }
}
