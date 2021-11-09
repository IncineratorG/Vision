package com.vision.services.surveillance.data.service_internal.tasks.tasks.detect_device_movement.is_detect_device_movement_service_running;

import com.vision.services.device_movement.DeviceMovementService;
import com.vision.services.surveillance.data.service_internal.interfaces.internal_task.InternalTask;

import java.util.Map;

public class IsDetectDeviceMovementServiceRunningSurveillanceServiceTask implements InternalTask {
    @Override
    public Object run(Map<String, Object> params) {
        return DeviceMovementService.get().isRunning();
    }
}
