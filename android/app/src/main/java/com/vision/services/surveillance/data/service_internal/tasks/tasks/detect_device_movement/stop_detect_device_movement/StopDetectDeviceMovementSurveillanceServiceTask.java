package com.vision.services.surveillance.data.service_internal.tasks.tasks.detect_device_movement.stop_detect_device_movement;

import android.content.Context;

import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.device_movement.DeviceMovementService;
import com.vision.services.surveillance.data.service_internal.interfaces.internal_task.InternalTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.SurveillanceServiceInternalTasks;

import java.util.Map;

public class StopDetectDeviceMovementSurveillanceServiceTask implements InternalTask {
    private Context mContext;
    private boolean mNeedUpdateDeviceMode;
    private String mCurrentGroupName;
    private String mCurrentGroupPassword;
    private String mCurrentDeviceName;
    private String mCurrentServiceMode;
    private OnTaskSuccess<Void> mOnSuccess;
    private OnTaskError<ServiceError> mOnError;

    public StopDetectDeviceMovementSurveillanceServiceTask(Context context,
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
        DeviceMovementService.get().stop(mContext);

        SurveillanceServiceInternalTasks.updateAndPublishDeviceInfoSurveillanceServiceTask(
                mContext,
                mNeedUpdateDeviceMode,
                mCurrentGroupName,
                mCurrentGroupPassword,
                mCurrentDeviceName,
                mCurrentServiceMode,
                mOnSuccess,
                mOnError
        ).run(null);

        return null;
    }
}
