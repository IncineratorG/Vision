package com.vision.rn_modules.auth.module_errors;

import com.vision.rn_modules.modules_common.data.error.ModuleError;

public class AuthModuleErrors {
    public static ModuleError commonModuleServiceError() {
        return new ModuleError("0", "COMMON_MODULE_SERVICE_ERROR");
    }

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

    public static ModuleError groupAlreadyExist() {
        return new ModuleError("9", "GROUP_ALREADY_EXIST");
    }

    public static ModuleError createGroupWithDeviceFirebaseFailure() {
        return new ModuleError("10", "CREATE_GROUP_WITH_DEVICE_FIREBASE_FAILURE");
    }

    public static ModuleError registerDeviceInGroupFirebaseFailure() {
        return new ModuleError("11", "REGISTER_DEVICE_IN_GROUP_FIREBASE_FAILURE");
    }

    public static ModuleError incorrectGroupPassword() {
        return new ModuleError("12", "INCORRECT_GROUP_PASSWORD");
    }

    public static ModuleError deviceNameAlreadyExist() {
        return new ModuleError("13", "DEVICE_NAME_ALREADY_EXIST");
    }

    public static ModuleError loginDeviceInGroupFirebaseFailure() {
        return new ModuleError("14", "LOGIN_DEVICE_IN_GROUP_FIREBASE_FAILURE");
    }

    public static ModuleError deviceAlreadyLoggedIn() {
        return new ModuleError("15", "DEVICE_ALREADY_LOGGED_IN");
    }

    public static ModuleError deviceNameNotExist() {
        return new ModuleError("16", "DEVICE_NAME_NOT_EXIST");
    }

    public static ModuleError firebaseFailure() {
        return new ModuleError("17", "FIREBASE_FAILURE");
    }
}
