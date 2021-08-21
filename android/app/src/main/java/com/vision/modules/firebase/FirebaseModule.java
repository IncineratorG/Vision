package com.vision.modules.firebase;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.vision.modules.firebase.module_actions.types.FirebaseJSActionTypes;
import com.vision.modules.firebase.module_actions_executor.FirebaseJSActionsExecutor;
import com.vision.modules.modules_common.interfaces.js_actions_executor.JSActionsExecutor;

import java.util.HashMap;
import java.util.Map;

public class FirebaseModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext mContext;
    private JSActionsExecutor mActionsExecutor;

    public FirebaseModule(@Nullable ReactApplicationContext reactContext) {
        super(reactContext);

        mContext = reactContext;
        mActionsExecutor = new FirebaseJSActionsExecutor();
    }

    @NonNull
    @Override
    public String getName() {
        return "Firebase";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();

        WritableMap actionTypesConstants = new WritableNativeMap();
        actionTypesConstants.putString(FirebaseJSActionTypes.TEST_ACTION, FirebaseJSActionTypes.TEST_ACTION);

        constants.put("actionTypes", actionTypesConstants);

        return constants;
    }

    @ReactMethod
    public void execute(ReadableMap action, Promise result) {
        mActionsExecutor.execute(mContext, action, result);
    }
}
