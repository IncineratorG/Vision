package com.vision.services.app_storages.common_storage;

import android.content.Context;
import android.util.Log;

import com.tencent.mmkv.MMKV;
import com.vision.services.app_storages.helper.AppStoragesHelper;

public class CommonStorage {
    public static final String NAME = "CommonStorage";

    private static final String sClassName = "CommonStorage";

    public CommonStorage() {

    }

    public boolean save(Context context, String storageId, String key, String value) {
        MMKV mmkv = AppStoragesHelper.mmkvWithID(context, storageId);
        if (mmkv == null) {
            Log.d("tag", sClassName + "->save(): MMKV_IS_NULL");
            return false;
        }

        return mmkv.encode(key, value);
    }

    public boolean remove(Context context, String storageId, String key) {
        MMKV mmkv = AppStoragesHelper.mmkvWithID(context, storageId);
        if (mmkv == null) {
            Log.d("tag", sClassName + "->remove(): MMKV_IS_NULL");
            return false;
        }

        mmkv.removeValueForKey(key);
        return true;
    }

    public String get(Context context, String storageId, String key) {
        MMKV mmkv = AppStoragesHelper.mmkvWithID(context, storageId);
        if (mmkv == null) {
            Log.d("tag", sClassName + "->get(): MMKV_IS_NULL");
            return null;
        }

        return mmkv.decodeString(key);
    }
}
