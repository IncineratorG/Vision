package com.vision.services.surveillance.pipeline.operations;

import android.util.Log;

import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.device_movement.DeviceMovementService;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_jobs.PipelineJobs;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_job.PipelineJob;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation.PipelineOperation;
import com.vision.services.surveillance.pipeline.jobs.StartDetectDeviceMovementJob;
import com.vision.services.surveillance.pipeline.jobs.StopDetectDeviceMovementJob;

import java.util.List;

public class DetectDeviceMovementOperation implements PipelineOperation {
    public DetectDeviceMovementOperation() {

    }

    @Override
    public void run(PipelineJobs jobs, OnTaskSuccess<Object> onSuccess, OnTaskError<Object> onError) {
        Log.d("TAG", "DetectDeviceMovementOperation");

        List<PipelineJob> startDetectDeviceMovementJobs = jobs.getJobs(StartDetectDeviceMovementJob.TYPE);
        List<PipelineJob> stopDetectDeviceMovementJobs = jobs.getJobs(StopDetectDeviceMovementJob.TYPE);

        Log.d("TAG", "DetectDeviceMovementOperation->startDetectDeviceMovementJobs: " + startDetectDeviceMovementJobs.size());
        Log.d("TAG", "DetectDeviceMovementOperation->stopDetectDeviceMovementJobs: " + stopDetectDeviceMovementJobs.size());

        for (int i = 0; i < startDetectDeviceMovementJobs.size(); ++i) {
            StartDetectDeviceMovementJob job = (StartDetectDeviceMovementJob) startDetectDeviceMovementJobs.get(i);
            job.setFinished(true);
        }

        for (int i = 0; i < stopDetectDeviceMovementJobs.size(); ++i) {
            stopDetectDeviceMovementJobs.get(i).setFinished(true);
        }

        onSuccess.onSuccess(true);

//        OnTaskSuccess<Void> movementStartCallback = (data) -> {
//            Log.d("tag", "DetectDeviceMovementOperation->movementStartCallback()");
//        };
//        OnTaskSuccess<Void> movementEndCallback = (data) -> {
//            Log.d("tag", "DetectDeviceMovementOperation->movementEndCallback()");
//        };


//        DeviceMovementService.get().start(mContext, movementStartCallback, movementEndCallback);

//        onSuccess.onSuccess(true);

//        OnTaskSuccess<Object> onTaskSuccess = data -> {
//
//        };
//
//        return onTaskSuccess;
    }
}
