package com.vision.modules.auth.module_actions_executor.handlers;


import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;

public class LogoutDeviceFromGroupHandler implements JSActionHandler {
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "LogoutDeviceFromGroupHandler");

        SurveillanceService surveillanceService = SurveillanceService.get();
        surveillanceService.dispose(context);

        result.resolve(true);
    }
}
