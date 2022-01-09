package com.vision.services.surveillance.pipeline.operations.empty.state_description;

import android.util.Log;

import com.vision.common.interfaces.service_object.ServiceObject;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation_state_description.PipelineOperationStateDescription;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EmptyOperationStateDescription implements PipelineOperationStateDescription {
    public static final String NAME_FIELD = "emptyOperation";
    public static final String TEST_VARIABLE_FIELD = "testVariable";

    public EmptyOperationStateDescription() {

    }

    @Override
    public String name() {
        return NAME_FIELD;
    }

    @Override
    public boolean includeInResult() {
        return true;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(TEST_VARIABLE_FIELD, 7);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    public Map<String, Object> toServiceObject() {
        Map<String, Object> serviceObject = new HashMap<>();

        Map<String, Object> stateDescription = new HashMap<>();
        stateDescription.put(TEST_VARIABLE_FIELD, 7);

        serviceObject.put(NAME_FIELD, stateDescription);

        return serviceObject;
    }

    @Override
    public boolean isValid(JSONObject stateResult) {
        if (stateResult == null) {
            Log.d("TAG", "EmptyOperationStateDescription->isValid()->RESULT_IS_NULL");
            return false;
        }

        if (!stateResult.has(TEST_VARIABLE_FIELD)) {
            Log.d("TAG", "EmptyOperationStateDescription->isValid()->INSUFFICIENT_FIELD: " + TEST_VARIABLE_FIELD);
            return false;
        }

        return true;
    }
}
