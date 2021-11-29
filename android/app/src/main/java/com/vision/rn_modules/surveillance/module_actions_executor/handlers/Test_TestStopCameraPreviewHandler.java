package com.vision.rn_modules.surveillance.module_actions_executor.handlers;


import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.rn_modules.modules_common.interfaces.js_action_handler.JSActionHandler;

public class Test_TestStopCameraPreviewHandler implements JSActionHandler {
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "Test_TestStopCameraPreviewHandler->handle()");

//        CameraService_V3.get().stopCameraPreview();

        result.resolve(true);
    }
}
