package com.vision.services.surveillance.service_internal_tasks.tasks.detect_device_movement;

import com.vision.services.device_movement.DeviceMovementService;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;

import java.util.Map;

public class IsDetectDeviceMovementServiceRunningTask implements ServiceSyncTask {
    @Override
    public Object run(Map<String, Object> params) {
        return DeviceMovementService.get().isRunning();
    }
}
