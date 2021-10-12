package com.vision.common.services.app_settings;


import android.content.Context;
import android.util.Log;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.vision.common.data.hybrid_objects.app_settings.AppSettings;
import com.vision.common.services.app_storages.AppStorages;
import com.vision.common.services.app_storages.surveillance.SurveillanceStorage;

public class AppSettingsService {
    public static final String NAME = "AppSettingsService";

    private static AppSettingsService sInstance;

    private AppSettings mDefaultAppSettings;

    private AppSettingsService() {
        WritableMap defaultSettingsMap = new WritableNativeMap();

        WritableMap surveillanceSettingsMap = new WritableNativeMap();

        WritableMap backCameraImageSettingsMap = new WritableNativeMap();
        backCameraImageSettingsMap.putString(AppSettings.BACK_CAMERA_IMAGE_QUALITY_FIELD, "low");

        surveillanceSettingsMap.putMap(AppSettings.BACK_CAMERA_IMAGE_SETTINGS_FIELD, backCameraImageSettingsMap);

        WritableMap frontCameraImageSettingsMap = new WritableNativeMap();
        frontCameraImageSettingsMap.putString(AppSettings.FRONT_CAMERA_IMAGE_QUALITY_FIELD, "low");

        surveillanceSettingsMap.putMap(AppSettings.FRONT_CAMERA_IMAGE_QUALITY_SETTINGS_FIELD, frontCameraImageSettingsMap);

        WritableMap notificationSettingsMap = new WritableNativeMap();
        notificationSettingsMap.putBoolean(AppSettings.NOTIFICATIONS_SETTINGS_RECEIVE_NOTIFICATIONS_FROM_CURRENT_GROUP_FIELD, true);

        surveillanceSettingsMap.putMap(AppSettings.NOTIFICATIONS_SETTINGS_FIELD, notificationSettingsMap);

        defaultSettingsMap.putMap(AppSettings.SURVEILLANCE_SETTINGS_FIELD, surveillanceSettingsMap);

        // ===
        mDefaultAppSettings = new AppSettings(defaultSettingsMap);
        // ===
    }

    public static AppSettingsService get() {
        if (sInstance == null) {
            sInstance = new AppSettingsService();
        }

        return sInstance;
    }

    public AppSettings getAppSettingsForGroup(Context context, String groupName) {
        SurveillanceStorage storage = AppStorages.get().surveillanceStorage();

        AppSettings savedAppSettings = storage.getAppSettingsFroGroup(context, groupName);
        if (savedAppSettings == null || savedAppSettings.isEmpty()) {
            return new AppSettings(mDefaultAppSettings);
        }

        return savedAppSettings;
    }

    public void updateAppSettingsForGroup(Context context, String groupName, AppSettings settings) {
        AppStorages.get().surveillanceStorage().saveAppSettingsForGroup(context, groupName, settings);
    }
}
