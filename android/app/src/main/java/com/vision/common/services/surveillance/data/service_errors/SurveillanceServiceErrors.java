package com.vision.common.services.surveillance.data.service_errors;


import com.vision.common.data.service_error.ServiceError;

public class SurveillanceServiceErrors {
    ServiceError badRequest() {
        return new ServiceError("1", "BAD_REQUEST");
    }
}
