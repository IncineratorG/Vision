package com.vision.services.surveillance;

import android.content.Context;
import android.util.Log;

import com.vision.common.constants.AppConstants;
import com.vision.common.data.hybrid_objects.authentication_data.AuthenticationData;
import com.vision.common.data.hybrid_objects.device_info_list.DeviceInfoList;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.common.data.service_notification.ServiceNotification;
import com.vision.common.interfaces.service_notification_sender.ServiceNotificationSender;
import com.vision.common.interfaces.service_notifications_executor.ServiceNotificationsExecutor;
import com.vision.common.interfaces.service_request_interrupter.ServiceRequestInterrupter;
import com.vision.common.interfaces.foreground_service_work.ForegroundServiceWork;
import com.vision.common.interfaces.service_state.ServiceState;
import com.vision.common.interfaces.service_state_change_listener.ServiceStateChangeListener;
import com.vision.services.app_storages.AppStorages;
import com.vision.services.camera.CameraService;
import com.vision.services.camera.data.state.CameraServiceState;
import com.vision.services.device_movement.DeviceMovementService;
import com.vision.services.device_movement.data.state.DeviceMovementServiceState;
import com.vision.services.surveillance.foreground_service_work.firebase.FBSForegroundServiceWork;
import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestDeliveredCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestErrorCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestResponseCallback;
import com.vision.common.interfaces.service_request_sender.ServiceRequestSender;
import com.vision.services.surveillance.notifications_manager.firebase.FBSNotificationsManager;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_response_sender.ServiceResponseSender;
import com.vision.services.surveillance.data.service_errors.external_service_errors_mapper.ExternalServiceErrorsMapper;
import com.vision.services.surveillance.data.internal_data.SurveillanceServiceInternalData;
import com.vision.services.surveillance.service_internal_tasks.task_results.SurveillanceServiceInternalTaskResults;
import com.vision.services.surveillance.service_internal_tasks.tasks.SurveillanceServiceInternalTasks;
import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.get_current_device_name.GetCurrentDeviceNameTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.get_current_group_name.GetCurrentGroupNameTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.get_current_group_password.GetCurrentGroupPasswordTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.detect_device_movement.is_detect_device_movement_service_running.IsDetectDeviceMovementServiceRunningTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.foreground_service.is_foreground_service_running.IsForegroundServiceRunningTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.init_and_dispose_service.get_current_service_mode.GetCurrentServiceModeTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.init_and_dispose_service.is_service_initialized.IsServiceInitializedTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.recognize_person_with_camera.is_recognize_person_with_camera_service_running.IsRecognizePersonWithCameraServiceRunningTask;

public class SurveillanceService implements
        ServiceResponseSender,
        ServiceRequestSender,
        ServiceRequestInterrupter,
        ServiceNotificationSender {
    public static final String NAME = "SurveillanceService";

    private static SurveillanceService sInstance;

    private SurveillanceServiceInternalData mInternalData;

    private SurveillanceService() {
        mInternalData = SurveillanceServiceInternalData.get();

        mInternalData.currentServiceMode = AppConstants.DEVICE_MODE_UNKNOWN;
        mInternalData.errorsMapper = new ExternalServiceErrorsMapper();

        // ===
        DeviceMovementService.get().addStateChangeListener((context, state) -> {
            Log.d("tag", "SurveillanceService->DEVICE_MOVEMENT_SERVICE_STATE_CHANGED");

            if (state == null) {
                Log.d("tag", "SurveillanceService->DEVICE_MOVEMENT_SERVICE_STATE_CHANGED->STATE_IS_NULL");
                return;
            }

            if (state instanceof DeviceMovementServiceState) {
                AppStorages.get().surveillanceStorage().saveServiceState(context, state);
            } else {
                Log.d("tag", "SurveillanceService->DEVICE_MOVEMENT_SERVICE_STATE_CHANGED->BAD_STATE_INSTANCE: " + state.stateId());
            }
        });

        CameraService.get().addStateChangeListener((context, state) -> {
            Log.d("tag", "SurveillanceService->CAMERA_SERVICE_STATE_CHANGED");

            if (state == null) {
                Log.d("tag", "SurveillanceService->CAMERA_SERVICE_STATE_CHANGED->STATE_IS_NULL");
                return;
            }

            if (state instanceof CameraServiceState) {
                AppStorages.get().surveillanceStorage().saveServiceState(context, state);
            } else {
                Log.d("tag", "SurveillanceService->CAMERA_SERVICE_STATE_CHANGED->BAD_STATE_INSTANCE: " + state.stateId());
            }
        });
        // ===
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
        SurveillanceServiceInternalTasks.createGroupWithDeviceTask(
                context, groupName, groupPassword, deviceName, onSuccess, onError
        ).run(null);
    }

    public void registerDeviceInGroup(Context context,
                                      String groupName,
                                      String groupPassword,
                                      String deviceName,
                                      OnTaskSuccess<Void> onSuccess,
                                      OnTaskError<ServiceError> onError) {
        SurveillanceServiceInternalTasks.registerDeviceInGroupTask(
                context, groupName, groupPassword, deviceName, onSuccess, onError
        ).run(null);
    }

    public void loginDeviceInGroup(Context context,
                                   String groupName,
                                   String groupPassword,
                                   String deviceName,
                                   OnTaskSuccess<Void> onSuccess,
                                   OnTaskError<ServiceError> onError) {
        SurveillanceServiceInternalTasks.loginDeviceInGroupTask(
                context, groupName, groupPassword, deviceName, onSuccess, onError
        ).run(null);
    }

    public void logoutDeviceFromGroup(Context context,
                                      OnTaskSuccess<Void> onSuccess,
                                      OnTaskError<ServiceError> onError) {
        SurveillanceServiceInternalTasks.logoutDeviceFromGroupTask(
                context,
                currentGroupName(),
                currentGroupPassword(),
                currentDeviceName(),
                onSuccess,
                onError
        ).run(null);
    }

    public void restoreService(Context context,
                               OnTaskSuccess<Void> onSuccess,
                               OnTaskError<ServiceError> onError) {
        SurveillanceServiceInternalTasks.restoreServiceTask(
                context,
                onSuccess,
                onError
        ).run(null);
    }

//    public void restoreDeviceInGroup(Context context,
//                                     AuthenticationData lastAuthenticationData,
//                                     OnTaskSuccess<Void> onSuccess,
//                                     OnTaskError<ServiceError> onError) {
//        SurveillanceServiceInternalTasks.restoreDeviceInGroupTask(
//                context,
//                lastAuthenticationData.groupName(),
//                lastAuthenticationData.groupPassword(),
//                lastAuthenticationData.deviceName(),
//                onSuccess,
//                onError
//        ).run(null);
//    }

//    public void logoutDeviceFromGroup(Context context,
//                                      AuthenticationData lastAuthenticationData,
//                                      OnTaskSuccess<Void> onSuccess,
//                                      OnTaskError<ServiceError> onError) {
//        SurveillanceServiceInternalTasks.logoutDeviceFromGroupTask(
//                context,
//                lastAuthenticationData.groupName(),
//                lastAuthenticationData.groupPassword(),
//                lastAuthenticationData.deviceName(),
//                onSuccess,
//                onError
//        ).run(null);
//    }

    public boolean isInitialized() {
        IsServiceInitializedTask task =
                SurveillanceServiceInternalTasks.isServiceInitializedTask();

        return SurveillanceServiceInternalTaskResults.result(task, task.run(null));
    }

    public void getDevicesInGroup(Context context,
                                  String groupName,
                                  String groupPassword,
                                  OnTaskSuccess<DeviceInfoList> onSuccess,
                                  OnTaskError<ServiceError> onError) {
        SurveillanceServiceInternalTasks.getDevicesInGroupTask(
                context, groupName, groupPassword, onSuccess, onError
        ).run(null);
    }

    public String currentGroupName() {
        GetCurrentGroupNameTask task =
                SurveillanceServiceInternalTasks.getCurrentGroupNameTask();

        return SurveillanceServiceInternalTaskResults.result(task, task.run(null));
    }

    public String currentGroupPassword() {
        GetCurrentGroupPasswordTask task =
                SurveillanceServiceInternalTasks.getCurrentGroupPasswordTask();

        return SurveillanceServiceInternalTaskResults.result(task, task.run(null));
    }

    public String currentDeviceName() {
        GetCurrentDeviceNameTask task =
                SurveillanceServiceInternalTasks.getCurrentDeviceNameTask();

        return SurveillanceServiceInternalTaskResults.result(task, task.run(null));
    }

    public String currentServiceMode() {
        GetCurrentServiceModeTask task =
                SurveillanceServiceInternalTasks.getCurrentServiceModeTask();

        return SurveillanceServiceInternalTaskResults.result(task, task.run(null));
    }

    public void startForegroundService(Context context,
                                       OnTaskSuccess<Void> onSuccess,
                                       OnTaskError<ServiceError> onError) {
        Log.d("tag", "SurveillanceService->startForegroundService()");

        SurveillanceServiceInternalTasks.startForegroundServiceTask(
                context,
                true,
                currentGroupName(),
                currentGroupPassword(),
                currentDeviceName(),
                onSuccess,
                onError
        ).run(null);
    }

    public void stopForegroundService(Context context,
                                      OnTaskSuccess<Void> onSuccess,
                                      OnTaskError<ServiceError> onError) {
        Log.d("tag", "SurveillanceService->stopForegroundService()");

        SurveillanceServiceInternalTasks.stopForegroundServiceTask(
                context,
                true,
                currentGroupName(),
                currentGroupPassword(),
                currentDeviceName(),
                onSuccess,
                onError
        ).run(null);
    }

    public boolean isForegroundServiceRunning(Context context) {
        IsForegroundServiceRunningTask task =
                SurveillanceServiceInternalTasks.isForegroundServiceRunningTask(context);

        return SurveillanceServiceInternalTaskResults.result(task, task.run(null));
    }

    public void startDetectDeviceMovement(Context context,
                                          OnTaskSuccess<Void> onSuccess,
                                          OnTaskError<ServiceError> onError) {
        Log.d("tag", "SurveillanceService->startDetectDeviceMovement()");

        SurveillanceServiceInternalTasks.startDetectDeviceMovementTask(
                context,
                false,
                currentGroupName(),
                currentGroupPassword(),
                currentDeviceName(),
                currentServiceMode(),
                onSuccess,
                onError
        ).run(null);
    }

    public void stopDetectDeviceMovement(Context context,
                                         OnTaskSuccess<Void> onSuccess,
                                         OnTaskError<ServiceError> onError) {
        Log.d("tag", "SurveillanceService->stopDetectDeviceMovement()");

        SurveillanceServiceInternalTasks.stopDetectDeviceMovementTask(
                context,
                false,
                currentGroupName(),
                currentGroupPassword(),
                currentDeviceName(),
                currentServiceMode(),
                onSuccess,
                onError
        ).run(null);
    }

    public boolean isDetectDeviceMovementServiceRunning() {
        Log.d("tag", "SurveillanceService->isDetectDeviceMovementServiceRunning()");

        IsDetectDeviceMovementServiceRunningTask task =
                SurveillanceServiceInternalTasks.isDetectDeviceMovementServiceRunningTask();

        return SurveillanceServiceInternalTaskResults.result(task, task.run(null));
    }

    // ===
    // =====
    public void startRecognizePersonWithCamera(Context context,
                                               String cameraType,
                                               int imageRotationDegrees,
                                               OnTaskSuccess<Void> onSuccess,
                                               OnTaskError<ServiceError> onError) {
        Log.d("tag", "SurveillanceService->startRecognizePersonWithCamera(): " + cameraType);

        SurveillanceServiceInternalTasks.startRecognizePersonWithCameraTask(
                context,
                cameraType,
                imageRotationDegrees,
                false,
                currentGroupName(),
                currentGroupPassword(),
                currentDeviceName(),
                currentServiceMode(),
                onSuccess,
                onError
        ).run(null);
    }

    public void stopRecognizePersonWithCamera(Context context,
                                              String cameraType,
                                              OnTaskSuccess<Void> onSuccess,
                                              OnTaskError<ServiceError> onError) {
        Log.d("tag", "SurveillanceService->stopRecognizePersonWithCamera(): " + cameraType);

        SurveillanceServiceInternalTasks.stopRecognizePersonWithCameraTask(
                context,
                cameraType,
                false,
                currentGroupName(),
                currentGroupPassword(),
                currentDeviceName(),
                currentServiceMode(),
                onSuccess,
                onError
        ).run(null);
    }

    public boolean isRecognizePersonWithCameraServiceRunning(String cameraType) {
        IsRecognizePersonWithCameraServiceRunningTask task =
                SurveillanceServiceInternalTasks.isRecognizePersonWithCameraServiceRunningTask(cameraType);

        return SurveillanceServiceInternalTaskResults.result(task, task.run(null));
    }

    public boolean isRecognizePersonWithFrontCameraServiceRunning() {
        return isRecognizePersonWithCameraServiceRunning("front");
    }

    public boolean isRecognizePersonWithBackCameraServiceRunning() {
        return isRecognizePersonWithCameraServiceRunning("back");
    }
    // =====
    // ===

    public ServiceNotificationsExecutor notificationsExecutor() {
        if (mInternalData.notificationsManager == null) {
            mInternalData.notificationsManager = new FBSNotificationsManager();
        }

        return mInternalData.notificationsManager;
    }

    public ForegroundServiceWork foregroundServiceWork() {
        mInternalData.foregroundServiceWork = new FBSForegroundServiceWork(mInternalData.communicationManager);
        return mInternalData.foregroundServiceWork;
    }

    @Override
    public void sendRequest(String groupName,
                            String groupPassword,
                            String receiverDeviceName,
                            ServiceRequest request,
                            OnRequestDeliveredCallback onDeliveredCallback,
                            OnRequestResponseCallback onResponseCallback,
                            OnRequestErrorCallback onErrorCallback) {
        mInternalData.communicationManager.sendRequest(
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
        return mInternalData.communicationManager.cancelRequest(requestId);
    }

    @Override
    public void sendResponse(String groupName,
                             String groupPassword,
                             String receiverDeviceName,
                             ServiceResponse response) {
        mInternalData.communicationManager.sendResponse(groupName, groupPassword, receiverDeviceName, response);
    }

    @Override
    public void sendNotificationToAll(Context context, ServiceNotification notification) {
        if (mInternalData.notificationsManager == null) {
            mInternalData.notificationsManager = new FBSNotificationsManager();
        }
        mInternalData.notificationsManager.sendNotificationToAll(context, notification);
    }
}

//package com.vision.services.surveillance;
//
//import android.app.ActivityManager;
//import android.content.Context;
//import android.content.Intent;
//import android.os.PowerManager;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.ValueEventListener;
//import com.vision.android_services.foreground.surveillance.SurveillanceForegroundService;
//import com.vision.common.constants.AppConstants;
//import com.vision.common.data.hybrid_objects.device_info_list.DeviceInfoList;
//import com.vision.common.data.hybrid_service_objects.device_info.DeviceInfo;
//import com.vision.common.data.service_error.ServiceError;
//import com.vision.common.data.service_generic_callbacks.OnTaskError;
//import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
//import com.vision.common.data.service_notification.ServiceNotification;
//import com.vision.common.interfaces.service_communication_manager.ServiceCommunicationManager;
//import com.vision.common.interfaces.service_notification_sender.ServiceNotificationSender;
//import com.vision.common.interfaces.service_notifications_executor.ServiceNotificationsExecutor;
//import com.vision.common.interfaces.service_notifications_manager.ServiceNotificationsManager;
//import com.vision.common.interfaces.service_request_interrupter.ServiceRequestInterrupter;
//import com.vision.common.interfaces.service_responses_executor.ServiceResponsesExecutor;
//import com.vision.services.app_storages.AppStorages;
//import com.vision.services.app_storages.surveillance.SurveillanceStorage;
//import com.vision.services.auth.AuthService;
//import com.vision.services.camera.CameraService;
//import com.vision.services.device_group.DeviceGroupService;
//import com.vision.services.device_info.DeviceInfoService;
//import com.vision.services.device_movement.DeviceMovementService;
//import com.vision.services.firebase_communication.FBSCommunicationService;
//import com.vision.services.firebase_messaging.FBSMessagingService;
//import com.vision.services.firebase_paths.FBSPathsService;
//import com.vision.common.interfaces.foreground_service_work.ForegroundServiceWork;
//import com.vision.services.surveillance.communication_manager.firebase.FBSCommunicationManager;
//import com.vision.services.surveillance.foreground_service_work.firebase.FBSForegroundServiceWork;
//import com.vision.common.data.service_request.ServiceRequest;
//import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestDeliveredCallback;
//import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestErrorCallback;
//import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestResponseCallback;
//import com.vision.common.interfaces.service_request_sender.ServiceRequestSender;
//import com.vision.services.surveillance.notifications.SurveillanceServiceNotifications;
//import com.vision.services.surveillance.notifications_manager.firebase.FBSNotificationsManager;
//import com.vision.services.surveillance.requests.sender.firebase.FBSRequestSender;
//import com.vision.common.interfaces.service_requests_executor.ServiceRequestsExecutor;
//import com.vision.common.data.service_response.ServiceResponse;
//import com.vision.common.interfaces.service_response_sender.ServiceResponseSender;
//import com.vision.services.surveillance.requests.executor.firebase.FBSRequestsExecutor;
//import com.vision.services.surveillance.responses.sender.firebase.FBSResponseSender;
//import com.vision.services.surveillance.responses.executor.firebase.FBSResponsesExecutor;
//import com.vision.services.surveillance.data.service_errors.SurveillanceServiceErrors;
//import com.vision.services.surveillance.data.service_errors.external_service_errors_mapper.ExternalServiceErrorsMapper;
//
//import java.util.List;
//
//public class SurveillanceService implements
//        ServiceResponseSender,
//        ServiceRequestSender,
//        ServiceRequestInterrupter,
//        ServiceNotificationSender {
//    public static final String NAME = "SurveillanceService";
//
//    private final String SERVICE_WAKE_LOCK_TAG = "Vision:ServiceWakeLockTag";
//
//    private static SurveillanceService sInstance;
//
//    private ExternalServiceErrorsMapper mErrorsMapper;
//    private String mCurrentServiceMode = AppConstants.DEVICE_MODE_UNKNOWN;
//    private ForegroundServiceWork mForegroundServiceWork;
//    private ServiceRequestsExecutor mRequestsExecutor;
//    private ServiceRequestSender mRequestsSender;
//    private ServiceResponsesExecutor mResponsesExecutor;
//    private ServiceResponseSender mResponseSender;
//    private ServiceCommunicationManager mCommunicationManager;
//    private ServiceNotificationsManager mNotificationsManager;
//    private PowerManager.WakeLock mServiceWakeLock;
//
//    private SurveillanceService() {
//        mErrorsMapper = new ExternalServiceErrorsMapper();
//    }
//
//    public static synchronized SurveillanceService get() {
//        if (sInstance == null) {
//            sInstance = new SurveillanceService();
//        }
//
//        return sInstance;
//    }
//
//    public void createGroupWithDevice(Context context,
//                                      String groupName,
//                                      String groupPassword,
//                                      String deviceName,
//                                      OnTaskSuccess<Void> onSuccess,
//                                      OnTaskError<ServiceError> onError) {
//        OnTaskSuccess<Void> successCallback = (data) -> {
//            OnTaskSuccess<Void> initSuccessCallback = (initData) -> onSuccess.onSuccess(null);
//            OnTaskError<ServiceError> initErrorCallback = (errorData) -> onError.onError(errorData);
//
//            init(context, groupName, groupPassword, deviceName, initSuccessCallback, initErrorCallback);
//        };
//
//        OnTaskError<ServiceError> errorCallback = (error) -> {
//            onError.onError(
//                    mErrorsMapper.mapToSurveillanceServiceError(
//                            ExternalServiceErrorsMapper.AUTH_SERVICE_TYPE,
//                            error
//                    )
//            );
//        };
//
//        AuthService.get().createGroupWithDevice(
//                context, groupName, groupPassword, deviceName, successCallback, errorCallback
//        );
//    }
//
//    public void registerDeviceInGroup(Context context,
//                                      String groupName,
//                                      String groupPassword,
//                                      String deviceName,
//                                      OnTaskSuccess<Void> onSuccess,
//                                      OnTaskError<ServiceError> onError) {
//        OnTaskSuccess<Void> successCallback = (data) -> {
//            OnTaskSuccess<Void> initSuccessCallback = (initData) -> onSuccess.onSuccess(null);
//            OnTaskError<ServiceError> initErrorCallback = (errorData) -> onError.onError(errorData);
//
//            init(context, groupName, groupPassword, deviceName, initSuccessCallback, initErrorCallback);
//        };
//
//        OnTaskError<ServiceError> errorCallback = (error) -> {
//            onError.onError(
//                    mErrorsMapper.mapToSurveillanceServiceError(
//                            ExternalServiceErrorsMapper.AUTH_SERVICE_TYPE,
//                            error
//                    )
//            );
//        };
//
//        AuthService.get().registerDeviceInGroup(
//                context, groupName, groupPassword, deviceName, successCallback, errorCallback
//        );
//    }
//
//    public void loginDeviceInGroup(Context context,
//                                   String groupName,
//                                   String groupPassword,
//                                   String deviceName,
//                                   OnTaskSuccess<Void> onSuccess,
//                                   OnTaskError<ServiceError> onError) {
//        OnTaskSuccess<Void> successCallback = (data) -> {
//            OnTaskSuccess<Void> initSuccessCallback = (initData) -> {
//                Log.d("tag", "SurveillanceService->loginDeviceInGroup(): 1");
//
//                onSuccess.onSuccess(null);
//            };
//            OnTaskError<ServiceError> initErrorCallback = (errorData) -> onError.onError(errorData);
//
//            init(context, groupName, groupPassword, deviceName, initSuccessCallback, initErrorCallback);
//        };
//
//        OnTaskError<ServiceError> errorCallback = (error) -> {
//            onError.onError(
//                    mErrorsMapper.mapToSurveillanceServiceError(
//                            ExternalServiceErrorsMapper.AUTH_SERVICE_TYPE,
//                            error
//                    )
//            );
//        };
//
//        AuthService.get().loginDeviceInGroup(
//                context, groupName, groupPassword, deviceName, successCallback, errorCallback
//        );
//    }
//
//    public void logoutDeviceFromGroup(Context context,
//                                      OnTaskSuccess<Void> onSuccess,
//                                      OnTaskError<ServiceError> onError) {
//        OnTaskSuccess<Void> successCallback = (data) -> {
//            onSuccess.onSuccess(null);
//        };
//
//        OnTaskError<ServiceError> errorCallback = (error) -> {
//            onError.onError(
//                    mErrorsMapper.mapToSurveillanceServiceError(
//                            ExternalServiceErrorsMapper.AUTH_SERVICE_TYPE,
//                            error
//                    )
//            );
//        };
//
//        dispose(context);
//        AuthService.get().logoutDeviceFromGroup(successCallback, errorCallback);
//    }
//
//    public boolean isInitialized() {
//        return AuthService.get().isLoggedIn();
//    }
//
//    public void getDevicesInGroup(Context context,
//                                  String groupName,
//                                  String groupPassword,
//                                  OnTaskSuccess<DeviceInfoList> onSuccess,
//                                  OnTaskError<ServiceError> onError) {
//        OnTaskError<ServiceError> errorCallback = (error) -> {
//            onError.onError(
//                    mErrorsMapper.mapToSurveillanceServiceError(
//                            ExternalServiceErrorsMapper.DEVICE_GROUP_SERVICE_TYPE,
//                            error
//                    )
//            );
//        };
//
//        DeviceGroupService.get().getDevicesInGroup(
//                context, groupName, groupPassword, onSuccess, errorCallback
//        );
//    }
//
//    public void subscribeToGlobalNotifications(Context context,
//                                               OnTaskSuccess<Void> onSuccess,
//                                               OnTaskError<ServiceError> onError) {
//        FBSMessagingService messagingService = FBSMessagingService.get();
//        SurveillanceStorage storage = AppStorages.get().surveillanceStorage();
//
//        String savedGlobalTopic = storage.getGlobalNotificationsTopic(context);
//        String currentGlobalTopic = messagingService.globalTopic(currentGroupName(), currentGroupPassword());
//
//        OnTaskSuccess<Void> successCallback = (data) -> {
//            Log.d("tag", "SurveillanceService->subscribeToGlobalNotifications()->successCallback");
//
//            storage.saveGlobalNotificationsTopic(context, currentGlobalTopic);
//            onSuccess.onSuccess(null);
//        };
//        OnTaskError<ServiceError> errorCallback = (error) -> {
//            onError.onError(
//                    mErrorsMapper.mapToSurveillanceServiceError(
//                            ExternalServiceErrorsMapper.FBS_MESSAGING_SERVICE_TYPE,
//                            error
//                    )
//            );
//        };
//
//        if (savedGlobalTopic == null) {
//            Log.d("tag", "SurveillanceService->subscribeToGlobalNotifications(): 1");
//
//            messagingService.subscribeToTopic(currentGlobalTopic, successCallback, errorCallback);
//        } else if (savedGlobalTopic.equals(currentGlobalTopic)) {
//            Log.d("tag", "SurveillanceService->subscribeToGlobalNotifications(): 2");
//
////            successCallback.onSuccess(null);
//            messagingService.subscribeToTopic(currentGlobalTopic, successCallback, errorCallback);
//        } else {
//            Log.d("tag", "SurveillanceService->subscribeToGlobalNotifications(): 3");
//
//            messagingService.unsubscribeFromTopic(
//                    savedGlobalTopic,
//                    (resultUnsubscribe) -> {
//                        Log.d("tag", "SurveillanceService->subscribeToGlobalNotifications(): 4");
//
//                        messagingService.subscribeToTopic(
//                                currentGlobalTopic,
//                                successCallback,
//                                errorCallback
//                        );
//                    },
//                    errorCallback
//            );
//        }
//    }
//
//    public void unsubscribeFromGlobalNotifications(Context context,
//                                                   OnTaskSuccess<Void> onSuccess,
//                                                   OnTaskError<ServiceError> onError) {
//        OnTaskSuccess<Void> successCallback = (data) -> onSuccess.onSuccess(null);
//        OnTaskError<ServiceError> errorCallback = (error) -> {
//            onError.onError(
//                    mErrorsMapper.mapToSurveillanceServiceError(
//                            ExternalServiceErrorsMapper.FBS_MESSAGING_SERVICE_TYPE,
//                            error
//                    )
//            );
//        };
//
//        SurveillanceStorage storage = AppStorages.get().surveillanceStorage();
//        storage.removeGlobalNotificationsTopic(context);
//
//        FBSMessagingService messagingService = FBSMessagingService.get();
//        String globalNotificationsTopic = messagingService.globalTopic(currentGroupName(), currentGroupPassword());
//        messagingService.unsubscribeFromTopic(globalNotificationsTopic, successCallback, errorCallback);
//    }
//
//    public String currentGroupName() {
//        return AuthService.get().currentGroupName();
//    }
//
//    public String currentGroupPassword() {
//        return AuthService.get().currentGroupPassword();
//    }
//
//    public String currentDeviceName() {
//        return AuthService.get().currentDeviceName();
//    }
//
//    public String currentServiceMode() {
//        return mCurrentServiceMode;
//    }
//
//    private void startListenToResponses(Context context) {
//        if (mCommunicationManager == null) {
//            Log.d("tag", "SurveillanceService->startListenToResponses(): COMMUNICATION_MANAGER_NOT_INITIALIZED");
//            return;
//        }
//
//        mCommunicationManager.startResponsesListener(context);
//    }
//
//    private void stopListenToResponses(Context context) {
//        if (mCommunicationManager == null) {
//            Log.d("tag", "SurveillanceService->stopListenToResponses(): COMMUNICATION_MANAGER_NOT_INITIALIZED");
//            return;
//        }
//
//        mCommunicationManager.stopResponsesListener(context);
//    }
//
//    public void startForegroundService(Context context,
//                                       OnTaskSuccess<Void> onSuccess,
//                                       OnTaskError<ServiceError> onError) {
//        Log.d("tag", "SurveillanceService->startForegroundService()");
//
//        if (context == null) {
//            Log.d("tag", "SurveillanceService->startForegroundService(): CONTEXT_IS_NULL");
//            return;
//        }
//
//        if (isForegroundServiceRunning(context)) {
//            Log.d("tag", "SurveillanceService->startForegroundService(): SERVICE_IS_ALREADY_RUNNING");
//            return;
//        }
//
//        if (mServiceWakeLock != null && mServiceWakeLock.isHeld()) {
//            mServiceWakeLock.release();
//            mServiceWakeLock = null;
//        }
//
//        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//
//        mServiceWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, SERVICE_WAKE_LOCK_TAG);
//        mServiceWakeLock.acquire();
//
//        mCurrentServiceMode = AppConstants.DEVICE_MODE_SERVICE;
//
//        Intent serviceIntent = new Intent(context, SurveillanceForegroundService.class);
//        serviceIntent.setAction("start");
//        context.startService(serviceIntent);
//
//        updateAndPublishDeviceInfo(context, true, onSuccess, onError);
//    }
//
//    public void stopForegroundService(Context context,
//                                      OnTaskSuccess<Void> onSuccess,
//                                      OnTaskError<ServiceError> onError) {
//        Log.d("tag", "SurveillanceService->stopForegroundService()");
//
//        if (context == null) {
//            Log.d("tag", "SurveillanceService->stopForegroundService(): CONTEXT_IS_NULL");
//            return;
//        }
//
//        if (!isForegroundServiceRunning(context)) {
//            Log.d("tag", "SurveillanceService->stopForegroundService(): SERVICE_ALREADY_NOT_RUNNING");
//            return;
//        }
//
//        if ((mServiceWakeLock != null) && (mServiceWakeLock.isHeld())) {
//            mServiceWakeLock.release();
//            mServiceWakeLock = null;
//        }
//
//        mCurrentServiceMode = AppConstants.DEVICE_MODE_USER;
//
//        Intent serviceIntent = new Intent(context, SurveillanceForegroundService.class);
//        serviceIntent.setAction("stop");
//        context.startService(serviceIntent);
//
//        // ===
//        // =====
//        DeviceMovementService.get().stop(context);
//        CameraService.get().stop(context);
//        // =====
//        // ===
//        updateAndPublishDeviceInfo(context, true, onSuccess, onError);
//    }
//
//    public boolean isForegroundServiceRunning(Context context) {
//        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
//            if (SurveillanceForegroundService.class.getName().equals(service.service.getClassName())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void startDetectDeviceMovement(Context context,
//                                          OnTaskSuccess<Void> onSuccess,
//                                          OnTaskError<ServiceError> onError) {
//        Log.d("tag", "SurveillanceService->startDetectDeviceMovement()");
//
//        OnTaskSuccess<Void> movementStartCallback = (data) -> {
//            Log.d("tag", "movementStartCallback()");
//
//            sendNotificationToAll(
//                    context,
//                    SurveillanceServiceNotifications.deviceMovementStartNotification(
//                            currentGroupName(),
//                            currentDeviceName()
//                    )
//            );
//        };
//        OnTaskSuccess<Void> movementEndCallback = (data) -> {
//            Log.d("tag", "movementEndCallback()");
//
//            sendNotificationToAll(
//                    context,
//                    SurveillanceServiceNotifications.deviceMovementEndNotification(
//                            currentGroupName(),
//                            currentDeviceName()
//                    )
//            );
//        };
//
//        DeviceMovementService.get().start(context, movementStartCallback, movementEndCallback);
//        updateAndPublishDeviceInfo(context, false, onSuccess, onError);
//    }
//
//    public void stopDetectDeviceMovement(Context context,
//                                         OnTaskSuccess<Void> onSuccess,
//                                         OnTaskError<ServiceError> onError) {
//        Log.d("tag", "SurveillanceService->stopDetectDeviceMovement()");
//
//        DeviceMovementService.get().stop(context);
//        updateAndPublishDeviceInfo(context, false, onSuccess, onError);
//    }
//
//    public boolean isDetectDeviceMovementServiceRunning() {
//        Log.d("tag", "SurveillanceService->isDetectDeviceMovementServiceRunning()");
//
//        return DeviceMovementService.get().isRunning();
//    }
//
//    // ===
//    // =====
//    public void startRecognizePersonWithCamera(Context context,
//                                               String cameraType,
//                                               int imageRotationDegrees,
//                                               OnTaskSuccess<Void> onSuccess,
//                                               OnTaskError<ServiceError> onError) {
//        Log.d("tag", "SurveillanceService->startRecognizePersonWithCamera(): " + cameraType);
//
//        CameraService cameraService = CameraService.get();
//        if (cameraType.equalsIgnoreCase("front")) {
//            cameraService.startRecognizePersonWithFrontCamera(context, imageRotationDegrees);
//            updateAndPublishDeviceInfo(context, false, onSuccess, onError);
//        } else if (cameraType.equalsIgnoreCase("back")) {
//            cameraService.startRecognizePersonWithBackCamera(context, imageRotationDegrees);
//            updateAndPublishDeviceInfo(context, false, onSuccess, onError);
//        } else {
//            Log.d("tag", "SurveillanceService->startRecognizePersonWithCamera()->UNKNOWN_CAMERA_TYPE: " + cameraType);
//            onError.onError(SurveillanceServiceErrors.commonServiceError());
//        }
//    }
//
//    public void stopRecognizePersonWithCamera(Context context,
//                                              String cameraType,
//                                              OnTaskSuccess<Void> onSuccess,
//                                              OnTaskError<ServiceError> onError) {
//        Log.d("tag", "SurveillanceService->stopRecognizePersonWithCamera(): " + cameraType);
//
//        CameraService cameraService = CameraService.get();
//        if (cameraType.equalsIgnoreCase("front")) {
//            cameraService.stopRecognizePersonWithFrontCamera();
//            updateAndPublishDeviceInfo(context, false, onSuccess, onError);
//        } else if (cameraType.equalsIgnoreCase("back")) {
//            cameraService.stopRecognizePersonWithBackCamera();
//            updateAndPublishDeviceInfo(context, false, onSuccess, onError);
//        } else {
//            Log.d("tag", "SurveillanceService->stopRecognizePersonWithCamera()->UNKNOWN_CAMERA_TYPE: " + cameraType);
//            onError.onError(SurveillanceServiceErrors.commonServiceError());
//        }
//    }
//
//    public boolean isRecognizePersonWithCameraServiceRunning(String cameraType) {
//        CameraService cameraService = CameraService.get();
//        if (cameraType.equalsIgnoreCase("front")) {
//            return cameraService.isFrontCameraRecognizePersonRunning();
//        } else if (cameraType.equalsIgnoreCase("back")) {
//            return cameraService.isBackCameraRecognizePersonRunning();
//        } else {
//            Log.d("tag", "SurveillanceService->isRecognizePersonWithCameraServiceRunning()->UNKNOWN_CAMERA_TYPE: " + cameraType);
//        }
//
//        return false;
//    }
//
//    public boolean isRecognizePersonWithFrontCameraServiceRunning() {
//        return CameraService.get().isFrontCameraRecognizePersonRunning();
//    }
//
//    public boolean isRecognizePersonWithBackCameraServiceRunning() {
//        return CameraService.get().isBackCameraRecognizePersonRunning();
//    }
//    // =====
//    // ===
//
//    @Override
//    public void sendRequest(String groupName,
//                            String groupPassword,
//                            String receiverDeviceName,
//                            ServiceRequest request,
//                            OnRequestDeliveredCallback onDeliveredCallback,
//                            OnRequestResponseCallback onResponseCallback,
//                            OnRequestErrorCallback onErrorCallback) {
//        mCommunicationManager.sendRequest(
//                groupName,
//                groupPassword,
//                receiverDeviceName,
//                request,
//                onDeliveredCallback,
//                onResponseCallback,
//                onErrorCallback
//        );
//    }
//
//    @Override
//    public boolean cancelRequest(String requestId) {
//        return mCommunicationManager.cancelRequest(requestId);
//    }
//
//    @Override
//    public void sendResponse(String groupName,
//                             String groupPassword,
//                             String receiverDeviceName,
//                             ServiceResponse response) {
//        mCommunicationManager.sendResponse(groupName, groupPassword, receiverDeviceName, response);
//    }
//
//    @Override
//    public void sendNotificationToAll(Context context, ServiceNotification notification) {
//        if (mNotificationsManager == null) {
//            mNotificationsManager = new FBSNotificationsManager();
//        }
//        mNotificationsManager.sendNotificationToAll(context, notification);
//    }
//
//    public ServiceNotificationsExecutor notificationsExecutor() {
//        if (mNotificationsManager == null) {
//            mNotificationsManager = new FBSNotificationsManager();
//        }
//
//        return mNotificationsManager;
//    }
//
//    public ForegroundServiceWork foregroundServiceWork() {
//        mForegroundServiceWork = new FBSForegroundServiceWork(mCommunicationManager);
//        return mForegroundServiceWork;
//    }
//
//    private void init(Context context,
//                      String groupName,
//                      String groupPassword,
//                      String deviceName,
//                      OnTaskSuccess<Void> onSuccess,
//                      OnTaskError<ServiceError> onError) {
//        OnTaskSuccess<Void> successCallback = (data) -> {
//            Log.d("tag", "SurveillanceService->init(): 1");
//
//            mCurrentServiceMode = AppConstants.DEVICE_MODE_USER;
//
//            List<String> currentRequestsPath = FBSPathsService.get().requestsPath(
//                    currentGroupName(),
//                    currentGroupPassword(),
//                    currentDeviceName()
//            );
//            List<String> currentResponsesPath = FBSPathsService.get().responsesPath(
//                    currentGroupName(),
//                    currentGroupPassword(),
//                    currentDeviceName()
//            );
//            List<String> currentUpdateFieldPath = FBSPathsService.get().updateFieldPath(
//                    currentGroupName(),
//                    currentGroupPassword(),
//                    currentDeviceName()
//            );
//
//            mRequestsExecutor = new FBSRequestsExecutor();
//            mRequestsSender = new FBSRequestSender();
//
//            mResponsesExecutor = new FBSResponsesExecutor();
//            mResponseSender = new FBSResponseSender();
//
//            mCommunicationManager = new FBSCommunicationManager(
//                    mRequestsExecutor,
//                    mResponsesExecutor,
//                    mRequestsSender,
//                    mResponseSender,
//                    currentRequestsPath,
//                    currentResponsesPath,
//                    currentUpdateFieldPath
//            );
//
//            mCommunicationManager.startIsAliveSignaling(context);
//            startListenToResponses(context);
//
//            onSuccess.onSuccess(null);
//        };
//        subscribeToGlobalNotifications(context, successCallback, onError);
//    }
//
//    private void dispose(Context context) {
//        List<String> currentUpdateFieldPath = FBSPathsService.get().updateFieldPath(
//                currentGroupName(),
//                currentGroupPassword(),
//                currentDeviceName()
//        );
//        FBSCommunicationService.get().setStringValue(
//                currentUpdateFieldPath,
//                String.valueOf(-1)
//        );
//
//        stopListenToResponses(context);
//        stopForegroundService(context, (data) -> {}, (error) -> {});
//        mCommunicationManager.stopIsAliveSignaling(context);
//    }
//
//    private void updateAndPublishDeviceInfo(Context context,
//                                            boolean needUpdateDeviceMode,
//                                            OnTaskSuccess<Void> onSuccess,
//                                            OnTaskError<ServiceError> onError) {
//        List<String> deviceInfoPath = FBSPathsService.get().deviceInfoPath(
//                currentGroupName(), currentGroupPassword(), currentDeviceName()
//        );
//        ValueEventListener listener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    Object value = snapshot.getValue();
//                    DeviceInfo currentDeviceInfo;
//
//                    if (value != null) {
//                        currentDeviceInfo = new DeviceInfo(value);
//                    } else {
//                        currentDeviceInfo = DeviceInfoService.get().currentDeviceInfo(
//                                context,
//                                currentDeviceName(),
//                                currentServiceMode()
//                        );
//                    }
//                    DeviceInfo updatedDeviceInfo = DeviceInfoService.get().updateDeviceInfo(context, currentDeviceInfo, false);
//                    if (needUpdateDeviceMode) {
//                        updatedDeviceInfo = DeviceInfoService.get().changeDeviceMode(
//                                currentServiceMode(),
//                                updatedDeviceInfo
//                        );
//                    }
//
//                    FBSCommunicationService.get().setMapValue(
//                            deviceInfoPath,
//                            updatedDeviceInfo.toServiceObject(),
//                            (data) -> onSuccess.onSuccess(null),
//                            (error) -> onError.onError(SurveillanceServiceErrors.firebaseFailure())
//                    );
//                } else {
//                    onError.onError(SurveillanceServiceErrors.firebaseFailure());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                onError.onError(SurveillanceServiceErrors.firebaseFailure());
//            }
//        };
//        FBSCommunicationService.get().getValue(deviceInfoPath, listener);
//    }
//}
//
//// =================================================================================================
//// =================================================================================================
//// =================================================================================================
//// =================================================================================================
//// =================================================================================================
//// =================================================================================================
//// =================================================================================================
//
////        List<String> deviceInfoPath = FBSPathsService.get().deviceInfoPath(
////                currentGroupName(), currentGroupPassword(), currentDeviceName()
////        );
////        ValueEventListener listener = new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                if (snapshot.exists()) {
////                    Object value = snapshot.getValue();
////                    DeviceInfo currentDeviceInfo;
////
////                    if (value != null) {
////                        currentDeviceInfo = new DeviceInfo(value);
////                    } else {
////                        currentDeviceInfo = DeviceInfoService.get().currentDeviceInfo(
////                                context,
////                                currentDeviceName(),
////                                AppConstants.DEVICE_MODE_USER
////                        );
////                    }
////
////                    DeviceInfo updatedDeviceInfo = DeviceInfoService.get().changeDeviceMode(
////                            AppConstants.DEVICE_MODE_SERVICE,
////                            currentDeviceInfo
////                    );
////                    FBSCommunicationService.get().setMapValue(
////                            deviceInfoPath,
////                            updatedDeviceInfo.toServiceObject(),
////                            (data) -> onSuccess.onSuccess(null),
////                            (error) -> onError.onError(SurveillanceServiceErrors.firebaseFailure())
////                    );
////                } else {
////                    onError.onError(SurveillanceServiceErrors.firebaseFailure());
////                }
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////                onError.onError(SurveillanceServiceErrors.firebaseFailure());
////            }
////        };
////        FBSCommunicationService.get().getValue(deviceInfoPath, listener);
//
////        List<String> deviceInfoPath = FBSPathsService.get().deviceInfoPath(
////                currentGroupName(), currentGroupPassword(), currentDeviceName()
////        );
////        ValueEventListener listener = new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                if (snapshot.exists()) {
////                    Object value = snapshot.getValue();
////                    DeviceInfo currentDeviceInfo;
////
////                    if (value != null) {
////                        currentDeviceInfo = new DeviceInfo(value);
////                    } else {
////                        currentDeviceInfo = DeviceInfoService.get().currentDeviceInfo(
////                                context,
////                                currentDeviceName(),
////                                AppConstants.DEVICE_MODE_USER
////                        );
////                    }
////
////                    DeviceInfo updatedDeviceInfo = DeviceInfoService.get().changeDeviceMode(
////                            AppConstants.DEVICE_MODE_USER,
////                            currentDeviceInfo
////                    );
////                    FBSCommunicationService.get().setMapValue(
////                            deviceInfoPath,
////                            updatedDeviceInfo.toServiceObject(),
////                            (data) -> onSuccess.onSuccess(null),
////                            (error) -> onError.onError(SurveillanceServiceErrors.firebaseFailure())
////                    );
////                } else {
////                    onError.onError(SurveillanceServiceErrors.firebaseFailure());
////                }
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////                onError.onError(SurveillanceServiceErrors.firebaseFailure());
////            }
////        };
////        FBSCommunicationService.get().getValue(deviceInfoPath, listener);
