package com.vision.modules.app_settings.module_actions_executor.handlers;


import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;

public class UpdateAppSettingsHandler implements JSActionHandler {
    private final String ACTION_PAYLOAD = "payload";

    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "UpdateAppSettingsHandler->handle()");

        result.resolve(true);
    }
}
