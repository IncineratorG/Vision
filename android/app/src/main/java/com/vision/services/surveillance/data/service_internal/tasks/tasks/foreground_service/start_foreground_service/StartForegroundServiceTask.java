package com.vision.services.surveillance.data.service_internal.tasks.tasks.foreground_service.start_foreground_service;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.vision.android_services.foreground.surveillance.SurveillanceForegroundService;
import com.vision.common.constants.AppConstants;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.surveillance.data.service_internal.data.internal_data.SurveillanceServiceInternalData;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;
import com.vision.services.surveillance.data.service_internal.tasks.task_results.SurveillanceServiceInternalTaskResults;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.SurveillanceServiceInternalTasks;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.foreground_service.is_foreground_service_running.IsForegroundServiceRunningTask;

import java.util.Map;

public class StartForegroundServiceTask implements ServiceSyncTask {
    private Context mContext;
    private boolean mNeedUpdateDeviceMode;
    private String mCurrentGroupName;
    private String mCurrentGroupPassword;
    private String mCurrentDeviceName;
    private OnTaskSuccess<Void> mOnSuccess;
    private OnTaskError<ServiceError> mOnError;

    public StartForegroundServiceTask(Context context,
                                      boolean needUpdateDeviceMode,
                                      String currentGroupName,
                                      String currentGroupPassword,
                                      String currentDeviceName,
                                      OnTaskSuccess<Void> onSuccess,
                                      OnTaskError<ServiceError> onError) {
        mContext = context;
        mNeedUpdateDeviceMode = needUpdateDeviceMode;
        mCurrentGroupName = currentGroupName;
        mCurrentGroupPassword = currentGroupPassword;
        mCurrentDeviceName = currentDeviceName;
        mOnSuccess = onSuccess;
        mOnError = onError;
    }

    @Override
    public Object run(Map<String, Object> params) {
        if (mContext == null) {
            Log.d("tag", "StartForegroundServiceTask->run(): CONTEXT_IS_NULL");
            return null;
        }

        IsForegroundServiceRunningTask task =
                SurveillanceServiceInternalTasks.isForegroundServiceRunningTask(mContext);

        if (SurveillanceServiceInternalTaskResults.result(task, task.run(null))) {
            Log.d("tag", "StartForegroundServiceTask->run(): SERVICE_IS_ALREADY_RUNNING");
            return null;
        }

        SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();

        if (mInternalData.serviceWakeLock != null && mInternalData.serviceWakeLock.isHeld()) {
            mInternalData.serviceWakeLock.release();
            mInternalData.serviceWakeLock = null;
        }

        PowerManager powerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);

        mInternalData.serviceWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, AppConstants.SERVICE_WAKE_LOCK_TAG);
        mInternalData.serviceWakeLock.acquire();

        mInternalData.currentServiceMode = AppConstants.DEVICE_MODE_SERVICE;

        Intent serviceIntent = new Intent(mContext, SurveillanceForegroundService.class);
        serviceIntent.setAction("start");
        mContext.startService(serviceIntent);

        SurveillanceServiceInternalTasks.updateAndPublishDeviceInfoTask(
                mContext,
                mNeedUpdateDeviceMode,
                mCurrentGroupName,
                mCurrentGroupPassword,
                mCurrentDeviceName,
                mInternalData.currentServiceMode,
                mOnSuccess,
                mOnError
        ).run(null);

        return null;
    }
}
