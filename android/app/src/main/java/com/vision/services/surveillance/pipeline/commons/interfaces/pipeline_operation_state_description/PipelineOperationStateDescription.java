package com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation_state_description;

import com.vision.common.interfaces.service_object.ServiceObject;

import org.json.JSONObject;

public interface PipelineOperationStateDescription extends ServiceObject {
    String name();
    boolean includeInResult();
    JSONObject toJSONObject();
    boolean isValid(JSONObject stateResult);
}
