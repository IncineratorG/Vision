package com.vision.services.surveillance.pipeline.operation_states_processor;

import android.content.Context;
import android.util.Log;

import com.vision.services.surveillance.pipeline.commons.data.pipeline_operation_state.PipelineOperationState;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_operation_states_processor.PipelineCycleOperationStatesProcessor;
import com.vision.services.surveillance.pipeline.operations.detect_device_movement.operation.DetectDeviceMovementOperation;
import com.vision.services.surveillance.pipeline.operations.detect_device_movement.status.DetectDeviceMovementOperationStatus;

import java.util.List;

public class SimpleOperationStatesProcessor implements PipelineCycleOperationStatesProcessor {
    public SimpleOperationStatesProcessor() {

    }

    @Override
    public void process(Context context, List<PipelineOperationState> states) {
        Log.d("TAG", "SimpleOperationStatesProcessor->process()->STATES_COUNT: " + states.size());

        for (int i = 0; i < states.size(); ++i) {
            PipelineOperationState state = states.get(i);

            if (state.operation().type().equalsIgnoreCase(DetectDeviceMovementOperation.TYPE)) {
                DetectDeviceMovementOperationStatus status = (DetectDeviceMovementOperationStatus) state.status();

                Log.d("TAG", "SimpleOperationStatesProcessor->process()->DETECT_MOVEMENT_RUNNING: " + status.deviceMovementRunning());
                Log.d("TAG", "SimpleOperationStatesProcessor->process()->MOVEMENT_DETECTED: " + status.movementDetected());
            }
        }
    }
}
