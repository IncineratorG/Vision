package com.vision.common.services.firebase_messaging.data;


import com.vision.common.data.service_error.ServiceError;

public class FBSMessagingServiceErrors {
    public static ServiceError firebaseFailure() {
        return new ServiceError("0", "FIREBASE_FAILURE");
    }
}
