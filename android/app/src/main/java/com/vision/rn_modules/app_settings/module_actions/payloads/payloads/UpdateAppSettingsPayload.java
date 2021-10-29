package com.vision.rn_modules.app_settings.module_actions.payloads.payloads;


import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.vision.rn_modules.modules_common.interfaces.js_payload.JSPayload;

public class UpdateAppSettingsPayload implements JSPayload {
    private final String APP_SETTINGS_FIELD = "appSettings";

    private ReadableMap mSettingsMap;

    public UpdateAppSettingsPayload(ReadableMap readableMap) {
        if (readableMap == null) {
            Log.d("tag", "UpdateAppSettingsPayload->READABLE_MAP_IS_NULL");
            return;
        }

        mSettingsMap = readableMap.getMap(APP_SETTINGS_FIELD);
    }

    @Override
    public boolean isValid() {
        return mSettingsMap != null;
    }

    public ReadableMap settingsMap() {
        return mSettingsMap;
    }
}
