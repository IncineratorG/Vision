package com.vision.services.surveillance.pipeline.cycle_result_builder;

import android.content.Context;
import android.util.Log;

import com.vision.services.surveillance.pipeline.commons.data.pipeline_operation_state.PipelineOperationState;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_result_builder.PipelineCycleResultBuilder;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation_state_description.PipelineOperationStateDescription;
import com.vision.services.surveillance.pipeline.operations.detect_device_movement.operation.DetectDeviceMovementOperation;
import com.vision.services.surveillance.pipeline.operations.detect_device_movement.state_description.DetectDeviceMovementOperationStateDescription;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SimpleCycleResultBuilder implements PipelineCycleResultBuilder {
    @Override
    public JSONObject build(Context context, List<PipelineOperationState> states) {
        JSONObject cycleResultObject = new JSONObject();

        for (int i = 0; i < states.size(); ++i) {
            PipelineOperationState state = states.get(i);
            if (!state.description().includeInResult()) {
                continue;
            }

            JSONObject operationResult = resultForOperation(state);
            if (operationResult.length() > 0) {
                try {
                    cycleResultObject.put(state.description().name(), operationResult);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d(
                        "TAG",
                        "SimpleCycleResultBuilder->build()->BAD_OPERATION_RESULT_FOR_OPERATION: " +
                                state.operation().type()
                );
            }
        }

        return cycleResultObject;
    }

    private JSONObject resultForOperation(PipelineOperationState operationState) {
        return operationState.description().toJSONObject();
    }
}
