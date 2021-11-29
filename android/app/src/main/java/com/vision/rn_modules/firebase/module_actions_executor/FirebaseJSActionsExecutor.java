package com.vision.rn_modules.firebase.module_actions_executor;


import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.rn_modules.firebase.module_actions.types.FirebaseJSActionTypes;
import com.vision.rn_modules.firebase.module_actions_executor.handlers.TestActionHandler;
import com.vision.rn_modules.firebase.module_errors.FirebaseModuleErrors;
import com.vision.rn_modules.modules_common.data.error.ModuleError;
import com.vision.rn_modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.rn_modules.modules_common.interfaces.js_actions_executor.JSActionsExecutor;

import java.util.HashMap;
import java.util.Map;

public class FirebaseJSActionsExecutor implements JSActionsExecutor {
    private static final String ACTION_TYPE = "type";
    private Map<String, JSActionHandler> mHandlers;

    public FirebaseJSActionsExecutor() {
        mHandlers = new HashMap<>();
        mHandlers.put(FirebaseJSActionTypes.TEST_ACTION, new TestActionHandler());
    }

    @Override
    public void execute(ReactApplicationContext context, ReadableMap action, Promise result) {
        if (action == null) {
            ModuleError error = FirebaseModuleErrors.badAction();
            result.reject(error.code(), error.message());
            return;
        }

        final String type = action.getString(ACTION_TYPE);
        if (type == null) {
            ModuleError error = FirebaseModuleErrors.badActionType();
            result.reject(error.code(), error.message());
            return;
        }

        JSActionHandler actionHandler = mHandlers.get(type);
        if (actionHandler != null) {
            actionHandler.handle(context, action, result);
        } else {
            ModuleError error = FirebaseModuleErrors.unknownActionType();
            result.reject(error.code(), error.message());
        }
    }
}
