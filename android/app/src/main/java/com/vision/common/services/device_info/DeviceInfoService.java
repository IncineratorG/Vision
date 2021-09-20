package com.vision.common.services.device_info;

public class DeviceInfoService {
    private static DeviceInfoService sInstance;

    private DeviceInfoService() {

    }

    public static synchronized DeviceInfoService get() {
        if (sInstance == null) {
            sInstance = new DeviceInfoService();
        }

        return sInstance;
    }

    public boolean hasFrontCamera() {
        return false;
    }

    public boolean hasBackCamera() {
        return false;
    }
}
