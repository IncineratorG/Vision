package com.vision.rn_modules.auth.module_actions_executor.handlers;


import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.services.surveillance.SurveillanceService;
import com.vision.rn_modules.auth.module_errors.mapper.AuthModuleErrorsMapper;
import com.vision.rn_modules.modules_common.data.error.ModuleError;
import com.vision.rn_modules.modules_common.interfaces.js_action_handler.JSActionHandler;

public class LogoutDeviceFromGroupHandler implements JSActionHandler {
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "LogoutDeviceFromGroupHandler");

        SurveillanceService surveillanceService = SurveillanceService.get();
        surveillanceService.logoutDeviceFromGroup(
                context,
                (data) -> {
                    result.resolve(true);
                },
                (error) -> {
                    ModuleError moduleError = AuthModuleErrorsMapper.mapToModuleError(
                            AuthModuleErrorsMapper.SURVEILLANCE_SERVICE_TYPE,
                            error
                    );
                    result.reject(moduleError.code(), moduleError.message());
                }
        );
    }
}
