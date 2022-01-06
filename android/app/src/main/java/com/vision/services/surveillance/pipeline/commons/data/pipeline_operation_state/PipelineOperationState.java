package com.vision.services.surveillance.pipeline.commons.data.pipeline_operation_state;

import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation.PipelineOperation;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation_status.PipelineOperationStatus;

public class PipelineOperationState {
    private PipelineOperation mOperation;
    private PipelineOperationStatus mStatus;

    public PipelineOperationState(PipelineOperation operation, PipelineOperationStatus status) {
        mOperation = operation;
        mStatus = status;
    }

    public PipelineOperation operation() {
        return mOperation;
    }

    public PipelineOperationStatus status() {
        return mStatus;
    }
}
