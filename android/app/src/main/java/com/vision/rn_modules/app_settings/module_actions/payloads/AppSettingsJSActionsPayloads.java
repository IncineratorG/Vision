package com.vision.rn_modules.app_settings.module_actions.payloads;


import com.facebook.react.bridge.ReadableMap;
import com.vision.rn_modules.app_settings.module_actions.payloads.payloads.UpdateAppSettingsPayload;

public class AppSettingsJSActionsPayloads {
    public static UpdateAppSettingsPayload updateAppSettingsPayload(ReadableMap payloadMap) {
        return new UpdateAppSettingsPayload(payloadMap);
    }
}
