package com.vision.services.surveillance.pipeline.operations;

import android.util.Log;

import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.device_movement.DeviceMovementService;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_jobs.PipelineJobs;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_job.PipelineJob;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation.PipelineOperation;

public class DetectDeviceMovementOperation implements PipelineOperation {
    public DetectDeviceMovementOperation() {

    }

    @Override
    public void run(PipelineJobs jobs, OnTaskSuccess<Object> onSuccess, OnTaskError<Object> onError) {
        Log.d("TAG", "DetectDeviceMovementOperation");

        OnTaskSuccess<Void> movementStartCallback = (data) -> {
            Log.d("tag", "DetectDeviceMovementOperation->movementStartCallback()");
        };
        OnTaskSuccess<Void> movementEndCallback = (data) -> {
            Log.d("tag", "DetectDeviceMovementOperation->movementEndCallback()");
        };

//        DeviceMovementService.get().start(mContext, movementStartCallback, movementEndCallback);

//        onSuccess.onSuccess(true);

//        OnTaskSuccess<Object> onTaskSuccess = data -> {
//
//        };
//
//        return onTaskSuccess;
    }
}
