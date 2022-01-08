package com.vision.services.surveillance.service_internal_tasks.tasks.detect_device_movement;

import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.device_movement.DeviceMovementService;
import com.vision.services.surveillance.SurveillanceService;
import com.vision.services.surveillance.notifications.SurveillanceServiceNotifications;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;
import com.vision.services.surveillance.pipeline.Pipeline;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_job.PipelineJob;
import com.vision.services.surveillance.pipeline.operations.detect_device_movement.jobs.StartDetectDeviceMovementJob;
import com.vision.services.surveillance.service_internal_tasks.tasks.SurveillanceServiceInternalTasks;

import java.util.Map;

public class StartDetectDeviceMovementTask implements ServiceSyncTask {
    private Context mContext;
    private boolean mNeedUpdateDeviceMode;
    private String mCurrentGroupName;
    private String mCurrentGroupPassword;
    private String mCurrentDeviceName;
    private String mCurrentServiceMode;
    private OnTaskSuccess<Void> mOnSuccess;
    private OnTaskError<ServiceError> mOnError;

    public StartDetectDeviceMovementTask(Context context,
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
        Log.d("tag", "StartDetectDeviceMovementSurveillanceServiceTask->run()");

        OnTaskSuccess<Void> movementStartCallback = (data) -> {
            Log.d("tag", "movementStartCallback()");

            SurveillanceService.get().sendNotificationToAll(
                    mContext,
                    SurveillanceServiceNotifications.deviceMovementStartNotification(
                            mCurrentGroupName,
                            mCurrentDeviceName
                    )
            );
        };
        OnTaskSuccess<Void> movementEndCallback = (data) -> {
            Log.d("tag", "movementEndCallback()");

            SurveillanceService.get().sendNotificationToAll(
                    mContext,
                    SurveillanceServiceNotifications.deviceMovementEndNotification(
                            mCurrentGroupName,
                            mCurrentDeviceName
                    )
            );
        };

//        DeviceMovementService.get().start(mContext, movementStartCallback, movementEndCallback);

        // ===
        PipelineJob startDetectDeviceMovementJob = new StartDetectDeviceMovementJob(
                String.valueOf(System.currentTimeMillis()),
                mContext,
                data -> {
                    Log.d("TAG", "StartDetectDeviceMovementTask->DETECT_DEVICE_MOVEMENT_STARTED");

                    SurveillanceServiceInternalTasks.updateAndPublishDeviceInfoTask(
                            mContext,
                            mNeedUpdateDeviceMode,
                            mCurrentGroupName,
                            mCurrentGroupPassword,
                            mCurrentDeviceName,
                            mCurrentServiceMode,
                            mOnSuccess,
                            mOnError
                    ).run(null);
                },
                error -> {
                    Log.d("TAG", "StartDetectDeviceMovementTask->DETECT_DEVICE_MOVEMENT_START->ERROR: " + error.toString());
                }
        );

        Pipeline.get().scheduleJob(startDetectDeviceMovementJob);
        // ===

//        SurveillanceServiceInternalTasks.updateAndPublishDeviceInfoTask(
//                mContext,
//                mNeedUpdateDeviceMode,
//                mCurrentGroupName,
//                mCurrentGroupPassword,
//                mCurrentDeviceName,
//                mCurrentServiceMode,
//                mOnSuccess,
//                mOnError
//        ).run(null);

        return null;
    }
}
