package com.vision.services.surveillance.pipeline;

import android.util.Log;

import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;


public class Pipeline {
    private static Pipeline sInstance;

    private boolean mIsRunning;
    private Thread mWorkerThread;

    private Pipeline() {
        mIsRunning = false;
    }

    public static synchronized Pipeline get() {
        if (sInstance == null) {
            sInstance = new Pipeline();
        }

        return sInstance;
    }

    public void start(OnTaskSuccess<Void> onSuccess, OnTaskError<ServiceError> error) {
        Log.d("TAG", "Pipeline-start()");

        mWorkerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                Log.d("TAG", "PIPELINE_RUNNING");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        mWorkerThread.start();

        mIsRunning = true;

        onSuccess.onSuccess(null);
    }

    public void stop(OnTaskSuccess<Void> onSuccess, OnTaskError<ServiceError> error) {
        Log.d("TAG", "Pipeline->stop()");

        mIsRunning = false;

        if (mWorkerThread != null) {
            mWorkerThread.interrupt();
            try {
                mWorkerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        onSuccess.onSuccess(null);
    }

    public boolean isRunning() {
        Log.d("TAG", "Pipeline->isRunning()");

        return mIsRunning;
    }
}
