package com.vision.modules.surveillance.module_actions_executor.handlers;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;

public class Test_TestMotionSensorHandler implements JSActionHandler {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private SensorEventListener mSensorEventListener;
    private boolean mListenerRegistered;

    private boolean mIsInitialMovement = true;
    private float mPrevX = -1;
    private float mPrevY = -1;
    private float mPrevZ = -1;
    private final float mDefaultX = 0.00f;
    private final float mDefaultY = 9.81f;
    private final float mDefaultZ = 0.00f;

    public Test_TestMotionSensorHandler() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "TestMotionSensorHandler->handle()");

        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Log.d("tag", "1: " + (mSensorManager == null));
        Log.d("tag", "2: " + (mSensor == null));

        if (mListenerRegistered) {
            Log.d("tag", "TestMotionSensorHandler->handle()->WILL_UNREGISTER");

            if (mSensorEventListener != null) {
                mSensorManager.unregisterListener(mSensorEventListener);
                mIsInitialMovement = true;
                mPrevX = -1;
                mPrevY = -1;
                mPrevZ = -1;
            } else {
                Log.d("tag", "TestMotionSensorHandler->handle(): LISTENER_IS_NULL");
            }
        } else {
            Log.d("tag", "TestMotionSensorHandler->handle()->WILL_REGISTER");

            mSensorEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    float[] values = sensorEvent.values;
                    // Movement
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

                    Log.d("tag", x + " - " + y + " - " + z + " : " + xDelta + " - " + yDelta + " - " + zDelta);

                    mPrevX = x;
                    mPrevY = y;
                    mPrevZ = z;

                    if (xDelta > 0.5 || yDelta > 0.5 || zDelta > 0.5) {
                        Log.d("tag", "MOVEMENT_DETECTED: " + x + " - " + y + " - " + z);

                        Toast.makeText(
                                context,
                                "MOVEMENT_DETECTED: " + x + " - " + y + " - " + z,
                                Toast.LENGTH_SHORT
                        ).show();
                    }

//                    String xStr = String.format("%.2f", x);
//                    String yStr = String.format("%.2f", y);
//                    String zStr = String.format("%.2f", z);
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            };

            mListenerRegistered = mSensorManager.registerListener(mSensorEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

            Log.d("tag", "TestMotionSensorHandler->handle()->WILL_REGISTER->RESULT: " + mListenerRegistered);
        }

        // ===
        // =====
//        if (mTriggerEventListener == null) {
//            Log.d("tag", "TestMotionSensorHandler->handle(): WILL_INIT_SENSOR");
//
//            mTriggerEventListener = new TriggerEventListener() {
//                @Override
//                public void onTrigger(TriggerEvent event) {
//                    // Do work
//
//                    Toast.makeText(
//                            context,
//                            "Motion Sensor Trigered",
//                            Toast.LENGTH_SHORT
//                    ).show();
//                }
//            };
//
//            mSensorManager.requestTriggerSensor(mTriggerEventListener, mSensor);
//        } else {
//            Log.d("tag", "TestMotionSensorHandler->handle(): WILL_CLEAR_SENSOR");
//
//            mSensorManager.cancelTriggerSensor(mTriggerEventListener, mSensor);
//        }
        // =====
        // ===

        result.resolve(true);
    }
}
