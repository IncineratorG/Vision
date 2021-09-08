package com.vision.modules.surveillance_foreground_service.module_actions_executor;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.modules.modules_common.data.error.ModuleError;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.modules.modules_common.interfaces.js_actions_executor.JSActionsExecutor;
import com.vision.modules.surveillance_foreground_service.module_actions.types.SurveillanceForegroundServiceJSActionTypes;
import com.vision.modules.surveillance_foreground_service.module_actions_executor.handlers.IsServiceRunningHandler;
import com.vision.modules.surveillance_foreground_service.module_actions_executor.handlers.StartServiceHandler;
import com.vision.modules.surveillance_foreground_service.module_actions_executor.handlers.StopServiceHandler;
import com.vision.modules.surveillance_foreground_service.module_actions_executor.handlers.TestRequestHandler;
import com.vision.modules.surveillance_foreground_service.module_errors.SurveillanceForegroundServiceModuleErrors;

import java.util.HashMap;
import java.util.Map;

public class SurveillanceForegroundServiceJSActionsExecutor implements JSActionsExecutor {
    private static final String ACTION_TYPE = "type";
    private Map<String, JSActionHandler> mHandlers;

    public SurveillanceForegroundServiceJSActionsExecutor() {
        mHandlers = new HashMap<>();
        mHandlers.put(SurveillanceForegroundServiceJSActionTypes.IS_RUNNING, new IsServiceRunningHandler());
        mHandlers.put(SurveillanceForegroundServiceJSActionTypes.START_SERVICE, new StartServiceHandler());
        mHandlers.put(SurveillanceForegroundServiceJSActionTypes.STOP_SERVICE, new StopServiceHandler());
        mHandlers.put(SurveillanceForegroundServiceJSActionTypes.TEST_REQUEST, new TestRequestHandler());
    }

    @Override
    public void execute(ReactApplicationContext context, ReadableMap action, Promise result) {
        if (action == null) {
            ModuleError error = SurveillanceForegroundServiceModuleErrors.badAction();
            result.reject(error.code(), error.message());
            return;
        }

        final String type = action.getString(ACTION_TYPE);
        if (type == null) {
            ModuleError error = SurveillanceForegroundServiceModuleErrors.badActionType();
            result.reject(error.code(), error.message());
            return;
        }

        JSActionHandler actionHandler = mHandlers.get(type);
        if (actionHandler != null) {
            actionHandler.handle(context, action, result);
        } else {
            ModuleError error = SurveillanceForegroundServiceModuleErrors.unknownActionType();
            result.reject(error.code(), error.message());
        }
    }
}
