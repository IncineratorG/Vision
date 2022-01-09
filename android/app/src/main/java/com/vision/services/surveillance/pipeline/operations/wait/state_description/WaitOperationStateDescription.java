package com.vision.services.surveillance.pipeline.operations.wait.state_description;

import android.util.Log;

import com.vision.common.interfaces.service_object.ServiceObject;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation_state_description.PipelineOperationStateDescription;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WaitOperationStateDescription implements PipelineOperationStateDescription {
    public WaitOperationStateDescription() {

    }

    @Override
    public String name() {
        return "";
    }

    @Override
    public boolean includeInResult() {
        return false;
    }

    @Override
    public boolean isValid(JSONObject stateResult) {
        if (stateResult == null) {
            Log.d("TAG", "WaitOperationStateDescription->isValid()->RESULT_IS_NULL");
            return false;
        }

        return true;
    }

    @Override
    public Map<String, Object> toServiceObject() {
        return new HashMap<>();
    }

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject();
    }
}