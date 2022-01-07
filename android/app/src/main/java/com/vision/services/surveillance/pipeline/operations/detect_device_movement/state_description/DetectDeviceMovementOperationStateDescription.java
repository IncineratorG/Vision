package com.vision.services.surveillance.pipeline.operations.detect_device_movement.state_description;

import android.util.Log;

import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation_state_description.PipelineOperationStateDescription;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Override
    public String name() {
        return NAME_FIELD;
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

//    @Override
//    public boolean isValid(JSONObject result) {
//        if (result == null) {
//            Log.d("TAG", "DetectDeviceMovementOperationStateDescription->isValid()->RESULT_IS_NULL");
//            return false;
//        }
//
//
//
//        return true;
//    }

    public DetectDeviceMovementOperationStateDescription(boolean deviceMovementRunning, boolean movementDetected) {
        mDeviceMovementRunning = deviceMovementRunning;
        mMovementDetected = movementDetected;
    }

    public boolean deviceMovementRunning() {
        return mDeviceMovementRunning;
    }

    public boolean movementDetected() {
        return mMovementDetected;
    }
}
