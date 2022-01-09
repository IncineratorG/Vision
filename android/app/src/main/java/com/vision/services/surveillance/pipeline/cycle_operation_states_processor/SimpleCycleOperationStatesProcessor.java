package com.vision.services.surveillance.pipeline.cycle_operation_states_processor;

import android.content.Context;
import android.util.Log;

import com.vision.services.surveillance.pipeline.commons.data.pipeline_operation_state.PipelineOperationState;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_operation_states_processor.PipelineCycleOperationStatesProcessor;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation.PipelineOperation;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation_state_description.PipelineOperationStateDescription;
import com.vision.services.surveillance.pipeline.operations.detect_device_movement.operation.DetectDeviceMovementOperation;
import com.vision.services.surveillance.pipeline.operations.detect_device_movement.state_description.DetectDeviceMovementOperationStateDescription;
import com.vision.services.surveillance.pipeline.operations.empty.operation.EmptyOperation;
import com.vision.services.surveillance.pipeline.operations.wait.operation.WaitOperation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SimpleCycleOperationStatesProcessor implements PipelineCycleOperationStatesProcessor {
    public SimpleCycleOperationStatesProcessor() {

    }

    @Override
    public void process(Context context, List<PipelineOperationState> states) {
        Log.d("TAG", "SimpleOperationStatesProcessor->process()->STATES_COUNT: " + states.size());

        for (int i = 0; i < states.size(); ++i) {
            PipelineOperationState state = states.get(i);
//            processState(state);
        }

//        for (int i = 0; i < states.size(); ++i) {
//            PipelineOperationState state = states.get(i);
//
//            if (state.operation().type().equalsIgnoreCase(DetectDeviceMovementOperation.TYPE)) {
//                DetectDeviceMovementOperationStateDescription description = (DetectDeviceMovementOperationStateDescription) state.description();
//
//                Log.d("TAG", "SimpleOperationStatesProcessor->process()->DETECT_MOVEMENT_RUNNING: " + description.deviceMovementRunning());
//                Log.d("TAG", "SimpleOperationStatesProcessor->process()->MOVEMENT_DETECTED: " + description.movementDetected());
//            }
//        }

        // ===
//        try {
//            JSONObject stateObject = new JSONObject();
//
//            JSONObject deviceMovementServiceState = new JSONObject();
//            deviceMovementServiceState.put("running", false);
//            deviceMovementServiceState.put("variableOne", 1);
//            deviceMovementServiceState.put("variableTwo", "NONE");
//
//            stateObject.put("deviceMovementServiceState", deviceMovementServiceState);
//
//            JSONObject detectPersonServiceState = new JSONObject();
//            detectPersonServiceState.put("running", false);
//            detectPersonServiceState.put("variableOne", 1);
//            detectPersonServiceState.put("variableTwo", "NONE");
//
//            stateObject.put("detectPersonServiceState", detectPersonServiceState);
//
//            JSONObject detectSoundServiceState = new JSONObject();
//            detectSoundServiceState.put("running", false);
//            detectSoundServiceState.put("variableOne", 3);
//
//            stateObject.put("detectSoundServiceState", detectSoundServiceState);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        // ===
    }

    private void processState(PipelineOperationState state) {
        PipelineOperation operation = state.operation();
        PipelineOperationStateDescription stateDescription = state.description();

        switch (operation.type()) {
            case (EmptyOperation.TYPE): {
                Log.d("TAG", "SimpleCycleOperationStatesProcessor->processState()->WILL_PROCESS_EMPTY_STATE");
                break;
            }

            case (WaitOperation.TYPE): {
                Log.d("TAG", "SimpleCycleOperationStatesProcessor->processState()->WILL_PROCESS_WAIT_STATE");
                break;
            }

            case (DetectDeviceMovementOperation.TYPE): {
                Log.d("TAG", "SimpleCycleOperationStatesProcessor->processState()->WILL_PROCESS_DETECT_DEVICE_MOVEMENT_STATE");
                break;
            }

            default: {
                Log.d("TAG", "SimpleCycleOperationStatesProcessor->processState()->UNKNOWN_OPERATION: " + operation.type());
                break;
            }
        }
    }
}
