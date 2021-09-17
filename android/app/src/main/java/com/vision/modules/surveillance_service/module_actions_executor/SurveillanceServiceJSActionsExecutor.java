package com.vision.modules.surveillance_service.module_actions_executor;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.modules.modules_common.data.error.ModuleError;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.modules.modules_common.interfaces.js_actions_executor.JSActionsExecutor;
import com.vision.modules.surveillance_service.module_actions.types.SurveillanceServiceJSActionTypes;
import com.vision.modules.surveillance_service.module_actions_executor.handlers.GetDevicesInGroupHandler;
import com.vision.modules.surveillance_service.module_actions_executor.handlers.IsServiceRunningHandler;
import com.vision.modules.surveillance_service.module_actions_executor.handlers.SendRequestHandler;
import com.vision.modules.surveillance_service.module_actions_executor.handlers.StartServiceHandler;
import com.vision.modules.surveillance_service.module_actions_executor.handlers.StopServiceHandler;
import com.vision.modules.surveillance_service.module_actions_executor.handlers.TestRequestHandler;
import com.vision.modules.surveillance_service.module_errors.SurveillanceServiceModuleErrors;

import java.util.HashMap;
import java.util.Map;

public class SurveillanceServiceJSActionsExecutor implements JSActionsExecutor {
    private static final String ACTION_TYPE = "type";
    private Map<String, JSActionHandler> mHandlers;

    public SurveillanceServiceJSActionsExecutor() {
        mHandlers = new HashMap<>();
        // ===
        mHandlers.put(SurveillanceServiceJSActionTypes.TEST_REQUEST, new TestRequestHandler());
        mHandlers.put(SurveillanceServiceJSActionTypes.SEND_REQUEST, new SendRequestHandler());
        // ===
        mHandlers.put(SurveillanceServiceJSActionTypes.IS_RUNNING, new IsServiceRunningHandler());
        mHandlers.put(SurveillanceServiceJSActionTypes.START_SERVICE, new StartServiceHandler());
        mHandlers.put(SurveillanceServiceJSActionTypes.STOP_SERVICE, new StopServiceHandler());
        mHandlers.put(SurveillanceServiceJSActionTypes.GET_DEVICES_IN_GROUP, new GetDevicesInGroupHandler());
    }

    @Override
    public void execute(ReactApplicationContext context, ReadableMap action, Promise result) {
        if (action == null) {
            ModuleError error = SurveillanceServiceModuleErrors.badAction();
            result.reject(error.code(), error.message());
            return;
        }

        final String type = action.getString(ACTION_TYPE);
        if (type == null) {
            ModuleError error = SurveillanceServiceModuleErrors.badActionType();
            result.reject(error.code(), error.message());
            return;
        }

        JSActionHandler actionHandler = mHandlers.get(type);
        if (actionHandler != null) {
            actionHandler.handle(context, action, result);
        } else {
            ModuleError error = SurveillanceServiceModuleErrors.unknownActionType();
            result.reject(error.code(), error.message());
        }
    }
}
