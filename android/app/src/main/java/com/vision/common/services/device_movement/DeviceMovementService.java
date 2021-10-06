package com.vision.common.services.device_movement;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class DeviceMovementService {
    private static DeviceMovementService sInstance;

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private SensorEventListener mSensorEventListener;

    private DeviceMovementService() {

    }

    public static DeviceMovementService get() {
        if (sInstance == null) {
            sInstance = new DeviceMovementService();
        }

        return sInstance;
    }

    public boolean canDetectDeviceMovement(Context context) {
        if (mSensorManager == null) {
            mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        }

        if (mSensorManager == null) {
            return false;
        }

        return mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null;
    }

    public boolean isRunning() {
        return false;
    }

    public void start() {
        Log.d("tag", "DeviceMovementService->start()");
    }

    public void stop() {
        Log.d("tag", "DeviceMovementService->stop()");
    }
}
