package com.vision.common.services.surveillance;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.vision.android_services.foreground.surveillance.SurveillanceForegroundService;
import com.vision.common.constants.AppConstants;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.common.data.service_notification.ServiceNotification;
import com.vision.common.interfaces.service_communication_manager.ServiceCommunicationManager;
import com.vision.common.interfaces.service_notification_sender.ServiceNotificationSender;
import com.vision.common.interfaces.service_request_interrupter.ServiceRequestInterrupter;
import com.vision.common.interfaces.service_responses_handler.ServiceResponsesExecutor;
import com.vision.common.services.auth.AuthService;
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
import com.vision.common.services.surveillance.data.service_errors.external_service_errors_mapper.ExternalServiceErrorsMapper;

import java.util.List;

public class SurveillanceService implements
        ServiceResponseSender,
        ServiceRequestSender,
        ServiceRequestInterrupter,
        ServiceNotificationSender {
    private final String SERVICE_WAKE_LOCK_TAG = "Vision:ServiceWakeLockTag";

    private static SurveillanceService sInstance;

    private ExternalServiceErrorsMapper mErrorsMapper;
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
        mErrorsMapper = new ExternalServiceErrorsMapper();
    }

    public static synchronized SurveillanceService get() {
        if (sInstance == null) {
            sInstance = new SurveillanceService();
        }

        return sInstance;
    }

    public void createGroupWithDevice(Context context,
                                      String groupName,
                                      String groupPassword,
                                      String deviceName,
                                      OnTaskSuccess<Void> onSuccess,
                                      OnTaskError<ServiceError> onError) {
        OnTaskSuccess<Void> successCallback = (data) -> {
            init(context);
            onSuccess.onSuccess(null);
        };

        OnTaskError<ServiceError> errorCallback = (error) -> {
            onError.onError(
                    mErrorsMapper.mapToSurveillanceServiceError(
                            ExternalServiceErrorsMapper.AUTH_SERVICE_TYPE,
                            error
                    )
            );
        };

        AuthService.get().createGroupWithDevice(
                context, groupName, groupPassword, deviceName, successCallback, errorCallback
        );
    }

    public void registerDeviceInGroup(Context context,
                                      String groupName,
                                      String groupPassword,
                                      String deviceName,
                                      OnTaskSuccess<Void> onSuccess,
                                      OnTaskError<ServiceError> onError) {
        OnTaskSuccess<Void> successCallback = (data) -> {
            init(context);
            onSuccess.onSuccess(null);
        };

        OnTaskError<ServiceError> errorCallback = (error) -> {
            onError.onError(
                    mErrorsMapper.mapToSurveillanceServiceError(
                            ExternalServiceErrorsMapper.AUTH_SERVICE_TYPE,
                            error
                    )
            );
        };

        AuthService.get().registerDeviceInGroup(
                context, groupName, groupPassword, deviceName, successCallback, errorCallback
        );
    }

    public void loginDeviceInGroup(Context context,
                                   String groupName,
                                   String groupPassword,
                                   String deviceName,
                                   OnTaskSuccess<Void> onSuccess,
                                   OnTaskError<ServiceError> onError) {
        OnTaskSuccess<Void> successCallback = (data) -> {
            init(context);
            onSuccess.onSuccess(null);
        };

        OnTaskError<ServiceError> errorCallback = (error) -> {
            onError.onError(
                    mErrorsMapper.mapToSurveillanceServiceError(
                            ExternalServiceErrorsMapper.AUTH_SERVICE_TYPE,
                            error
                    )
            );
        };

        AuthService.get().loginDeviceInGroup(
                context, groupName, groupPassword, deviceName, successCallback, errorCallback
        );
    }

    public void logoutDeviceFromGroup(Context context,
                                      OnTaskSuccess<Void> onSuccess,
                                      OnTaskError<ServiceError> onError) {
        OnTaskSuccess<Void> successCallback = (data) -> {
            onSuccess.onSuccess(null);
        };

        OnTaskError<ServiceError> errorCallback = (error) -> {
            onError.onError(
                    mErrorsMapper.mapToSurveillanceServiceError(
                            ExternalServiceErrorsMapper.AUTH_SERVICE_TYPE,
                            error
                    )
            );
        };

        dispose(context);
        AuthService.get().logoutDeviceFromGroup(successCallback, errorCallback);
    }

    public boolean isInitialized() {
        return AuthService.get().isLoggedIn();
    }

    public String currentGroupName() {
        return AuthService.get().currentGroupName();
    }

    public String currentGroupPassword() {
        return AuthService.get().currentGroupPassword();
    }

    public String currentDeviceName() {
        return AuthService.get().currentDeviceName();
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
        return mCommunicationManager.cancelRequest(requestId);
    }

    @Override
    public void sendResponse(String groupName,
                             String groupPassword,
                             String receiverDeviceName,
                             ServiceResponse response) {
        mCommunicationManager.sendResponse(groupName, groupPassword, receiverDeviceName, response);
    }

    @Override
    public void sendNotificationToAll(ServiceNotification notification) {
        mNotificationSender.sendNotificationToAll(notification);
    }

    public ForegroundServiceWork foregroundServiceWork() {
        mForegroundServiceWork = new FBSForegroundServiceWork(mCommunicationManager);
        return mForegroundServiceWork;
    }

    // ===
    // =====
    private void init(Context context) {
        mCurrentServiceMode = AppConstants.DEVICE_MODE_USER;

        List<String> currentRequestsPath = FBSPathsService.get().requestsPath(
                currentGroupName(),
                currentGroupPassword(),
                currentDeviceName()
        );
        List<String> currentResponsesPath = FBSPathsService.get().responsesPath(
                currentGroupName(),
                currentGroupPassword(),
                currentDeviceName()
        );
        List<String> currentUpdateFieldPath = FBSPathsService.get().updateFieldPath(
                currentGroupName(),
                currentGroupPassword(),
                currentDeviceName()
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

    private void dispose(Context context) {
        List<String> currentUpdateFieldPath = FBSPathsService.get().updateFieldPath(
                currentGroupName(),
                currentGroupPassword(),
                currentDeviceName()
        );
        FBSService.get().setStringValue(
                currentUpdateFieldPath,
                String.valueOf(-1)
        );

        stopListenToResponses(context);
        stopForegroundService(context);
        mCommunicationManager.stopIsAliveSignaling(context);
    }
    // =====
    // ===
}
