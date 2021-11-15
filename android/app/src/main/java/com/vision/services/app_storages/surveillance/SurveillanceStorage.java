package com.vision.services.app_storages.surveillance;


import android.content.Context;
import android.util.Log;

import com.tencent.mmkv.MMKV;
import com.vision.common.data.hybrid_objects.app_settings.AppSettings;
import com.vision.common.data.hybrid_objects.authentication_data.AuthenticationData;
import com.vision.services.app_storages.helper.AppStoragesHelper;

public class SurveillanceStorage {
    public static final String NAME = "SurveillanceStorage";

    private final String AUTHENTICATION_DATA_MMKV_ID = "authenticationDataMMKVID";
    private final String AUTHENTICATION_DATA_KEY = "authenticationData";

    private final String NOTIFICATIONS_TOPIC_MMKV_ID = "notificationsTopicMMKVID";
    private final String NOTIFICATIONS_TOPIC_KEY = "notificationsTopic";

    private final String APP_SETTINGS_MMKV_ID = "appSettingsMMKVID";
    private final String APP_SETTINGS_PREFIX_KEY = "appSettings_";

    public SurveillanceStorage() {

    }

    public boolean saveLastAuthenticationData(Context context, AuthenticationData authenticationData) {
        MMKV mmkv = AppStoragesHelper.mmkvWithID(context, AUTHENTICATION_DATA_MMKV_ID);
        if (mmkv == null) {
            Log.d("tag", "SurveillanceStorage->saveLastAuthenticationData()->MMKV_IS_NULL");
            return false;
        }

        return mmkv.encode(AUTHENTICATION_DATA_KEY, authenticationData.stringify());
    }

    public AuthenticationData getLastAuthenticationData(Context context) {
        MMKV mmkv = AppStoragesHelper.mmkvWithID(context, AUTHENTICATION_DATA_MMKV_ID);
        if (mmkv == null) {
            Log.d("tag", "SurveillanceStorage->getLastAuthenticationData()->MMKV_IS_NULL");
            return null;
        }

        String stringifiedAuthenticationData = mmkv.decodeString(AUTHENTICATION_DATA_KEY);
        return new AuthenticationData(stringifiedAuthenticationData);
    }

    public boolean saveGlobalNotificationsTopic(Context context, String topic) {
        MMKV mmkv = AppStoragesHelper.mmkvWithID(context, NOTIFICATIONS_TOPIC_MMKV_ID);
        if (mmkv == null) {
            Log.d("tag", "SurveillanceStorage->saveGlobalNotificationsTopic()->MMKV_IS_NULL");
            return false;
        }

        return mmkv.encode(NOTIFICATIONS_TOPIC_KEY, topic);
    }

    public String getGlobalNotificationsTopic(Context context) {
        MMKV mmkv = AppStoragesHelper.mmkvWithID(context, NOTIFICATIONS_TOPIC_MMKV_ID);
        if (mmkv == null) {
            Log.d("tag", "SurveillanceStorage->getGlobalNotificationsTopic()->MMKV_IS_NULL");
            return null;
        }

        return mmkv.decodeString(NOTIFICATIONS_TOPIC_KEY);
    }

    public boolean removeGlobalNotificationsTopic(Context context) {
        MMKV mmkv = AppStoragesHelper.mmkvWithID(context, NOTIFICATIONS_TOPIC_MMKV_ID);
        if (mmkv == null) {
            Log.d("tag", "SurveillanceStorage->removeGlobalNotificationsTopic()->MMKV_IS_NULL");
            return false;
        }

        mmkv.removeValueForKey(NOTIFICATIONS_TOPIC_KEY);
        return true;
    }

    public boolean saveAppSettingsForGroup(Context context, String groupName, AppSettings settings) {
        MMKV mmkv = AppStoragesHelper.mmkvWithID(context, APP_SETTINGS_MMKV_ID);
        if (mmkv == null) {
            Log.d("tag", "SurveillanceStorage->saveAppSettingsForGroup()->MMKV_IS_NULL");
            return false;
        }

        String settingsStorageKey = APP_SETTINGS_PREFIX_KEY + groupName;

        return mmkv.encode(settingsStorageKey, settings.stringify());
    }

    public AppSettings getAppSettingsFroGroup(Context context, String groupName) {
        if (groupName == null) {
            Log.d("tag", "SurveillanceStorage->getAppSettingsFroGroup()->GROUP_NAME_IS_NULL");
            return null;
        }

        MMKV mmkv = AppStoragesHelper.mmkvWithID(context, APP_SETTINGS_MMKV_ID);
        if (mmkv == null) {
            Log.d("tag", "SurveillanceStorage->getAppSettingsFroGroup()->MMKV_IS_NULL");
            return null;
        }

        String settingsStorageKey = APP_SETTINGS_PREFIX_KEY + groupName;

        String stringifiedApPSettings = mmkv.decodeString(settingsStorageKey);
        return new AppSettings(stringifiedApPSettings);
    }

    public boolean removeAppSettingsForGroup(Context context, String groupName) {
        MMKV mmkv = AppStoragesHelper.mmkvWithID(context, APP_SETTINGS_MMKV_ID);
        if (mmkv == null) {
            Log.d("tag", "SurveillanceStorage->removeAppSettingsForGroup()->MMKV_IS_NULL");
            return false;
        }

        String settingsStorageKey = APP_SETTINGS_PREFIX_KEY + groupName;

        mmkv.removeValueForKey(settingsStorageKey);
        return true;
    }
}
