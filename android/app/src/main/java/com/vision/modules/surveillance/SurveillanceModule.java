package com.vision.modules.surveillance;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.PermissionListener;
import com.vision.common.services.surveillance.data.requests.types.SurveillanceServiceRequestTypes;
import com.vision.modules.surveillance.module_actions.types.SurveillanceJSActionTypes;
import com.vision.modules.surveillance.module_actions_executor.SurveillanceJSActionsExecutor;
import com.vision.modules.surveillance.module_events.types.SurveillanceEventTypes;

import java.util.HashMap;
import java.util.Map;

public class SurveillanceModule extends ReactContextBaseJavaModule implements PermissionListener {
    private ReactApplicationContext mContext;
    private SurveillanceJSActionsExecutor mActionsExecutor;

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
        actionTypesConstants.putString(SurveillanceJSActionTypes.SEND_TEST_NOTIFICATION, SurveillanceJSActionTypes.SEND_TEST_NOTIFICATION);
        actionTypesConstants.putString(SurveillanceJSActionTypes.TEST_MOTION_SENSOR, SurveillanceJSActionTypes.TEST_MOTION_SENSOR);
        actionTypesConstants.putString(SurveillanceJSActionTypes.TEST_CAMERA_MOTION_DETECTION, SurveillanceJSActionTypes.TEST_CAMERA_MOTION_DETECTION);
        actionTypesConstants.putString(SurveillanceJSActionTypes.TEST_START_CAMERA_PREVIEW, SurveillanceJSActionTypes.TEST_START_CAMERA_PREVIEW);
        actionTypesConstants.putString(SurveillanceJSActionTypes.TEST_STOP_CAMERA_PREVIEW, SurveillanceJSActionTypes.TEST_STOP_CAMERA_PREVIEW);
        actionTypesConstants.putString(SurveillanceJSActionTypes.TEST_TAKE_CAMERA_PREVIEW_PICTURE, SurveillanceJSActionTypes.TEST_TAKE_CAMERA_PREVIEW_PICTURE);

        actionTypesConstants.putString(SurveillanceJSActionTypes.TEST_START_BACK_CAMERA, SurveillanceJSActionTypes.TEST_START_BACK_CAMERA);
        actionTypesConstants.putString(SurveillanceJSActionTypes.TEST_START_FRONT_CAMERA, SurveillanceJSActionTypes.TEST_START_FRONT_CAMERA);
        actionTypesConstants.putString(SurveillanceJSActionTypes.TEST_STOP_BACK_CAMERA, SurveillanceJSActionTypes.TEST_STOP_BACK_CAMERA);
        actionTypesConstants.putString(SurveillanceJSActionTypes.TEST_STOP_FRONT_CAMERA, SurveillanceJSActionTypes.TEST_STOP_FRONT_CAMERA);
        actionTypesConstants.putString(SurveillanceJSActionTypes.TEST_TAKE_BACK_CAMERA_PICTURE, SurveillanceJSActionTypes.TEST_TAKE_BACK_CAMERA_PICTURE);
        actionTypesConstants.putString(SurveillanceJSActionTypes.TEST_TAKE_FRONT_CAMERA_PICTURE, SurveillanceJSActionTypes.TEST_TAKE_FRONT_CAMERA_PICTURE);
        // ===
        actionTypesConstants.putString(SurveillanceJSActionTypes.GET_APP_PERMISSIONS, SurveillanceJSActionTypes.GET_APP_PERMISSIONS);
        actionTypesConstants.putString(SurveillanceJSActionTypes.IS_RUNNING, SurveillanceJSActionTypes.IS_RUNNING);
        actionTypesConstants.putString(SurveillanceJSActionTypes.START_SERVICE, SurveillanceJSActionTypes.START_SERVICE);
        actionTypesConstants.putString(SurveillanceJSActionTypes.STOP_SERVICE, SurveillanceJSActionTypes.STOP_SERVICE);
        actionTypesConstants.putString(SurveillanceJSActionTypes.GET_DEVICES_IN_GROUP, SurveillanceJSActionTypes.GET_DEVICES_IN_GROUP);
        actionTypesConstants.putString(SurveillanceJSActionTypes.SEND_REQUEST, SurveillanceJSActionTypes.SEND_REQUEST);
        actionTypesConstants.putString(SurveillanceJSActionTypes.CANCEL_REQUEST, SurveillanceJSActionTypes.CANCEL_REQUEST);

        WritableMap requestTypes = new WritableNativeMap();
        requestTypes.putString(SurveillanceServiceRequestTypes.TEST_REQUEST_WITH_PAYLOAD, SurveillanceServiceRequestTypes.TEST_REQUEST_WITH_PAYLOAD);
        requestTypes.putString(SurveillanceServiceRequestTypes.IS_DEVICE_ALIVE, SurveillanceServiceRequestTypes.IS_DEVICE_ALIVE);
        requestTypes.putString(SurveillanceServiceRequestTypes.TAKE_BACK_CAMERA_IMAGE, SurveillanceServiceRequestTypes.TAKE_BACK_CAMERA_IMAGE);
        requestTypes.putString(SurveillanceServiceRequestTypes.TAKE_FRONT_CAMERA_IMAGE, SurveillanceServiceRequestTypes.TAKE_FRONT_CAMERA_IMAGE);
        requestTypes.putString(SurveillanceServiceRequestTypes.TOGGLE_DETECT_DEVICE_MOVEMENT, SurveillanceServiceRequestTypes.TOGGLE_DETECT_DEVICE_MOVEMENT);

        WritableMap eventTypes = new WritableNativeMap();
        eventTypes.putString(SurveillanceEventTypes.REQUEST_DELIVERED, SurveillanceEventTypes.REQUEST_DELIVERED);
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

    @Override
    public boolean onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("tag", "SurveillanceModule->onRequestPermissionsResult()");
        return true;
    }
}
