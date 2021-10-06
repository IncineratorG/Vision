package com.vision.modules.app_settings.module_actions.payloads;


import com.facebook.react.bridge.ReadableMap;
import com.vision.modules.app_settings.module_actions.payloads.payloads.UpdateAppSettingsPayload;

public class AppSettingsJSActionsPayloads {
    public static UpdateAppSettingsPayload updateAppSettingsPayload(ReadableMap payloadMap) {
        return new UpdateAppSettingsPayload(payloadMap);
    }
}
