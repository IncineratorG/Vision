package com.vision.rn_modules.auth.module_actions_executor.handlers;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.rn_modules.modules_common.interfaces.js_action_handler.JSActionHandler;

public class IsLoggedInHandler implements JSActionHandler {
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "IsLoggedInHandler->handle()");

        SurveillanceService surveillanceService = SurveillanceService.get();

        if (surveillanceService.isInitialized()) {
            Log.d("tag", "IsLoggedInHandler->handle(): SERVICE_INITIALIZED");
        } else {
            Log.d("tag", "IsLoggedInHandler->handle(): SERVICE_NOT_INITIALIZED");
            result.resolve(false);
        }

        String groupName = surveillanceService.currentGroupName();
        String groupPassword = surveillanceService.currentGroupPassword();
        String deviceName = surveillanceService.currentDeviceName();

        if (groupName == null) {
            Log.d("tag", "IsLoggedInHandler->handle(): GROUP_NAME_IS_NULL");
        } else {
            Log.d("tag", "IsLoggedInHandler->handle(): GROUP_NAME: " + groupName);
        }

        if (groupPassword == null) {
            Log.d("tag", "IsLoggedInHandler->handle(): GROUP_PASSWORD_IS_NULL");
        } else {
            Log.d("tag", "IsLoggedInHandler->handle(): GROUP_PASSWORD: " + groupPassword);
        }

        if (deviceName == null) {
            Log.d("tag", "IsLoggedInHandler->handle(): DEVICE_NAME_IS_NULL");
        } else {
            Log.d("tag", "IsLoggedInHandler->handle(): DEVICE_NAME: " + deviceName);
        }

        if (groupName == null ||
                groupPassword == null ||
                deviceName == null ||
                !surveillanceService.isInitialized()) {
            result.resolve(false);
        } else {
            result.resolve(true);
        }
    }
}
