package com.vision.common.services.surveillance;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vision.android_services.foreground.surveillance.SurveillanceForegroundService;
import com.vision.common.interfaces.service_responses_handler.ServiceResponsesHandler;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.interfaces.foregroun_service_work.ForegroundServiceWork;
import com.vision.common.services.surveillance.data.foreground_service_work.firebase.FBSForegroundServiceWork;
import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.interfaces.service_request_sender.callbacks.OnDeliveredCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnErrorCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnResponseCallback;
import com.vision.common.interfaces.service_request_sender.ServiceRequestSender;
import com.vision.common.services.surveillance.data.request_sender.firebase.FBSRequestSender;
import com.vision.common.interfaces.service_requests_handler.ServiceRequestsHandler;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_response_sender.ServiceResponseSender;
import com.vision.common.services.surveillance.data.requests_handler.firebase.FBSRequestsHandler;
import com.vision.common.services.surveillance.data.response_sender.firebase.FBSResponseSender;
import com.vision.common.services.surveillance.data.responses_handler.firebase.FBSResponsesHandler;
import com.vision.common.services.surveillance.data.service_requests.SurveillanceServiceRequests;

import java.util.List;

public class SurveillanceService implements ServiceResponseSender, ServiceRequestSender {
    private static SurveillanceService sInstance;

    private String mCurrentGroupName;
    private String mCurrentGroupPassword;
    private String mCurrentDeviceName;

    private ForegroundServiceWork mForegroundServiceWork;
    private ServiceRequestsHandler mRequestsHandler;
    private SurveillanceServiceRequests mRequests;
    private ServiceRequestSender mRequestsSender;

    private ServiceResponsesHandler mResponsesHandler;
    private ServiceResponseSender mResponseSender;

    private SurveillanceService() {
        mRequests = new SurveillanceServiceRequests();

        mRequestsHandler = new FBSRequestsHandler();
        mRequestsSender = new FBSRequestSender();

        mResponsesHandler = new FBSResponsesHandler();
        mResponseSender = new FBSResponseSender();
    }

    public static synchronized SurveillanceService get() {
        if (sInstance == null) {
            sInstance = new SurveillanceService();
        }

        return sInstance;
    }

    public String currentGroupName() {
        return mCurrentGroupName;
    }

    public String currentGroupPassword() {
        return mCurrentGroupPassword;
    }

    public String currentDeviceName() {
        return mCurrentDeviceName;
    }

    public void setCurrentUserData(String groupName, String groupPassword, String deviceName) {
        mCurrentGroupName = groupName;
        mCurrentGroupPassword = groupPassword;
        mCurrentDeviceName = deviceName;
    }

    public void clearCurrentUserData() {
        mCurrentGroupName = null;
        mCurrentGroupPassword = null;
        mCurrentDeviceName = null;
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
    public void sendRequest(String groupName,
                            String groupPassword,
                            String receiverDeviceName,
                            ServiceRequest request,
                            OnDeliveredCallback deliveredCallback,
                            OnResponseCallback onResponseCallback,
                            OnErrorCallback onErrorCallback) {
        Log.d("tag", "SurveillanceService->sendRequest()");

        mRequestsSender.sendRequest(
                groupName,
                groupPassword,
                receiverDeviceName,
                request,
                deliveredCallback,
                onResponseCallback,
                onErrorCallback
        );
    }

    @Override
    public void sendResponse(String groupName,
                             String groupPassword,
                             String receiverDeviceName,
                             ServiceResponse response) {
        Log.d("tag", "SurveillanceService->sendResponse()");

        mResponseSender.sendResponse(
                groupName,
                groupPassword,
                receiverDeviceName,
                response
        );
    }

    public ForegroundServiceWork foregroundServiceWork() {
        List<String> currentRequestsPath = FBSPathsService.get().requestsPath(
                mCurrentGroupName,
                mCurrentGroupPassword,
                mCurrentDeviceName
        );

        mForegroundServiceWork = new FBSForegroundServiceWork(mRequestsHandler, currentRequestsPath);
        return mForegroundServiceWork;
    }
}
