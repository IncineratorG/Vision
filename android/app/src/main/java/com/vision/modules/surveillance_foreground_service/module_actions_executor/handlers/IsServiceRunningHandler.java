package com.vision.modules.surveillance_foreground_service.module_actions_executor.handlers;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;

public class IsServiceRunningHandler implements JSActionHandler {
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "IsServiceRunningHandler");

        boolean isForegroundServiceRunning = SurveillanceService.get().isForegroundServiceRunning(context);

        Log.d("tag", "IsServiceRunningHandler: " + isForegroundServiceRunning);

        result.resolve(isForegroundServiceRunning);
    }
}
