package com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation_state_description;

import org.json.JSONObject;

public interface PipelineOperationStateDescription {
    String name();
    boolean includeInResult();
    JSONObject toJSONObject();
    boolean isValid(JSONObject stateResult);
}
