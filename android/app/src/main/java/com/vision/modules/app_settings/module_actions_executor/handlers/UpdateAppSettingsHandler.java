package com.vision.modules.app_settings.module_actions_executor.handlers;


import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.common.data.hybrid_objects.app_settings.AppSettings;
import com.vision.modules.app_settings.module_actions.payloads.AppSettingsJSActionsPayloads;
import com.vision.modules.app_settings.module_actions.payloads.payloads.UpdateAppSettingsPayload;
import com.vision.modules.app_settings.module_errors.AppSettingsModuleErrors;
import com.vision.modules.modules_common.data.error.ModuleError;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;

public class UpdateAppSettingsHandler implements JSActionHandler {
    private final String ACTION_PAYLOAD = "payload";

    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "UpdateAppSettingsHandler->handle()");

        ReadableMap payloadMap = action.getMap(ACTION_PAYLOAD);
        if (payloadMap == null) {
            ModuleError error = AppSettingsModuleErrors.badPayload();
            result.reject(error.code(), error.message());
            return;
        }

        UpdateAppSettingsPayload payload = AppSettingsJSActionsPayloads.updateAppSettingsPayload(payloadMap);
        if (!payload.isValid()) {
            ModuleError error = AppSettingsModuleErrors.badPayload();
            result.reject(error.code(), error.message());
            return;
        }

        AppSettings appSettings = new AppSettings(payload.settingsMap());

        result.resolve(true);
    }
}
