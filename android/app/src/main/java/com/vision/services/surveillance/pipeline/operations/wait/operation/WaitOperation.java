package com.vision.services.surveillance.pipeline.operations.wait.operation;

import android.util.Log;

import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_jobs.PipelineJobs;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_operation_state.PipelineOperationState;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation.PipelineOperation;
import com.vision.services.surveillance.pipeline.operations.wait.state_description.WaitOperationStateDescription;

public class WaitOperation implements PipelineOperation {
    public static final String TYPE = "WaitOperation";

    private String mId;

    public WaitOperation(String id) {
        mId = id;
    }

    @Override
    public String id() {
        return mId;
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public void run(PipelineJobs jobs, OnTaskSuccess<PipelineOperationState> onSuccess, OnTaskError<Object> onError) {
        Log.d("TAG", "WaitOperation");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onSuccess.onSuccess(new PipelineOperationState(this, new WaitOperationStateDescription()));
    }
}
