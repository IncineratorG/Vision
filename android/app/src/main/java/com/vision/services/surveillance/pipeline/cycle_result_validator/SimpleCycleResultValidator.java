package com.vision.services.surveillance.pipeline.cycle_result_validator;

import android.util.Log;

import com.vision.services.surveillance.pipeline.commons.data.pipeline_cycle.PipelineCycle;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_operation_state.PipelineOperationState;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_result_validator.PipelineCycleResultValidator;

import org.json.JSONObject;

import java.util.List;

public class SimpleCycleResultValidator implements PipelineCycleResultValidator {
    @Override
    public boolean isValid(JSONObject result, PipelineCycle cycle) {
        Log.d("TAG", "SimpleCycleResultValidator->isValid(): " + result.toString());

        return false;
    }
}
