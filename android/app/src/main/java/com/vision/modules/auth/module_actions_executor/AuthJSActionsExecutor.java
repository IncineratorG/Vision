package com.vision.modules.auth.module_actions_executor;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.modules.auth.module_actions.types.AuthJSActionTypes;
import com.vision.modules.auth.module_actions_executor.handlers.CreateDeviceGroupHandler;
import com.vision.modules.auth.module_actions_executor.handlers.IsDeviceGroupExistHandler;
import com.vision.modules.auth.module_actions_executor.handlers.IsDeviceNameAvailableHandler;
import com.vision.modules.auth.module_actions_executor.handlers.LoginDeviceInGroupHandler;
import com.vision.modules.auth.module_actions_executor.handlers.RegisterDeviceInGroupHandler;
import com.vision.modules.auth.module_errors.AuthModuleErrors;
import com.vision.modules.modules_common.data.error.ModuleError;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.modules.modules_common.interfaces.js_actions_executor.JSActionsExecutor;

import java.util.HashMap;
import java.util.Map;

public class AuthJSActionsExecutor implements JSActionsExecutor {
    private static final String ACTION_TYPE = "type";
    private Map<String, JSActionHandler> mHandlers;

    public AuthJSActionsExecutor() {
        mHandlers = new HashMap<>();
        mHandlers.put(AuthJSActionTypes.REGISTER_DEVICE_IN_GROUP, new RegisterDeviceInGroupHandler());
        mHandlers.put(AuthJSActionTypes.IS_DEVICE_GROUP_EXIST, new IsDeviceGroupExistHandler());
        mHandlers.put(AuthJSActionTypes.IS_DEVICE_NAME_AVAILABLE, new IsDeviceNameAvailableHandler());
        mHandlers.put(AuthJSActionTypes.CREATE_DEVICE_GROUP, new CreateDeviceGroupHandler());
        mHandlers.put(AuthJSActionTypes.LOGIN_DEVICE_IN_GROUP, new LoginDeviceInGroupHandler());
    }

    @Override
    public void execute(ReactApplicationContext context, ReadableMap action, Promise result) {
        if (action == null) {
            ModuleError error = AuthModuleErrors.badAction();
            result.reject(error.code(), error.message());
            return;
        }

        final String type = action.getString(ACTION_TYPE);
        if (type == null) {
            ModuleError error = AuthModuleErrors.badActionType();
            result.reject(error.code(), error.message());
            return;
        }

        JSActionHandler actionHandler = mHandlers.get(type);
        if (actionHandler != null) {
            actionHandler.handle(context, action, result);
        } else {
            ModuleError error = AuthModuleErrors.unknownActionType();
            result.reject(error.code(), error.message());
        }
    }
}
