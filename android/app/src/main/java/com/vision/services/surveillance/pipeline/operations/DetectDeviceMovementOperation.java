package com.vision.services.surveillance.pipeline.operations;

import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_input.PipelineInput;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_output.PipelineOutput;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation.PipelineOperation;

public class DetectDeviceMovementOperation implements PipelineOperation {
    public DetectDeviceMovementOperation() {
        
    }

    @Override
    public void run(PipelineInput input, PipelineOutput output, OnTaskSuccess<Object> onSuccess) {

    }
}
