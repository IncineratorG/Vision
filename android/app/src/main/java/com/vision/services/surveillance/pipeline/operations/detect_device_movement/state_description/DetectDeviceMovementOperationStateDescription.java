package com.vision.services.surveillance.pipeline.operations.detect_device_movement.state_description;

import android.util.Log;

import com.vision.common.interfaces.service_object.ServiceObject;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation_state_description.PipelineOperationStateDescription;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetectDeviceMovementOperationStateDescription implements PipelineOperationStateDescription {
    public static final String NAME_FIELD = "detectDeviceMovement";
    public static final String DEVICE_MOVEMENT_RUNNING_FIELD = "deviceMovementRunning";
    public static final String DEVICE_MOVEMENT_DETECTED_FIELD = "deviceMovementDetected";

    private boolean mDeviceMovementRunning;
    private boolean mMovementDetected;

    public DetectDeviceMovementOperationStateDescription() {
        mDeviceMovementRunning = false;
        mMovementDetected = false;
    }

    public DetectDeviceMovementOperationStateDescription(boolean deviceMovementRunning, boolean movementDetected) {
        mDeviceMovementRunning = deviceMovementRunning;
        mMovementDetected = movementDetected;
    }

    public DetectDeviceMovementOperationStateDescription(DetectDeviceMovementOperationStateDescription other) {
        mDeviceMovementRunning = other.mDeviceMovementRunning;
        mMovementDetected = other.mMovementDetected;
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
            jsonObject.put(DEVICE_MOVEMENT_RUNNING_FIELD, mDeviceMovementRunning);
            jsonObject.put(DEVICE_MOVEMENT_DETECTED_FIELD, mMovementDetected);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    public Map<String, Object> toServiceObject() {
        Map<String, Object> serviceObject = new HashMap<>();

        Map<String, Object> stateDescription = new HashMap<>();
        stateDescription.put(DEVICE_MOVEMENT_RUNNING_FIELD, mDeviceMovementRunning);
        stateDescription.put(DEVICE_MOVEMENT_DETECTED_FIELD, mMovementDetected);

        serviceObject.put(NAME_FIELD, stateDescription);

        return serviceObject;
    }

    @Override
    public boolean isValid(JSONObject stateResult) {
        if (stateResult == null) {
            Log.d("TAG", "DetectDeviceMovementOperationStateDescription->isValid()->RESULT_IS_NULL");
            return false;
        }

        if (!stateResult.has(DEVICE_MOVEMENT_RUNNING_FIELD)) {
            Log.d("TAG", "DetectDeviceMovementOperationStateDescription->isValid()->INSUFFICIENT_FIELD: " + DEVICE_MOVEMENT_RUNNING_FIELD);
            return false;
        }

        if (!stateResult.has(DEVICE_MOVEMENT_DETECTED_FIELD)) {
            Log.d("TAG", "DetectDeviceMovementOperationStateDescription->isValid()->INSUFFICIENT_FIELD: " + DEVICE_MOVEMENT_DETECTED_FIELD);
            return false;
        }

        return true;
    }

    public boolean deviceMovementRunning() {
        return mDeviceMovementRunning;
    }

    public boolean movementDetected() {
        return mMovementDetected;
    }
}
