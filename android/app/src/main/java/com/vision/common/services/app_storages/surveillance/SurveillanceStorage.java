package com.vision.common.services.app_storages.surveillance;


import android.content.Context;
import android.util.Log;

import com.tencent.mmkv.MMKV;
import com.vision.common.services.app_storages.helper.AppStoragesHelper;

public class SurveillanceStorage {
    private final String NOTIFICATIONS_TOPIC_MMKV_ID = "notificationsTopicMMKVID";
    private final String NOTIFICATIONS_TOPIC_KEY = "notificationsTopic";

    public SurveillanceStorage() {

    }

    public boolean saveGlobalNotificationsTopic(Context context, String topic) {
        MMKV mmkv = AppStoragesHelper.mmkvWithID(context, NOTIFICATIONS_TOPIC_MMKV_ID);
        if (mmkv == null) {
            Log.d("tag", "SurveillanceStorage->saveGlobalNotificationsTopic()->MMKV_IS_NULL");
            return false;
        }

        return mmkv.encode(NOTIFICATIONS_TOPIC_KEY, topic);
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

    public String getGlobalNotificationsTopic(Context context) {
        MMKV mmkv = AppStoragesHelper.mmkvWithID(context, NOTIFICATIONS_TOPIC_MMKV_ID);
        if (mmkv == null) {
            Log.d("tag", "SurveillanceStorage->getGlobalNotificationsTopic()->MMKV_IS_NULL");
            return null;
        }

        return mmkv.decodeString(NOTIFICATIONS_TOPIC_KEY);
    }
}
