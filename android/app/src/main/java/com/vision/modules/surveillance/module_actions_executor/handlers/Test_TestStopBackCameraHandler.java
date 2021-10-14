package com.vision.modules.surveillance.module_actions_executor.handlers;


import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.common.constants.AppConstants;
import com.vision.common.services.camera.CameraService_V4;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;

public class Test_TestStopBackCameraHandler implements JSActionHandler {
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "Test_TestStopBackCameraHandler->handle()");

//        CameraService_V4 cameraService = CameraService_V4.get();
//        cameraService.stopBackCameraPreview();

        result.resolve(true);
    }
}
