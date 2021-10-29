package com.vision.services.surveillance.data.service_errors;


import com.vision.common.data.service_error.ServiceError;

public class SurveillanceServiceErrors {
    public static ServiceError commonServiceError() {
        return new ServiceError("0", "COMMON_SERVICE_ERROR");
    }

    public static ServiceError requestFailure() {
        return new ServiceError("1", "REQUEST_FAILURE");
    }

    public static ServiceError requestTimeout() {
        return new ServiceError("2", "REQUEST_TIMEOUT");
    }

    // === AuthServiceErrors ===
    public static ServiceError firebaseFailure() {
        return new ServiceError("3", "FIREBASE_FAILURE");
    }

    public static ServiceError groupAlreadyExist() {
        return new ServiceError("4", "GROUP_ALREADY_EXIST");
    }

    public static ServiceError groupNotExist() {
        return new ServiceError("5", "GROUP_NOT_EXIST");
    }

    public static ServiceError incorrectGroupPassword() {
        return new ServiceError("6", "INCORRECT_GROUP_PASSWORD");
    }

    public static ServiceError deviceNameAlreadyExist() {
        return new ServiceError("7", "DEVICE_NAME_ALREADY_EXIST");
    }

    public static ServiceError deviceNameNotExist() {
        return new ServiceError("8", "DEVICE_NAME_NOT_EXIST");
    }

    public static ServiceError deviceAlreadyLoggedIn() {
        return new ServiceError("9", "DEVICE_ALREADY_LOGGED_IN");
    }
    // ===========================

    // === CameraServiceErrors ===
    public static ServiceError badCameraPreviewImageData() {
        return new ServiceError("10", "BAD_CAMERA_PREVIEW_IMAGE_DATA");
    }
    // ===========================
}
