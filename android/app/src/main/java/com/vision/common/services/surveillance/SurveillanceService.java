package com.vision.common.services.surveillance;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vision.android_services.foreground.surveillance.SurveillanceForegroundService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.services.surveillance.data.foreground_service_work.ForegroundServiceWork;
import com.vision.common.services.surveillance.data.foreground_service_work.firebase.FBSForegroundServiceWork;
import com.vision.common.services.surveillance.data.request_handler.RequestHandler;
import com.vision.common.services.surveillance.data.request_handler.firebase.FBSRequestHandler;

import java.util.List;

public class SurveillanceService {
    private static SurveillanceService sInstance;

    private ForegroundServiceWork mForegroundServiceWork;
    private RequestHandler mRequestHandler;

    private SurveillanceService() {
        mRequestHandler = new FBSRequestHandler();
        List<String> requestPath = FBSPathsService.get().requestPath();

        mForegroundServiceWork = new FBSForegroundServiceWork(
                mRequestHandler,
                requestPath
        );
    }

    public static synchronized SurveillanceService get() {
        if (sInstance == null) {
            sInstance = new SurveillanceService();
        }

        return sInstance;
    }

    public void start(Context context) {
        Log.d("tag", "SurveillanceService->start()");

        if (context == null) {
            Log.d("tag", "SurveillanceService->start(): CONTEXT_IS_NULL");
            return;
        }

        if (isRunning(context)) {
            Log.d("tag", "SurveillanceService->start(): SERVICE_IS_ALREADY_RUNNING");
            return;
        }

        Intent serviceIntent = new Intent(context, SurveillanceForegroundService.class);
        serviceIntent.setAction("start");
        context.startService(serviceIntent);
    }

    public void stop(Context context) {
        Log.d("tag", "SurveillanceService->stop()");

        if (context == null) {
            Log.d("tag", "SurveillanceService->stop(): CONTEXT_IS_NULL");
            return;
        }

        if (!isRunning(context)) {
            Log.d("tag", "SurveillanceService->stop(): SERVICE_ALREADY_NOT_RUNNING");
            return;
        }

        Intent serviceIntent = new Intent(context, SurveillanceForegroundService.class);
        serviceIntent.setAction("stop");
        context.startService(serviceIntent);
    }

    public boolean isRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (SurveillanceForegroundService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public ForegroundServiceWork foregroundServiceWork() {
        return mForegroundServiceWork;
    }
}
