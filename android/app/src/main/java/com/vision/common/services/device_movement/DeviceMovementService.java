package com.vision.common.services.device_movement;


import android.util.Log;

public class DeviceMovementService {
    private static DeviceMovementService sInstance;

    private DeviceMovementService() {

    }

    public static DeviceMovementService get() {
        if (sInstance == null) {
            sInstance = new DeviceMovementService();
        }

        return sInstance;
    }

    public void start() {
        Log.d("tag", "DeviceMovementService->start()");
    }

    public void stop() {
        Log.d("tag", "DeviceMovementService->stop()");
    }
}
