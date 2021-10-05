package com.vision.modules.app_settings.module_actions_executor;


import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.modules.app_settings.module_actions.types.AppSettingsJSActionTypes;
import com.vision.modules.app_settings.module_actions_executor.handlers.GetAppSettingsHandler;
import com.vision.modules.app_settings.module_actions_executor.handlers.UpdateAppSettingsHandler;
import com.vision.modules.app_settings.module_errors.AppSettingsModuleErrors;
import com.vision.modules.modules_common.data.error.ModuleError;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.modules.modules_common.interfaces.js_actions_executor.JSActionsExecutor;

import java.util.HashMap;
import java.util.Map;

public class AppSettingsJSActionsExecutor implements JSActionsExecutor {
    private static final String ACTION_TYPE = "type";
    private Map<String, JSActionHandler> mHandlers;

    public AppSettingsJSActionsExecutor() {
        mHandlers = new HashMap<>();
        mHandlers.put(AppSettingsJSActionTypes.GET_APP_SETTINGS, new GetAppSettingsHandler());
        mHandlers.put(AppSettingsJSActionTypes.UPDATE_APP_SETTINGS, new UpdateAppSettingsHandler());
    }

    @Override
    public void execute(ReactApplicationContext context, ReadableMap action, Promise result) {
        if (action == null) {
            ModuleError error = AppSettingsModuleErrors.badAction();
            result.reject(error.code(), error.message());
            return;
        }

        final String type = action.getString(ACTION_TYPE);
        if (type == null) {
            ModuleError error = AppSettingsModuleErrors.badActionType();
            result.reject(error.code(), error.message());
            return;
        }

        JSActionHandler actionHandler = mHandlers.get(type);
        if (actionHandler != null) {
            actionHandler.handle(context, action, result);
        } else {
            ModuleError error = AppSettingsModuleErrors.unknownActionType();
            result.reject(error.code(), error.message());
        }
    }
}
