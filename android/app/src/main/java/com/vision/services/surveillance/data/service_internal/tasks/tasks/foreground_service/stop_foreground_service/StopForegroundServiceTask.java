package com.vision.services.surveillance.data.service_internal.tasks.tasks.foreground_service.stop_foreground_service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vision.android_services.foreground.surveillance.SurveillanceForegroundService;
import com.vision.common.constants.AppConstants;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.camera.CameraService;
import com.vision.services.device_movement.DeviceMovementService;
import com.vision.services.surveillance.data.service_internal.data.internal_data.SurveillanceServiceInternalData;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;
import com.vision.services.surveillance.data.service_internal.tasks.task_results.SurveillanceServiceInternalTaskResults;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.SurveillanceServiceInternalTasks;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.foreground_service.is_foreground_service_running.IsForegroundServiceRunningTask;

import java.util.Map;

public class StopForegroundServiceTask implements ServiceSyncTask {
    private Context mContext;
    private boolean mNeedUpdateDeviceMode;
    private String mCurrentGroupName;
    private String mCurrentGroupPassword;
    private String mCurrentDeviceName;
    private String mCurrentServiceMode;
    private OnTaskSuccess<Void> mOnSuccess;
    private OnTaskError<ServiceError> mOnError;

    public StopForegroundServiceTask(Context context,
                                     boolean needUpdateDeviceMode,
                                     String currentGroupName,
                                     String currentGroupPassword,
                                     String currentDeviceName,
                                     String currentServiceMode,
                                     OnTaskSuccess<Void> onSuccess,
                                     OnTaskError<ServiceError> onError) {
        mContext = context;
        mNeedUpdateDeviceMode = needUpdateDeviceMode;
        mCurrentGroupName = currentGroupName;
        mCurrentGroupPassword = currentGroupPassword;
        mCurrentDeviceName = currentDeviceName;
        mCurrentServiceMode = currentServiceMode;
        mOnSuccess = onSuccess;
        mOnError = onError;
    }

    @Override
    public Object run(Map<String, Object> params) {
        if (mContext == null) {
            Log.d("tag", "StopForegroundServiceTask->run(): CONTEXT_IS_NULL");
            return null;
        }

        IsForegroundServiceRunningTask task =
                SurveillanceServiceInternalTasks.isForegroundServiceRunningTask(mContext);

        if (!SurveillanceServiceInternalTaskResults.result(task, task.run(null))) {
            Log.d("tag", "StopForegroundServiceTask->run(): SERVICE_ALREADY_NOT_RUNNING");
            return null;
        }

        SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();

        if ((mInternalData.mServiceWakeLock != null) && (mInternalData.mServiceWakeLock.isHeld())) {
            mInternalData.mServiceWakeLock.release();
            mInternalData.mServiceWakeLock = null;
        }

        mInternalData.mCurrentServiceMode = AppConstants.DEVICE_MODE_USER;

        Intent serviceIntent = new Intent(mContext, SurveillanceForegroundService.class);
        serviceIntent.setAction("stop");
        mContext.startService(serviceIntent);

        // ===
        // =====
        DeviceMovementService.get().stop(mContext);
        CameraService.get().stop(mContext);
        // =====
        // ===

        SurveillanceServiceInternalTasks.updateAndPublishDeviceInfoTask(
                mContext,
                mNeedUpdateDeviceMode,
                mCurrentGroupName,
                mCurrentGroupPassword,
                mCurrentDeviceName,
                mInternalData.mCurrentServiceMode,
                mOnSuccess,
                mOnError
        ).run(null);

        return null;
    }
}
