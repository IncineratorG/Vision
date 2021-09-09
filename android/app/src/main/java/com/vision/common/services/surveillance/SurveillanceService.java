package com.vision.common.services.surveillance;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vision.android_services.foreground.surveillance.SurveillanceForegroundService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.services.surveillance.data.foreground_service_work.ForegroundServiceWork;
import com.vision.common.services.surveillance.data.foreground_service_work.firebase.FBSForegroundServiceWork;
import com.vision.common.services.surveillance.data.request.Request;
import com.vision.common.services.surveillance.data.request_sender.OnDeliveredCallback;
import com.vision.common.services.surveillance.data.request_sender.OnErrorCallback;
import com.vision.common.services.surveillance.data.request_sender.OnResponseCallback;
import com.vision.common.services.surveillance.data.request_sender.RequestSender;
import com.vision.common.services.surveillance.data.request_sender.firebase.FBSRequestSender;
import com.vision.common.services.surveillance.data.requests_executor.RequestsExecutor;
import com.vision.common.services.surveillance.data.requests_executor.firebase.FBSRequestsExecutor;
import com.vision.common.services.surveillance.data.response.Response;
import com.vision.common.services.surveillance.data.response_sender.ResponseSender;
import com.vision.common.services.surveillance.data.service_requests.SurveillanceServiceRequests;

import java.util.List;

public class SurveillanceService implements ResponseSender, RequestSender {
    private static SurveillanceService sInstance;

    private ForegroundServiceWork mForegroundServiceWork;
    private RequestsExecutor mRequestsExecutor;
    private SurveillanceServiceRequests mRequests;
    private RequestSender mRequestsSender;

    private SurveillanceService() {
        mRequestsExecutor = new FBSRequestsExecutor();
        List<String> requestPath = FBSPathsService.get().currentRequestsPath();
        mRequests = new SurveillanceServiceRequests();

        mForegroundServiceWork = new FBSForegroundServiceWork(
                mRequestsExecutor,
                requestPath
        );

        mRequestsSender = new FBSRequestSender();
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

    public SurveillanceServiceRequests requests() {
        return mRequests;
    }

    @Override
    public void sendRequest(String receiverLogin,
                            Request request,
                            OnDeliveredCallback deliveredCallback,
                            OnResponseCallback onResponseCallback,
                            OnErrorCallback onErrorCallback) {
        Log.d("tag", "SurveillanceService->sendRequest()");

        mRequestsSender.sendRequest(
                receiverLogin,
                request,
                deliveredCallback,
                onResponseCallback,
                onErrorCallback
        );
    }

    @Override
    public void sendResponse(Response response) {
        Log.d("tag", "SurveillanceService->sendResponse()");
    }

    public ForegroundServiceWork foregroundServiceWork() {
        return mForegroundServiceWork;
    }
}
