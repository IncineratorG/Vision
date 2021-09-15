package com.vision.modules.surveillance_service;

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
import com.vision.modules.surveillance_service.module_actions.types.SurveillanceServiceJSActionTypes;
import com.vision.modules.surveillance_service.module_actions_executor.SurveillanceServiceJSActionsExecutor;

import java.util.HashMap;
import java.util.Map;

public class SurveillanceServiceModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext mContext;
    private JSActionsExecutor mActionsExecutor;

    public SurveillanceServiceModule(@Nullable ReactApplicationContext reactContext) {
        super(reactContext);

        mContext = reactContext;
        mActionsExecutor = new SurveillanceServiceJSActionsExecutor();
    }

    @NonNull
    @Override
    public String getName() {
        return "SurveillanceService";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();

        WritableMap actionTypesConstants = new WritableNativeMap();
        actionTypesConstants.putString(SurveillanceServiceJSActionTypes.IS_RUNNING, SurveillanceServiceJSActionTypes.IS_RUNNING);
        actionTypesConstants.putString(SurveillanceServiceJSActionTypes.START_SERVICE, SurveillanceServiceJSActionTypes.START_SERVICE);
        actionTypesConstants.putString(SurveillanceServiceJSActionTypes.STOP_SERVICE, SurveillanceServiceJSActionTypes.STOP_SERVICE);
        actionTypesConstants.putString(SurveillanceServiceJSActionTypes.TEST_REQUEST, SurveillanceServiceJSActionTypes.TEST_REQUEST);
        actionTypesConstants.putString(SurveillanceServiceJSActionTypes.GET_DEVICES_IN_GROUP, SurveillanceServiceJSActionTypes.GET_DEVICES_IN_GROUP);

        constants.put("actionTypes", actionTypesConstants);

        return constants;
    }

    @ReactMethod
    public void execute(ReadableMap action, Promise result) {
        mActionsExecutor.execute(mContext, action, result);
    }
}
