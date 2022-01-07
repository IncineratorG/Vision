package com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation_state_description;

import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_result_validator.PipelineCycleResultValidator;

import org.json.JSONObject;

public interface PipelineOperationStateDescription {
    String name();
    JSONObject toJSONObject();
}
