package com.vision.modules.surveillance_foreground_service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.vision.modules.modules_common.interfaces.js_actions_executor.JSActionsExecutor;
import com.vision.modules.surveillance_foreground_service.module_actions.types.SurveillanceForegroundServiceJSActionTypes;
import com.vision.modules.surveillance_foreground_service.module_actions_executor.SurveillanceForegroundServiceJSActionsExecutor;

import java.util.HashMap;
import java.util.Map;

public class SurveillanceForegroundServiceModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext mContext;
    private JSActionsExecutor mActionsExecutor;

    public SurveillanceForegroundServiceModule(@Nullable ReactApplicationContext reactContext) {
        super(reactContext);

        mContext = reactContext;
        mActionsExecutor = new SurveillanceForegroundServiceJSActionsExecutor();
    }

    @NonNull
    @Override
    public String getName() {
        return "SurveillanceForegroundService";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();

        WritableMap actionTypesConstants = new WritableNativeMap();
        actionTypesConstants.putString(SurveillanceForegroundServiceJSActionTypes.IS_RUNNING, SurveillanceForegroundServiceJSActionTypes.IS_RUNNING);
        actionTypesConstants.putString(SurveillanceForegroundServiceJSActionTypes.START_SERVICE, SurveillanceForegroundServiceJSActionTypes.START_SERVICE);
        actionTypesConstants.putString(SurveillanceForegroundServiceJSActionTypes.STOP_SERVICE, SurveillanceForegroundServiceJSActionTypes.STOP_SERVICE);
        actionTypesConstants.putString(SurveillanceForegroundServiceJSActionTypes.TEST_REQUEST, SurveillanceForegroundServiceJSActionTypes.TEST_REQUEST);

        constants.put("actionTypes", actionTypesConstants);

        return constants;
    }

    @ReactMethod
    public void execute(ReadableMap action, Promise result) {
        mActionsExecutor.execute(mContext, action, result);
    }
}
