package com.vision.services.surveillance.restore_service_manager;

import android.content.Context;
import android.util.Log;

import com.vision.common.data.hybrid_objects.authentication_data.AuthenticationData;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.common.interfaces.service_restore_handler.ServiceRestoreHandler;
import com.vision.services.app_storages.AppStorages;
import com.vision.services.auth.AuthService;
import com.vision.services.firebase_communication.FBSCommunicationService;
import com.vision.services.firebase_paths.FBSPathsService;
import com.vision.services.surveillance.data.internal_data.SurveillanceServiceInternalData;
import com.vision.services.surveillance.data.service_errors.SurveillanceServiceErrors;
import com.vision.services.surveillance.restore_service_manager.handlers.RestoreCameraServiceHandler;
import com.vision.services.surveillance.restore_service_manager.handlers.RestoreDeviceMovementServiceHandler;
import com.vision.services.surveillance.restore_service_manager.handlers.RestoreSurveillanceServiceHandler;
import com.vision.services.surveillance.service_internal_tasks.tasks.SurveillanceServiceInternalTasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestoreServiceManager {
    private static final String RESTORE_SURVEILLANCE_SERVICE_STAGE = "RESTORE_SURVEILLANCE_SERVICE_STAGE";
    private static final String RESTORE_DEVICE_MOVEMENT_SERVICE_STAGE = "RESTORE_DEVICE_MOVEMENT_SERVICE_STAGE";
    private static final String RESTORE_CAMERA_SERVICE_STAGE = "RESTORE_CAMERA_SERVICE_STAGE";

    private Map<String, ServiceRestoreHandler> mRestoreHandlers;
    private Map<String, OnTaskSuccess<Object>> mRestoreHandlersSuccessCallbacks;
    private Map<String, OnTaskError<ServiceError>> mRestoreHandlersErrorCallbacks;

    public RestoreServiceManager() {
        mRestoreHandlers = new HashMap<>();
        mRestoreHandlersSuccessCallbacks = new HashMap<>();
        mRestoreHandlersErrorCallbacks = new HashMap<>();
    }

    public void restore(Context context,
                        OnTaskSuccess<Void> onSuccess,
                        OnTaskError<ServiceError> onError) {
        AuthenticationData lastAuthenticationData =
                AppStorages.get().surveillanceStorage().getLastAuthenticationData(context);

        OnTaskSuccess<Void> restoreSuccessCallback = (data) -> onSuccess.onSuccess(null);
        OnTaskError<ServiceError> restoreErrorCallback = (error) -> onError.onError(error);

        OnTaskSuccess<Void> loginSuccessCallback = (data) -> {
            setRestoreHandlersAndCallbacks(
                    context,
                    lastAuthenticationData,
                    restoreSuccessCallback,
                    restoreErrorCallback
            );
            restoreServices(context, lastAuthenticationData);
        };
        OnTaskError<ServiceError> loginErrorCallback = (error) -> onError.onError(error);

        OnTaskSuccess<Void> logoutSuccessCallback = (data) -> {
            SurveillanceServiceInternalTasks.loginDeviceInGroupTask(
                    context,
                    lastAuthenticationData.groupName(),
                    lastAuthenticationData.groupPassword(),
                    lastAuthenticationData.deviceName(),
                    loginSuccessCallback,
                    loginErrorCallback
            ).run(null);
        };
        OnTaskError<ServiceError> logoutErrorCallback = (error) -> onError.onError(error);

        logoutFromService(
                context, lastAuthenticationData, logoutSuccessCallback, logoutErrorCallback
        );
    }

    private void logoutFromService(Context context,
                                   AuthenticationData authenticationData,
                                   OnTaskSuccess<Void> onLogoutSuccess,
                                   OnTaskError<ServiceError> onLogoutError) {
        List<String> currentUpdateFieldPath = FBSPathsService.get().updateFieldPath(
                authenticationData.groupName(),
                authenticationData.groupPassword(),
                authenticationData.deviceName()
        );
        FBSCommunicationService.get().setStringValue(
                currentUpdateFieldPath,
                String.valueOf(-1),
                task -> AuthService.get().logoutDeviceFromGroup(onLogoutSuccess, onLogoutError),
                e -> {
                    Log.d("tag", "RestoreDeviceInGroupTask->logoutFromService()->ERROR: " + e.toString());
                    onLogoutError.onError(SurveillanceServiceErrors.firebaseFailure());
                }
        );

//        SurveillanceServiceInternalTasks.stopListenToResponsesTask(mContext).run(null);

        SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();
        if (mInternalData.communicationManager != null) {
            mInternalData.communicationManager.stopIsAliveSignaling(context);
        }
    }

    private void setRestoreHandlersAndCallbacks(Context context,
                                                AuthenticationData authenticationData,
                                                OnTaskSuccess<Void> onSuccess,
                                                OnTaskError<ServiceError> onError) {
        mRestoreHandlers.put(RESTORE_CAMERA_SERVICE_STAGE, new RestoreCameraServiceHandler());
        mRestoreHandlersSuccessCallbacks.put(RESTORE_CAMERA_SERVICE_STAGE, (result) -> {
            onSuccess.onSuccess(null);
        });
        mRestoreHandlersErrorCallbacks.put(RESTORE_CAMERA_SERVICE_STAGE, (error) -> {
            onError.onError(error);
        });

        mRestoreHandlers.put(RESTORE_DEVICE_MOVEMENT_SERVICE_STAGE, new RestoreDeviceMovementServiceHandler());
        mRestoreHandlersSuccessCallbacks.put(RESTORE_DEVICE_MOVEMENT_SERVICE_STAGE, (result) -> {
            OnTaskSuccess<Object> successCallback =
                    mRestoreHandlersSuccessCallbacks.get(RESTORE_CAMERA_SERVICE_STAGE);
            OnTaskError<ServiceError> errorCallback =
                    mRestoreHandlersErrorCallbacks.get(RESTORE_CAMERA_SERVICE_STAGE);
            ServiceRestoreHandler restoreHandler =
                    mRestoreHandlers.get(RESTORE_CAMERA_SERVICE_STAGE);

            restoreHandler.handle(context, authenticationData, successCallback, errorCallback);
        });
        mRestoreHandlersErrorCallbacks.put(RESTORE_DEVICE_MOVEMENT_SERVICE_STAGE, (error) -> {
            onError.onError(error);
        });

        mRestoreHandlers.put(RESTORE_SURVEILLANCE_SERVICE_STAGE, new RestoreSurveillanceServiceHandler());
        mRestoreHandlersSuccessCallbacks.put(RESTORE_SURVEILLANCE_SERVICE_STAGE, (result) -> {
            OnTaskSuccess<Object> successCallback =
                    mRestoreHandlersSuccessCallbacks.get(RESTORE_DEVICE_MOVEMENT_SERVICE_STAGE);
            OnTaskError<ServiceError> errorCallback =
                    mRestoreHandlersErrorCallbacks.get(RESTORE_DEVICE_MOVEMENT_SERVICE_STAGE);
            ServiceRestoreHandler restoreHandler =
                    mRestoreHandlers.get(RESTORE_DEVICE_MOVEMENT_SERVICE_STAGE);

            restoreHandler.handle(context, authenticationData, successCallback, errorCallback);
        });
        mRestoreHandlersErrorCallbacks.put(RESTORE_SURVEILLANCE_SERVICE_STAGE, (error) -> {
            onError.onError(error);
        });
    }

    private void restoreServices(Context context, AuthenticationData authenticationData) {
        OnTaskSuccess<Object> successCallback =
                mRestoreHandlersSuccessCallbacks.get(RESTORE_SURVEILLANCE_SERVICE_STAGE);
        OnTaskError<ServiceError> errorCallback =
                mRestoreHandlersErrorCallbacks.get(RESTORE_SURVEILLANCE_SERVICE_STAGE);
        ServiceRestoreHandler restoreHandler =
                mRestoreHandlers.get(RESTORE_SURVEILLANCE_SERVICE_STAGE);

        restoreHandler.handle(context, authenticationData, successCallback, errorCallback);
    }
}
