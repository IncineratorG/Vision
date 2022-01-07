package com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation_state_description;

import org.json.JSONObject;

public interface PipelineOperationStateDescription {
    String name();
    JSONObject toJSONObject();
    boolean isValid(JSONObject stateResult);
}
