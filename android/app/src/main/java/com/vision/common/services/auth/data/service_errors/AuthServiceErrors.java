package com.vision.common.services.auth.data.service_errors;


import com.vision.common.data.service_error.ServiceError;

public class AuthServiceErrors {
    public static ServiceError firebaseFailure() {
        return new ServiceError("0", "FIREBASE_FAILURE");
    }

    public static ServiceError groupAlreadyExist() {
        return new ServiceError("1", "GROUP_ALREADY_EXIST");
    }

    public static ServiceError groupNotExist() {
        return new ServiceError("2", "GROUP_NOT_EXIST");
    }

    public static ServiceError incorrectGroupPassword() {
        return new ServiceError("3", "INCORRECT_GROUP_PASSWORD");
    }

    public static ServiceError deviceNameAlreadyExist() {
        return new ServiceError("4", "DEVICE_NAME_ALREADY_EXIST");
    }

    public static ServiceError deviceNameNotExist() {
        return new ServiceError("5", "DEVICE_NAME_NOT_EXIST");
    }

    public static ServiceError deviceAlreadyLoggedIn() {
        return new ServiceError("6", "DEVICE_ALREADY_LOGGED_IN");
    }
}
