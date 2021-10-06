package com.vision.modules.modules_common.interfaces.activity_request_permissions_result_callback;


import java.util.HashMap;
import java.util.Map;

public class ModuleActivityRequestPermissionsResultCallbacks {
    private Map<Integer, ActivityRequestPermissionsResultCallback> mActivityRequestPermissionsResultCallbacks;

    public ModuleActivityRequestPermissionsResultCallbacks() {
        mActivityRequestPermissionsResultCallbacks = new HashMap<>();
    }

    public void set(int resultCode, ActivityRequestPermissionsResultCallback callback) {
        mActivityRequestPermissionsResultCallbacks.put(resultCode, callback);
    }

    public ActivityRequestPermissionsResultCallback getAndRemove(int resultCode) {
        ActivityRequestPermissionsResultCallback callback = mActivityRequestPermissionsResultCallbacks.get(resultCode);
        mActivityRequestPermissionsResultCallbacks.remove(resultCode);
        return callback;
    }
}
