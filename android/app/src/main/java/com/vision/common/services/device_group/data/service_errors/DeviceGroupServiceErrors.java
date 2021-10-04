package com.vision.common.services.device_group.data.service_errors;


import com.vision.common.data.service_error.ServiceError;

public class DeviceGroupServiceErrors {
    public static ServiceError firebaseFailure() {
        return new ServiceError("0", "FIREBASE_FAILURE");
    }
}
