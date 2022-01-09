package com.vision.services.surveillance.pipeline.operations.recognize_person.operation;

import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_jobs.PipelineJobs;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_operation_state.PipelineOperationState;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation.PipelineOperation;
import com.vision.services.surveillance.pipeline.operations.recognize_person.state_description.RecognizePersonOperationStateDescription;

public class RecognizePersonOperation implements PipelineOperation {
    public static final String TYPE = "RecognizePersonOperation";

    private String mId;

    public RecognizePersonOperation(String id) {
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
        RecognizePersonOperationStateDescription stateDescription = new RecognizePersonOperationStateDescription();

        onSuccess.onSuccess(new PipelineOperationState(this, stateDescription));
    }
}
