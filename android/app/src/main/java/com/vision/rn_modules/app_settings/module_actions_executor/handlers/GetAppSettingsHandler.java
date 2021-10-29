package com.vision.rn_modules.app_settings.module_actions_executor.handlers;


import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.common.data.hybrid_objects.app_settings.AppSettings;
import com.vision.common.services.app_settings.AppSettingsService;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.rn_modules.modules_common.interfaces.js_action_handler.JSActionHandler;

public class GetAppSettingsHandler implements JSActionHandler {
    private final String ACTION_PAYLOAD = "payload";

    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "GetAppSettingsHandler->handle()");

        String currentGroupName = SurveillanceService.get().currentGroupName();

        AppSettings appSettings = AppSettingsService.get().getAppSettingsForGroup(context, currentGroupName);

        result.resolve(appSettings.toWritableMap());
    }
}
