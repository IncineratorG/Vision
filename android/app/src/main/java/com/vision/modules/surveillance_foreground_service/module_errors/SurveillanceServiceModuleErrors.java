package com.vision.modules.surveillance_foreground_service.module_errors;

import com.vision.modules.modules_common.data.error.ModuleError;

public class SurveillanceServiceModuleErrors {
    public static ModuleError badAction() {
        return new ModuleError("1", "BAD_ACTION");
    }

    public static ModuleError badActionType() {
        return new ModuleError("2", "BAD_ACTION_TYPE");
    }

    public static ModuleError unknownActionType() {
        return new ModuleError("3", "UNKNOWN_ACTION_TYPE");
    }
}
