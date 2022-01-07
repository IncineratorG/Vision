package com.vision.services.surveillance.pipeline.commons.data.pipeline_operation_state;

import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation.PipelineOperation;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation_state_description.PipelineOperationStateDescription;

public class PipelineOperationState {
    private PipelineOperation mOperation;
    private PipelineOperationStateDescription mStatus;

    public PipelineOperationState(PipelineOperation operation, PipelineOperationStateDescription status) {
        mOperation = operation;
        mStatus = status;
    }

    public PipelineOperation operation() {
        return mOperation;
    }

    public PipelineOperationStateDescription description() {
        return mStatus;
    }
}
