package com.vision.common.services.device_movement;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;

public class DeviceMovementService {
    public static final String NAME = "DeviceMovementService";

    private final long MOVEMENT_COOLDOWN_TIMESTAMP_DELTA = 5000;

    private static DeviceMovementService sInstance;

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private SensorEventListener mSensorEventListener;
    private boolean mIsRunning = false;
    private boolean mIsInitialMovement = true;
    private float mPrevX = -1;
    private float mPrevY = -1;
    private float mPrevZ = -1;
    private long mLastMovementTimestamp = -1;
    private boolean mMovementInProgress = false;

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
        Log.d("tag", "DeviceMovementService->isRunning(): " + mIsRunning);
        return mIsRunning;
    }

    public void start(Context context,
                      OnTaskSuccess<Void> movementStartCallback,
                      OnTaskSuccess<Void> movementEndCallback) {
        Log.d("tag", "DeviceMovementService->start()");

        if (mIsRunning) {
            Log.d("tag", "DeviceMovementService->start(): SERVICE_ALREADY_RUNNING");
            return;
        }

        if (mSensorManager == null) {
            mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        }

        if (mSensorManager == null) {
            Log.d("tag", "DeviceMovementService->start(): UNABLE_TO_INITIALIZE_SENSOR_MANAGER");
            return;
        }
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (mSensor == null) {
            Log.d("tag", "DeviceMovementService->start(): UNABLE_TO_INITIALIZE_SENSOR");
            return;
        }

        mSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                processSensorData(sensorEvent, movementStartCallback, movementEndCallback);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        mIsRunning = mSensorManager.registerListener(mSensorEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop(Context context) {
        Log.d("tag", "DeviceMovementService->stop()");

        if (!mIsRunning) {
            Log.d("tag", "DeviceMovementService->stop(): SERVICE_ALREADY_STOPPED");
            return;
        }

        if (mSensorEventListener == null) {
            Log.d("tag", "DeviceMovementService->stop(): SENSOR_LISTENER_IS_NULL");
            mIsRunning = false;
            return;
        }

        if (mSensorManager == null) {
            Log.d("tag", "DeviceMovementService->stop(): SENSOR_MANAGER_IS_NULL");
            mIsRunning = false;
            return;
        }

        mSensorManager.unregisterListener(mSensorEventListener);
        mSensorEventListener = null;

        mPrevX = -1;
        mPrevY = -1;
        mPrevZ = -1;

        mLastMovementTimestamp = -1;

        mMovementInProgress = false;
        mIsInitialMovement = true;
        mIsRunning = false;
    }

    private void processSensorData(SensorEvent sensorEvent,
                                   OnTaskSuccess<Void> movementStartCallback,
                                   OnTaskSuccess<Void> movementEndCallback) {
        float[] values = sensorEvent.values;

        float x = values[0];
        float y = values[1];
        float z = values[2];

        if (mIsInitialMovement) {
            mIsInitialMovement = false;
            mPrevX = x;
            mPrevY = y;
            mPrevZ = z;
            return;
        }

        float xDelta = Math.abs(mPrevX - x);
        float yDelta = Math.abs(mPrevY - y);
        float zDelta = Math.abs(mPrevZ - z);

        long currentDateTimestamp = System.currentTimeMillis();

        if (hasMovement(x, y, z)) {
            mLastMovementTimestamp = currentDateTimestamp;

            if (!mMovementInProgress) {
                movementStartCallback.onSuccess(null);
                mMovementInProgress = true;
            }
        } else {
            if (mMovementInProgress) {
                if (mLastMovementTimestamp + MOVEMENT_COOLDOWN_TIMESTAMP_DELTA < currentDateTimestamp) {
                    movementEndCallback.onSuccess(null);
                    mMovementInProgress = false;
                }
            }
        }

        mPrevX = x;
        mPrevY = y;
        mPrevZ = z;
    }

    private boolean hasMovement(float x, float y, float z) {
        float xDelta = Math.abs(mPrevX - x);
        float yDelta = Math.abs(mPrevY - y);
        float zDelta = Math.abs(mPrevZ - z);

        return xDelta > 0.5 || yDelta > 0.5 || zDelta > 0.5;
    }
}
