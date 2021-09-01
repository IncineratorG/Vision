package com.vision.modules.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.vision.modules.auth.module_actions.types.AuthJSActionTypes;
import com.vision.modules.modules_common.interfaces.js_actions_executor.JSActionsExecutor;

import java.util.HashMap;
import java.util.Map;

public class AuthModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext mContext;
    private JSActionsExecutor mActionsExecutor;

    public AuthModule(@Nullable ReactApplicationContext reactContext) {
        super(reactContext);

        mContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return "Auth";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();

        WritableMap actionTypesConstants = new WritableNativeMap();
        actionTypesConstants.putString(AuthJSActionTypes.IS_USER_GROUP_EXIST, AuthJSActionTypes.IS_USER_GROUP_EXIST);
        actionTypesConstants.putString(AuthJSActionTypes.REGISTER_USER_GROUP, AuthJSActionTypes.REGISTER_USER_GROUP);
        actionTypesConstants.putString(AuthJSActionTypes.CREATE_USER_IN_USER_GROUP, AuthJSActionTypes.CREATE_USER_IN_USER_GROUP);
        actionTypesConstants.putString(AuthJSActionTypes.LOGIN_USER_IN_USER_GROUP, AuthJSActionTypes.LOGIN_USER_IN_USER_GROUP);

        constants.put("actionTypes", actionTypesConstants);

        return constants;
    }

    @ReactMethod
    public void execute(ReadableMap action, Promise result) {
        mActionsExecutor.execute(mContext, action, result);
    }
}
