package com.vision.common.services.app_settings;


import com.vision.common.data.hybrid_objects.app_settings.AppSettings;

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

    public AppSettings getCurrentAppSettings() {
        return null;
    }

    public void updateCurrentAppSettings(AppSettings settings) {

    }
}
