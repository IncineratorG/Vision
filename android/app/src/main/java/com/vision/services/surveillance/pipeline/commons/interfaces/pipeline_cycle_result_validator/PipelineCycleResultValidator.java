package com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_result_validator;

import com.vision.services.surveillance.pipeline.commons.data.pipeline_cycle.PipelineCycle;

import org.json.JSONObject;

public interface PipelineCycleResultValidator {
    boolean isValid(JSONObject result, PipelineCycle cycle);
}
