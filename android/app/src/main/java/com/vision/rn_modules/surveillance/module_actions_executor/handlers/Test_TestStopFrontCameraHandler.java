package com.vision.rn_modules.surveillance.module_actions_executor.handlers;


import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.rn_modules.modules_common.interfaces.js_action_handler.JSActionHandler;

public class Test_TestStopFrontCameraHandler implements JSActionHandler {
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "Test_TestStopFrontCameraHandler->handle()");

//        CameraService_V4 cameraService = CameraService_V4.get();
//        cameraService.stopFrontCameraPreview();

        result.resolve(true);
    }
}