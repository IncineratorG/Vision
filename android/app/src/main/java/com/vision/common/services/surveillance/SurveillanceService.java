package com.vision.common.services.surveillance;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.vision.android_services.foreground.surveillance.SurveillanceForegroundService;
import com.vision.common.constants.AppConstants;
import com.vision.common.data.service_notification.ServiceNotification;
import com.vision.common.interfaces.service_communication_manager.ServiceCommunicationManager;
import com.vision.common.interfaces.service_notification_sender.ServiceNotificationSender;
import com.vision.common.interfaces.service_request_interrupter.ServiceRequestInterrupter;
import com.vision.common.interfaces.service_responses_handler.ServiceResponsesExecutor;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.interfaces.foregroun_service_work.ForegroundServiceWork;
import com.vision.common.services.surveillance.data.communication_manager.firebase.FBSCommunicationManager;
import com.vision.common.services.surveillance.data.foreground_service_work.firebase.FBSForegroundServiceWork;
import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestDeliveredCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestErrorCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestResponseCallback;
import com.vision.common.interfaces.service_request_sender.ServiceRequestSender;
import com.vision.common.services.surveillance.data.notifications.sender.firebase.FBSNotificationSender;
import com.vision.common.services.surveillance.data.requests.sender.firebase.FBSRequestSender;
import com.vision.common.interfaces.service_requests_handler.ServiceRequestsExecutor;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_response_sender.ServiceResponseSender;
import com.vision.common.services.surveillance.data.requests.executor.firebase.FBSRequestsExecutor;
import com.vision.common.services.surveillance.data.responses.sender.firebase.FBSResponseSender;
import com.vision.common.services.surveillance.data.responses.executor.firebase.FBSResponsesExecutor;

import java.util.List;

public class SurveillanceService implements
        ServiceResponseSender,
        ServiceRequestSender,
        ServiceRequestInterrupter,
        ServiceNotificationSender {
    private final String SERVICE_WAKE_LOCK_TAG = "Vision:ServiceWakeLockTag";

    private static SurveillanceService sInstance;

    private String mCurrentGroupName;
    private String mCurrentGroupPassword;
    private String mCurrentDeviceName;
    private String mCurrentServiceMode = AppConstants.DEVICE_MODE_UNKNOWN;

    private ForegroundServiceWork mForegroundServiceWork;
    private ServiceRequestsExecutor mRequestsExecutor;
    private ServiceRequestSender mRequestsSender;

    private ServiceResponsesExecutor mResponsesExecutor;
    private ServiceResponseSender mResponseSender;

    private ServiceNotificationSender mNotificationSender;

    private ServiceCommunicationManager mCommunicationManager;

    private PowerManager.WakeLock mServiceWakeLock;

    private SurveillanceService() {

    }

    public static synchronized SurveillanceService get() {
        if (sInstance == null) {
            sInstance = new SurveillanceService();
        }

        return sInstance;
    }

    public void init(Context context, String groupName, String groupPassword, String deviceName) {
        mCurrentGroupName = groupName;
        mCurrentGroupPassword = groupPassword;
        mCurrentDeviceName = deviceName;
        mCurrentServiceMode = AppConstants.DEVICE_MODE_USER;

        List<String> currentRequestsPath = FBSPathsService.get().requestsPath(
                mCurrentGroupName,
                mCurrentGroupPassword,
                mCurrentDeviceName
        );
        List<String> currentResponsesPath = FBSPathsService.get().responsesPath(
                mCurrentGroupName,
                mCurrentGroupPassword,
                mCurrentDeviceName
        );
        List<String> currentUpdateFieldPath = FBSPathsService.get().updateFieldPath(
                mCurrentGroupName,
                mCurrentGroupPassword,
                mCurrentDeviceName
        );

        mRequestsExecutor = new FBSRequestsExecutor();
        mRequestsSender = new FBSRequestSender();

        mResponsesExecutor = new FBSResponsesExecutor();
        mResponseSender = new FBSResponseSender();

        mNotificationSender = new FBSNotificationSender(context);

        mCommunicationManager = new FBSCommunicationManager(
                mRequestsExecutor,
                mResponsesExecutor,
                mRequestsSender,
                mResponseSender,
                mNotificationSender,
                currentRequestsPath,
                currentResponsesPath,
                currentUpdateFieldPath
        );

        mCommunicationManager.startIsAliveSignaling(context);
        startListenToResponses(context);
    }

    public boolean isInitialized() {
        return mCurrentGroupName != null &&
                !mCurrentGroupName.isEmpty() &&
                mCurrentGroupPassword != null &&
                !mCurrentGroupPassword.isEmpty() &&
                mCurrentDeviceName != null &&
                !mCurrentDeviceName.isEmpty();
    }

    public void dispose(Context context) {
        List<String> currentUpdateFieldPath = FBSPathsService.get().updateFieldPath(
                mCurrentGroupName,
                mCurrentGroupPassword,
                mCurrentDeviceName
        );
        FBSService.get().setStringValue(
                currentUpdateFieldPath,
                String.valueOf(-1)
        );

        mCurrentGroupName = null;
        mCurrentGroupPassword = null;
        mCurrentDeviceName = null;

        stopListenToResponses(context);
        stopForegroundService(context);
        mCommunicationManager.stopIsAliveSignaling(context);

        // ===
//        CameraService.get().dispose(context);
        // ===
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

    public String currentServiceMode() {
        return mCurrentServiceMode;
    }

    private void startListenToResponses(Context context) {
        if (mCommunicationManager == null) {
            Log.d("tag", "SurveillanceService->startListenToResponses(): COMMUNICATION_MANAGER_NOT_INITIALIZED");
            return;
        }

        mCommunicationManager.startResponsesListener(context);
    }

    private void stopListenToResponses(Context context) {
        if (mCommunicationManager == null) {
            Log.d("tag", "SurveillanceService->stopListenToResponses(): COMMUNICATION_MANAGER_NOT_INITIALIZED");
            return;
        }

        mCommunicationManager.stopResponsesListener(context);
    }

    public void startForegroundService(Context context) {
        Log.d("tag", "SurveillanceService->startForegroundService()");

        if (context == null) {
            Log.d("tag", "SurveillanceService->startForegroundService(): CONTEXT_IS_NULL");
            return;
        }

        if (isForegroundServiceRunning(context)) {
            Log.d("tag", "SurveillanceService->startForegroundService(): SERVICE_IS_ALREADY_RUNNING");
            return;
        }

        if (mServiceWakeLock != null && mServiceWakeLock.isHeld()) {
            mServiceWakeLock.release();
            mServiceWakeLock = null;
        }

        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        mServiceWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, SERVICE_WAKE_LOCK_TAG);
        mServiceWakeLock.acquire();

        mCurrentServiceMode = AppConstants.DEVICE_MODE_SERVICE;

        Intent serviceIntent = new Intent(context, SurveillanceForegroundService.class);
        serviceIntent.setAction("start");
        context.startService(serviceIntent);
    }

    public void stopForegroundService(Context context) {
        Log.d("tag", "SurveillanceService->stopForegroundService()");

        if (context == null) {
            Log.d("tag", "SurveillanceService->stopForegroundService(): CONTEXT_IS_NULL");
            return;
        }

        if (!isForegroundServiceRunning(context)) {
            Log.d("tag", "SurveillanceService->stopForegroundService(): SERVICE_ALREADY_NOT_RUNNING");
            return;
        }

        if ((mServiceWakeLock != null) && (mServiceWakeLock.isHeld())) {
            mServiceWakeLock.release();
            mServiceWakeLock = null;
        }

        mCurrentServiceMode = AppConstants.DEVICE_MODE_USER;

        Intent serviceIntent = new Intent(context, SurveillanceForegroundService.class);
        serviceIntent.setAction("stop");
        context.startService(serviceIntent);
    }

    public boolean isForegroundServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (SurveillanceForegroundService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void sendRequest(String groupName,
                            String groupPassword,
                            String receiverDeviceName,
                            ServiceRequest request,
                            OnRequestDeliveredCallback onDeliveredCallback,
                            OnRequestResponseCallback onResponseCallback,
                            OnRequestErrorCallback onErrorCallback) {
        Log.d("tag", "SurveillanceService->sendRequest()");

        mCommunicationManager.sendRequest(
                groupName,
                groupPassword,
                receiverDeviceName,
                request,
                onDeliveredCallback,
                onResponseCallback,
                onErrorCallback
        );
    }

    @Override
    public boolean cancelRequest(String requestId) {
        Log.d("tag", "SurveillanceService->cancelRequest()");

        return mCommunicationManager.cancelRequest(requestId);
    }

    @Override
    public void sendResponse(String groupName,
                             String groupPassword,
                             String receiverDeviceName,
                             ServiceResponse response) {
        Log.d("tag", "SurveillanceService->sendResponse()");

        mCommunicationManager.sendResponse(groupName, groupPassword, receiverDeviceName, response);
    }

    @Override
    public void sendNotificationToAll(ServiceNotification notification) {
        Log.d("tag", "SurveillanceService->sendNotificationToAll");

        mNotificationSender.sendNotificationToAll(notification);
    }

    public ForegroundServiceWork foregroundServiceWork() {
        mForegroundServiceWork = new FBSForegroundServiceWork(mCommunicationManager);
        return mForegroundServiceWork;
    }
}
