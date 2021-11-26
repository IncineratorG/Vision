package com.vision.services.surveillance.service_internal_tasks.tasks.authentication.get_current_device_name;

import com.vision.services.auth.AuthService;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;

import java.util.Map;

public class GetCurrentDeviceNameTask implements ServiceSyncTask {
    @Override
    public Object run(Map<String, Object> params) {
        return AuthService.get().currentDeviceName();
    }
}
