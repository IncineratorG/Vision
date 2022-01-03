package com.vision.services.surveillance.service_internal_tasks.tasks.authentication;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

import com.vision.common.constants.AppConstants;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;
import com.vision.services.auth.AuthService;
import com.vision.services.firebase_communication.FBSCommunicationService;
import com.vision.services.firebase_paths.FBSPathsService;
import com.vision.services.surveillance.data.service_errors.SurveillanceServiceErrors;
import com.vision.services.surveillance.data.internal_data.SurveillanceServiceInternalData;
import com.vision.services.surveillance.service_internal_tasks.tasks.SurveillanceServiceInternalTasks;

import java.util.List;
import java.util.Map;

public class RestoreDeviceInGroupTask implements ServiceSyncTask {
    private Context mContext;
    private String mCurrentGroupName;
    private String mCurrentGroupPassword;
    private String mCurrentDeviceName;
    private OnTaskSuccess<Void> mOnSuccess;
    private OnTaskError<ServiceError> mOnError;

    public RestoreDeviceInGroupTask(Context context,
                                    String currentGroupName,
                                    String currentGroupPassword,
                                    String currentDeviceName,
                                    OnTaskSuccess<Void> onSuccess,
                                    OnTaskError<ServiceError> onError) {
        mContext = context;
        mCurrentGroupName = currentGroupName;
        mCurrentGroupPassword = currentGroupPassword;
        mCurrentDeviceName = currentDeviceName;
        mOnSuccess = onSuccess;
        mOnError = onError;
    }

    @Override
    public Object run(Map<String, Object> params) {
        OnTaskSuccess<Void> restoreSuccessCallback = (data) -> mOnSuccess.onSuccess(null);
        OnTaskError<ServiceError> restoreErrorCallback = (error) -> mOnError.onError(error);

        OnTaskSuccess<Void> loginSuccessCallback = (data) -> {
            restoreService(restoreSuccessCallback, restoreErrorCallback);
        };
        OnTaskError<ServiceError> loginErrorCallback = (error) -> mOnError.onError(error);

        OnTaskSuccess<Void> logoutSuccessCallback = (data) -> {
            SurveillanceServiceInternalTasks.loginDeviceInGroupTask(
                    mContext,
                    mCurrentGroupName,
                    mCurrentGroupPassword,
                    mCurrentDeviceName,
                    loginSuccessCallback,
                    loginErrorCallback
            ).run(null);
        };
        OnTaskError<ServiceError> logoutErrorCallback = (error) -> mOnError.onError(error);

        logoutFromService(logoutSuccessCallback, logoutErrorCallback);

        return null;
    }

    private void logoutFromService(OnTaskSuccess<Void> onLogoutSuccess,
                                   OnTaskError<ServiceError> onLogoutError) {
        List<String> currentUpdateFieldPath = FBSPathsService.get().updateFieldPath(
                mCurrentGroupName,
                mCurrentGroupPassword,
                mCurrentDeviceName
        );
        FBSCommunicationService.get().setStringValue(
                currentUpdateFieldPath,
                String.valueOf(-1),
                task -> AuthService.get().logoutDeviceFromGroup(onLogoutSuccess, onLogoutError),
                e -> {
                    Log.d("tag", "RestoreDeviceInGroupTask->logoutFromService()->ERROR: " + e.toString());
                    mOnError.onError(SurveillanceServiceErrors.firebaseFailure());
                }
        );

//        SurveillanceServiceInternalTasks.stopListenToResponsesTask(mContext).run(null);

        SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();
        if (mInternalData.communicationManager != null) {
            mInternalData.communicationManager.stopIsAliveSignaling(mContext);
        }
    }

    private void restoreService(OnTaskSuccess<Void> onRestoreSuccess,
                                OnTaskError<ServiceError> onRestoreError) {
        SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();

        if (mInternalData.serviceWakeLock != null && mInternalData.serviceWakeLock.isHeld()) {
            mInternalData.serviceWakeLock.release();
            mInternalData.serviceWakeLock = null;
        }

        PowerManager powerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);

        mInternalData.serviceWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, AppConstants.SERVICE_WAKE_LOCK_TAG);
        mInternalData.serviceWakeLock.acquire();

        mInternalData.currentServiceMode = AppConstants.DEVICE_MODE_SERVICE;

        SurveillanceServiceInternalTasks.updateAndPublishDeviceInfoTask(
                mContext,
                true,
                mCurrentGroupName,
                mCurrentGroupPassword,
                mCurrentDeviceName,
                mInternalData.currentServiceMode,
                onRestoreSuccess,
                onRestoreError
        ).run(null);
    }
}


//package com.vision.services.surveillance.service_internal_tasks.tasks.authentication.restore_device_in_group;
//
//import android.content.Context;
//import android.os.PowerManager;
//import android.util.Log;
//
//import com.vision.common.constants.AppConstants;
//import com.vision.common.data.service_error.ServiceError;
//import com.vision.common.data.service_generic_callbacks.OnTaskError;
//import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
//import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;
//import com.vision.services.auth.AuthService;
//import com.vision.services.firebase_communication.FBSCommunicationService;
//import com.vision.services.firebase_paths.FBSPathsService;
//import com.vision.services.surveillance.data.service_errors.SurveillanceServiceErrors;
//import com.vision.services.surveillance.data.internal_data.SurveillanceServiceInternalData;
//import com.vision.services.surveillance.service_internal_tasks.tasks.SurveillanceServiceInternalTasks;
//
//import java.util.List;
//import java.util.Map;
//
//public class RestoreDeviceInGroupTask implements ServiceSyncTask {
//    private Context mContext;
//    private String mCurrentGroupName;
//    private String mCurrentGroupPassword;
//    private String mCurrentDeviceName;
//    private OnTaskSuccess<Void> mOnSuccess;
//    private OnTaskError<ServiceError> mOnError;
//
//    public RestoreDeviceInGroupTask(Context context,
//                                    String currentGroupName,
//                                    String currentGroupPassword,
//                                    String currentDeviceName,
//                                    OnTaskSuccess<Void> onSuccess,
//                                    OnTaskError<ServiceError> onError) {
//        mContext = context;
//        mCurrentGroupName = currentGroupName;
//        mCurrentGroupPassword = currentGroupPassword;
//        mCurrentDeviceName = currentDeviceName;
//        mOnSuccess = onSuccess;
//        mOnError = onError;
//    }
//
//    @Override
//    public Object run(Map<String, Object> params) {
//        OnTaskSuccess<Void> restoreSuccessCallback = (data) -> mOnSuccess.onSuccess(null);
//        OnTaskError<ServiceError> restoreErrorCallback = (error) -> mOnError.onError(error);
//
//        OnTaskSuccess<Void> loginSuccessCallback = (data) -> {
//            restoreService(restoreSuccessCallback, restoreErrorCallback);
//        };
//        OnTaskError<ServiceError> loginErrorCallback = (error) -> mOnError.onError(error);
//
//        OnTaskSuccess<Void> logoutSuccessCallback = (data) -> {
//            SurveillanceServiceInternalTasks.loginDeviceInGroupTask(
//                    mContext,
//                    mCurrentGroupName,
//                    mCurrentGroupPassword,
//                    mCurrentDeviceName,
//                    loginSuccessCallback,
//                    loginErrorCallback
//            ).run(null);
//        };
//        OnTaskError<ServiceError> logoutErrorCallback = (error) -> mOnError.onError(error);
//
//        logoutFromService(logoutSuccessCallback, logoutErrorCallback);
//
//        return null;
//
////        OnTaskSuccess<Void> logoutSuccessCallback = (logoutData) -> {
////            OnTaskSuccess<Void> loginSuccessCallback = (loginData) -> {
////                OnTaskSuccess<Void> restoreSuccessCallback = (restoreData) -> mOnSuccess.onSuccess(null);
////                OnTaskError<ServiceError> restoreErrorCallback = (error) -> mOnError.onError(error);
////
////                restoreService(restoreSuccessCallback, restoreErrorCallback);
////            };
////            OnTaskError<ServiceError> loginErrorCallback = (error) -> mOnError.onError(error);
////
////            SurveillanceServiceInternalTasks.loginDeviceInGroupTask(
////                    mContext,
////                    mCurrentGroupName,
////                    mCurrentGroupPassword,
////                    mCurrentDeviceName,
////                    loginSuccessCallback,
////                    loginErrorCallback
////            ).run(null);
////        };
////        OnTaskError<ServiceError> logoutErrorCallback = (error) -> mOnError.onError(error);
////        logoutFromService(logoutSuccessCallback, logoutErrorCallback);
//
////        return null;
//    }
//
//    private void logoutFromService(OnTaskSuccess<Void> onLogoutSuccess,
//                                   OnTaskError<ServiceError> onLogoutError) {
//        List<String> currentUpdateFieldPath = FBSPathsService.get().updateFieldPath(
//                mCurrentGroupName,
//                mCurrentGroupPassword,
//                mCurrentDeviceName
//        );
//        FBSCommunicationService.get().setStringValue(
//                currentUpdateFieldPath,
//                String.valueOf(-1),
//                task -> AuthService.get().logoutDeviceFromGroup(onLogoutSuccess, onLogoutError),
//                e -> {
//                    Log.d("tag", "RestoreDeviceInGroupTask->logoutFromService()->ERROR: " + e.toString());
//                    mOnError.onError(SurveillanceServiceErrors.firebaseFailure());
//                }
//        );
//
////        SurveillanceServiceInternalTasks.stopListenToResponsesTask(mContext).run(null);
//
//        SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();
//        if (mInternalData.communicationManager != null) {
//            mInternalData.communicationManager.stopIsAliveSignaling(mContext);
//        }
//    }
//
//    private void restoreService(OnTaskSuccess<Void> onRestoreSuccess,
//                                OnTaskError<ServiceError> onRestoreError) {
//        SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();
//
//        if (mInternalData.serviceWakeLock != null && mInternalData.serviceWakeLock.isHeld()) {
//            mInternalData.serviceWakeLock.release();
//            mInternalData.serviceWakeLock = null;
//        }
//
//        PowerManager powerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
//
//        mInternalData.serviceWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, AppConstants.SERVICE_WAKE_LOCK_TAG);
//        mInternalData.serviceWakeLock.acquire();
//
//        mInternalData.currentServiceMode = AppConstants.DEVICE_MODE_SERVICE;
//
//        SurveillanceServiceInternalTasks.updateAndPublishDeviceInfoTask(
//                mContext,
//                true,
//                mCurrentGroupName,
//                mCurrentGroupPassword,
//                mCurrentDeviceName,
//                mInternalData.currentServiceMode,
//                onRestoreSuccess,
//                onRestoreError
//        ).run(null);
//    }
//}
