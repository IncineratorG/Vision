package com.vision.common.services.app_storages;

import com.vision.common.services.app_storages.common_storage.CommonStorage;
import com.vision.common.services.app_storages.surveillance.SurveillanceStorage;

public class AppStorages {
    private static AppStorages sInstance;

    private CommonStorage mCommonStorage;
    private SurveillanceStorage mSurveillanceStorage;

    private AppStorages() {
        mCommonStorage = new CommonStorage();
        mSurveillanceStorage = new SurveillanceStorage();
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

    public SurveillanceStorage surveillanceStorage() {
        return mSurveillanceStorage;
    }
}
