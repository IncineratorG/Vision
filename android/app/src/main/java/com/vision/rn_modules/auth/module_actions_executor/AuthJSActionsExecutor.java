package com.vision.rn_modules.auth.module_actions_executor;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.rn_modules.auth.module_actions.types.AuthJSActionTypes;
import com.vision.rn_modules.auth.module_actions_executor.handlers.CreateGroupWithDeviceHandler;
import com.vision.rn_modules.auth.module_actions_executor.handlers.GetCurrentAuthenticationDataHandler;
import com.vision.rn_modules.auth.module_actions_executor.handlers.IsDeviceGroupExistHandler;
import com.vision.rn_modules.auth.module_actions_executor.handlers.IsDeviceNameAvailableHandler;
import com.vision.rn_modules.auth.module_actions_executor.handlers.IsLoggedInHandler;
import com.vision.rn_modules.auth.module_actions_executor.handlers.LoginDeviceInGroupHandler;
import com.vision.rn_modules.auth.module_actions_executor.handlers.LogoutDeviceFromGroupHandler;
import com.vision.rn_modules.auth.module_actions_executor.handlers.RegisterDeviceInGroupHandler;
import com.vision.rn_modules.auth.module_errors.AuthModuleErrors;
import com.vision.rn_modules.modules_common.data.error.ModuleError;
import com.vision.rn_modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.rn_modules.modules_common.interfaces.js_actions_executor.JSActionsExecutor;

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
        mHandlers.put(AuthJSActionTypes.CREATE_GROUP_WITH_DEVICE, new CreateGroupWithDeviceHandler());
        mHandlers.put(AuthJSActionTypes.IS_LOGGED_IN, new IsLoggedInHandler());
        mHandlers.put(AuthJSActionTypes.LOGIN_DEVICE_IN_GROUP, new LoginDeviceInGroupHandler());
        mHandlers.put(AuthJSActionTypes.LOGOUT_DEVICE_FROM_GROUP, new LogoutDeviceFromGroupHandler());
        mHandlers.put(AuthJSActionTypes.GET_CURRENT_AUTHENTICATION_DATA, new GetCurrentAuthenticationDataHandler());
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
