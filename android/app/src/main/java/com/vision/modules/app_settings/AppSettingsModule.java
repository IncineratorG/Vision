package com.vision.modules.app_settings;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.vision.modules.app_settings.module_actions.types.AppSettingsJSActionTypes;
import com.vision.modules.app_settings.module_actions_executor.AppSettingsJSActionsExecutor;
import com.vision.modules.modules_common.interfaces.js_actions_executor.JSActionsExecutor;

import java.util.HashMap;
import java.util.Map;

public class AppSettingsModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext mContext;
    private JSActionsExecutor mActionsExecutor;

    public AppSettingsModule(@Nullable ReactApplicationContext reactContext) {
        super(reactContext);

        mContext = reactContext;
        mActionsExecutor = new AppSettingsJSActionsExecutor();
    }

    @NonNull
    @Override
    public String getName() {
        return "AppSettings";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();

        WritableMap actionTypesConstants = new WritableNativeMap();
        actionTypesConstants.putString(AppSettingsJSActionTypes.GET_APP_SETTINGS, AppSettingsJSActionTypes.GET_APP_SETTINGS);
        actionTypesConstants.putString(AppSettingsJSActionTypes.UPDATE_APP_SETTINGS, AppSettingsJSActionTypes.UPDATE_APP_SETTINGS);

        constants.put("actionTypes", actionTypesConstants);

        return constants;
    }

    @ReactMethod
    public void execute(ReadableMap action, Promise result) {
        mActionsExecutor.execute(mContext, action, result);
    }
}
