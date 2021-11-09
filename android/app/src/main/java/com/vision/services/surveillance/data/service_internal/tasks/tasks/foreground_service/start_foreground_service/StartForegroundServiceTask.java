package com.vision.services.surveillance.data.service_internal.tasks.tasks.foreground_service.start_foreground_service;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.vision.android_services.foreground.surveillance.SurveillanceForegroundService;
import com.vision.common.constants.AppConstants;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.surveillance.data.service_internal.data.internal_data.SurveillanceServiceInternalData;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.SurveillanceServiceInternalTasks;

import java.util.Map;

public class StartForegroundServiceTask implements ServiceSyncTask {
    private Context mContext;
    private boolean mNeedUpdateDeviceMode;
    private String mCurrentGroupName;
    private String mCurrentGroupPassword;
    private String mCurrentDeviceName;
    private String mCurrentServiceMode;
    private String mServiceWakeLockTag;
    private OnTaskSuccess<Void> mOnSuccess;
    private OnTaskError<ServiceError> mOnError;

    public StartForegroundServiceTask(Context context,
                                      boolean needUpdateDeviceMode,
                                      String currentGroupName,
                                      String currentGroupPassword,
                                      String currentDeviceName,
                                      String currentServiceMode,
                                      String serviceWakeLockTag,
                                      OnTaskSuccess<Void> onSuccess,
                                      OnTaskError<ServiceError> onError) {
        mContext = context;
        mNeedUpdateDeviceMode = needUpdateDeviceMode;
        mCurrentGroupName = currentGroupName;
        mCurrentGroupPassword = currentGroupPassword;
        mCurrentDeviceName = currentDeviceName;
        mCurrentServiceMode = currentServiceMode;
        mServiceWakeLockTag = serviceWakeLockTag;
        mOnSuccess = onSuccess;
        mOnError = onError;
    }

    @Override
    public Object run(Map<String, Object> params) {
        SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();

        if (mInternalData.mServiceWakeLock != null && mInternalData.mServiceWakeLock.isHeld()) {
            mInternalData.mServiceWakeLock.release();
            mInternalData.mServiceWakeLock = null;
        }

        PowerManager powerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);

        mInternalData.mServiceWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, mServiceWakeLockTag);
        mInternalData.mServiceWakeLock.acquire();

        mInternalData.mCurrentServiceMode = AppConstants.DEVICE_MODE_SERVICE;

        Intent serviceIntent = new Intent(mContext, SurveillanceForegroundService.class);
        serviceIntent.setAction("start");
        mContext.startService(serviceIntent);

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
