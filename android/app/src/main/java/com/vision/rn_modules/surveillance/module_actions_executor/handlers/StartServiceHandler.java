package com.vision.rn_modules.surveillance.module_actions_executor.handlers;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.rn_modules.modules_common.data.error.ModuleError;
import com.vision.rn_modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.rn_modules.surveillance.module_errors.SurveillanceModuleErrors;
import com.vision.rn_modules.surveillance.module_errors.mapper.SurveillanceModuleErrorsMapper;

public class StartServiceHandler implements JSActionHandler {
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "StartServiceHandler");

        SurveillanceService surveillanceService = SurveillanceService.get();
        if (!surveillanceService.isInitialized()) {
            ModuleError error = SurveillanceModuleErrors.serviceNotInitialized();
            result.reject(error.code(), error.message());
            return;
        }

        surveillanceService.startForegroundService(
                context,
                (data) -> result.resolve(true),
                (error) -> {
                    ModuleError moduleError = SurveillanceModuleErrorsMapper.mapToModuleError(
                            SurveillanceModuleErrorsMapper.SURVEILLANCE_SERVICE_TYPE,
                            error
                    );
                    result.reject(moduleError.code(), moduleError.message());
                }
        );
    }
}
