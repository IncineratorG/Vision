package com.vision.modules.app_settings.module_errors;


import com.vision.modules.modules_common.data.error.ModuleError;

public class AppSettingsModuleErrors {
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
}
