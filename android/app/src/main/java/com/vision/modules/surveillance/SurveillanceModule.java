package com.vision.modules.surveillance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.vision.common.services.surveillance.data.service_requests.types.SurveillanceServiceRequestTypes;
import com.vision.modules.modules_common.interfaces.js_actions_executor.JSActionsExecutor;
import com.vision.modules.surveillance.module_actions.types.SurveillanceJSActionTypes;
import com.vision.modules.surveillance.module_actions_executor.SurveillanceJSActionsExecutor;
import com.vision.modules.surveillance.module_events.types.SurveillanceEventTypes;

import java.util.HashMap;
import java.util.Map;

public class SurveillanceModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext mContext;
    private JSActionsExecutor mActionsExecutor;

    public SurveillanceModule(@Nullable ReactApplicationContext reactContext) {
        super(reactContext);

        mContext = reactContext;
        mActionsExecutor = new SurveillanceJSActionsExecutor();
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
        // ===
        actionTypesConstants.putString(SurveillanceJSActionTypes.TEST_REQUEST, SurveillanceJSActionTypes.TEST_REQUEST);
        actionTypesConstants.putString(SurveillanceJSActionTypes.SEND_REQUEST, SurveillanceJSActionTypes.SEND_REQUEST);
        // ===
        actionTypesConstants.putString(SurveillanceJSActionTypes.IS_RUNNING, SurveillanceJSActionTypes.IS_RUNNING);
        actionTypesConstants.putString(SurveillanceJSActionTypes.START_SERVICE, SurveillanceJSActionTypes.START_SERVICE);
        actionTypesConstants.putString(SurveillanceJSActionTypes.STOP_SERVICE, SurveillanceJSActionTypes.STOP_SERVICE);
        actionTypesConstants.putString(SurveillanceJSActionTypes.GET_DEVICES_IN_GROUP, SurveillanceJSActionTypes.GET_DEVICES_IN_GROUP);

        WritableMap requestTypes = new WritableNativeMap();
        requestTypes.putString(SurveillanceServiceRequestTypes.TEST_REQUEST_WITH_PAYLOAD, SurveillanceServiceRequestTypes.TEST_REQUEST_WITH_PAYLOAD);
        requestTypes.putString(SurveillanceServiceRequestTypes.IS_DEVICE_ALIVE, SurveillanceServiceRequestTypes.IS_DEVICE_ALIVE);

        WritableMap eventTypes = new WritableNativeMap();
        eventTypes.putString(SurveillanceEventTypes.REQUEST_ERROR, SurveillanceEventTypes.REQUEST_ERROR);
        eventTypes.putString(SurveillanceEventTypes.RESPONSE_RECEIVED, SurveillanceEventTypes.RESPONSE_RECEIVED);

        constants.put("actionTypes", actionTypesConstants);
        constants.put("requestTypes", requestTypes);
        constants.put("eventTypes", eventTypes);

        return constants;
    }

    @ReactMethod
    public void execute(ReadableMap action, Promise result) {
        mActionsExecutor.execute(mContext, action, result);
    }
}
