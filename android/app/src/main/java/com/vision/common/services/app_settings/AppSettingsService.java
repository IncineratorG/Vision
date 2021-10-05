package com.vision.common.services.app_settings;


import android.content.Context;

import com.vision.common.data.hybrid_objects.app_settings.AppSettings;
import com.vision.common.services.app_storages.AppStorages;

public class AppSettingsService {
    private static AppSettingsService sInstance;

    private AppSettingsService() {

    }

    public static AppSettingsService get() {
        if (sInstance == null) {
            sInstance = new AppSettingsService();
        }

        return sInstance;
    }

    public AppSettings getAppSettingsForGroup(Context context, String groupName) {
        return null;
    }

    public void updateAppSettingsForGroup(Context context, String groupName, AppSettings settings) {

    }
}
