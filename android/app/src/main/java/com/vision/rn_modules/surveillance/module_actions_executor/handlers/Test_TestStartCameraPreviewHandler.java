package com.vision.rn_modules.surveillance.module_actions_executor.handlers;


import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.services.camera.old_unused.CameraService_V3;
import com.vision.rn_modules.modules_common.interfaces.js_action_handler.JSActionHandler;

public class Test_TestStartCameraPreviewHandler implements JSActionHandler {
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "Test_TestStartCameraPreviewHandler->handle()");

        CameraService_V3.get().startCameraPreview();

        result.resolve(true);
    }
}
