package com.vision.modules.surveillance.module_errors;

import com.vision.modules.modules_common.data.error.ModuleError;

public class SurveillanceModuleErrors {
    public static ModuleError badAction() {
        return new ModuleError("1", "BAD_ACTION");
    }

    public static ModuleError badActionType() {
        return new ModuleError("2", "BAD_ACTION_TYPE");
    }

    public static ModuleError unknownActionType() {
        return new ModuleError("3", "UNKNOWN_ACTION_TYPE");
    }

    public static ModuleError badPayload() {
        return new ModuleError("4", "BAD_PAYLOAD");
    }

    public static ModuleError emptyGroupName() {
        return new ModuleError("5", "EMPTY_GROUP_NAME");
    }

    public static ModuleError emptyGroupPassword() {
        return new ModuleError("6", "EMPTY_GROUP_PASSWORD");
    }

    public static ModuleError emptyDeviceName() {
        return new ModuleError("7", "EMPTY_DEVICE_NAME");
    }

    public static ModuleError groupNotExist() {
        return new ModuleError("8", "GROUP_NOT_EXIST");
    }

    public static ModuleError getDevicesInGroupFirebaseFailure() {
        return new ModuleError("9", "GET_DEVICES_IN_GROUP_FIREBASE_FAILURE");
    }

    public static ModuleError serviceNotInitialized() {
        return new ModuleError("10", "SERVICE_NOT_INITIALIZED");
    }

    public static ModuleError generalRequestCallbackError() {
        return new ModuleError("11", "GENERAL_REQUEST_CALLBACK_ERROR");
    }

    public static ModuleError startServiceFirebaseFailure() {
        return new ModuleError("12", "START_SERVICE_FIREBASE_FAILURE");
    }

    public static ModuleError badRemoteDeviceInfo() {
        return new ModuleError("13", "BAD_REMOTE_DEVICE_INFO");
    }

    public static ModuleError stopServiceFirebaseFailure() {
        return new ModuleError("14", "STOP_SERVICE_FIREBASE_FAILURE");
    }
}
