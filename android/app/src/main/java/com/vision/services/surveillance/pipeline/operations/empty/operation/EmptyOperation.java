package com.vision.services.surveillance.pipeline.operations.empty.operation;

import android.util.Log;

import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_jobs.PipelineJobs;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_operation_state.PipelineOperationState;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation.PipelineOperation;
import com.vision.services.surveillance.pipeline.operations.empty.state_description.EmptyOperationStateDescription;

public class EmptyOperation implements PipelineOperation {
    public static final String TYPE = "EmptyOperation";

    private String mId;

    public EmptyOperation(String id) {
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
        Log.d("TAG", "EmptyOperation");

        onSuccess.onSuccess(new PipelineOperationState(this, new EmptyOperationStateDescription()));
    }
}
