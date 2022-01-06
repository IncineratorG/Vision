package com.vision.services.surveillance.pipeline.operations.detect_device_movement.status;

import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation_status.PipelineOperationStatus;

public class DetectDeviceMovementOperationStatus implements PipelineOperationStatus {
    private boolean mDeviceMovementRunning;
    private boolean mMovementDetected;

    public DetectDeviceMovementOperationStatus() {
        mDeviceMovementRunning = false;
        mMovementDetected = false;
    }

    public DetectDeviceMovementOperationStatus(boolean deviceMovementRunning, boolean movementDetected) {
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
