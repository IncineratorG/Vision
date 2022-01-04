package com.vision.services.surveillance;

import android.content.Context;
import android.util.Log;

import com.vision.common.constants.AppConstants;
import com.vision.common.data.hybrid_objects.device_info_list.DeviceInfoList;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.common.data.service_notification.ServiceNotification;
import com.vision.common.interfaces.service_notification_sender.ServiceNotificationSender;
import com.vision.common.interfaces.service_notifications_executor.ServiceNotificationsExecutor;
import com.vision.common.interfaces.service_request_interrupter.ServiceRequestInterrupter;
import com.vision.common.interfaces.foreground_service_work.ForegroundServiceWork;
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
import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.GetCurrentDeviceNameTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.GetCurrentGroupNameTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.GetCurrentGroupPasswordTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.detect_device_movement.IsDetectDeviceMovementServiceRunningTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.foreground_service.IsForegroundServiceRunningTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.init_and_dispose_service.GetCurrentServiceModeTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.init_and_dispose_service.IsServiceInitializedTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.recognize_person_with_camera.IsRecognizePersonWithCameraServiceRunningTask;

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

        // ===
        // =====
        SurveillanceServiceInternalTasks.startForegroundServiceTask(
                context,
                true,
                currentGroupName(),
                currentGroupPassword(),
                currentDeviceName(),
                onSuccess,
                onError
        ).run(null);
        // =====
        // ===
    }

    public void stopForegroundService(Context context,
                                      OnTaskSuccess<Void> onSuccess,
                                      OnTaskError<ServiceError> onError) {
        Log.d("tag", "SurveillanceService->stopForegroundService()");

        // ===
        // =====
        SurveillanceServiceInternalTasks.stopForegroundServiceTask(
                context,
                true,
                currentGroupName(),
                currentGroupPassword(),
                currentDeviceName(),
                onSuccess,
                onError
        ).run(null);
        // =====
        // ===
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

    public void takeFrontCameraImage(String quality,
                                     CameraService.OnImageTaken imageTakenCallback,
                                     CameraService.OnImageTakeError errorCallback) {
        CameraService.get().takeFrontCameraImage(quality, imageTakenCallback, errorCallback);
    }

    public void takeBackCameraImage(String quality,
                                    CameraService.OnImageTaken imageTakenCallback,
                                    CameraService.OnImageTakeError errorCallback) {
        CameraService.get().takeBackCameraImage(quality, imageTakenCallback, errorCallback);
    }

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
