package com.vision.services.surveillance.pipeline.cycle_result_validator;

import android.util.Log;

import com.vision.services.surveillance.pipeline.commons.data.pipeline_cycle.PipelineCycle;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_operation_state.PipelineOperationState;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_result_validator.PipelineCycleResultValidator;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation.PipelineOperation;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation_state_description.PipelineOperationStateDescription;
import com.vision.services.surveillance.pipeline.operations.detect_device_movement.operation.DetectDeviceMovementOperation;
import com.vision.services.surveillance.pipeline.operations.detect_device_movement.state_description.DetectDeviceMovementOperationStateDescription;
import com.vision.services.surveillance.pipeline.operations.empty.operation.EmptyOperation;
import com.vision.services.surveillance.pipeline.operations.empty.state_description.EmptyOperationStateDescription;
import com.vision.services.surveillance.pipeline.operations.wait.operation.WaitOperation;
import com.vision.services.surveillance.pipeline.operations.wait.state_description.WaitOperationStateDescription;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SimpleCycleResultValidator implements PipelineCycleResultValidator {
    @Override
    public boolean isValid(JSONObject result, PipelineCycle cycle) {
        Log.d("TAG", "SimpleCycleResultValidator->isValid(): " + result.toString());

        Set<String> checkedStates = new HashSet<>();

        List<PipelineOperationState> cycleOperationStates = cycle.getCurrentCycleOperationStates();
        for (int i = 0; i < cycleOperationStates.size(); ++i) {
            PipelineOperationStateDescription description = cycleOperationStates.get(i).description();
            if (!description.includeInResult()) {
                continue;
            }

            JSONObject stateResultSubObject = extractSubObject(description.name(), result);
            if (stateResultSubObject == null) {
                return false;
            }

            boolean isValidStateResult = isValidOperationStateResult(cycleOperationStates.get(i), stateResultSubObject);
            if (!isValidStateResult) {
                return false;
            }

            checkedStates.add(description.name());
        }

        int resultObjectKeysCount = 0;
        Iterator<String> keys = result.keys();
        while (keys.hasNext()) {
            keys.next();
            ++resultObjectKeysCount;
        }

        if (resultObjectKeysCount != checkedStates.size()) {
            Log.d(
                    "TAG",
                    "SimpleCycleResultValidator->isValid()->CHECKED_STATES_AND_RESULT_KEYS_NOT_MATCH: " +
                            checkedStates.size() + " - " +
                            resultObjectKeysCount
            );
            return false;
        }

        return true;
    }

    private JSONObject extractSubObject(String key, JSONObject fullObject) {
        if (!fullObject.has(key)) {
            Log.d("TAG", "SimpleCycleResultValidator->extractSubObject()->NO_SUBOBJECT_WITH_KEY: " + key);
            return null;
        }

        JSONObject subObject = null;
        try {
            subObject = fullObject.getJSONObject(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return subObject;
    }

    private boolean isValidOperationStateResult(PipelineOperationState state, JSONObject stateResult) {
        return state.description().isValid(stateResult);
    }
}
