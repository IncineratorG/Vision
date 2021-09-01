package com.vision.common.services.app_storages;

import com.vision.common.services.app_storages.common_storage.CommonStorage;

public class AppStorages {
    private static AppStorages sInstance;

    private CommonStorage mCommonStorage;

    private AppStorages() {
        mCommonStorage = new CommonStorage();
    }

    public static synchronized AppStorages get() {
        if (sInstance == null) {
            sInstance = new AppStorages();
        }

        return sInstance;
    }

    public CommonStorage commonStorage() {
        return mCommonStorage;
    }
}
