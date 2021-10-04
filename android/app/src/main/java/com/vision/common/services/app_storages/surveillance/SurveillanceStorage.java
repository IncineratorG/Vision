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
        return false;

//        MMKV mmkv = AppStoragesHelper.mmkvWithID(context, NOTES_LIST_SETTINGS_MMKV_ID);
//        if (mmkv == null) {
//            Log.d("tag", "AppNotesStorage->saveNotesListSettings()->MMKV_IS_NULL");
//            return false;
//        }
    }
}
