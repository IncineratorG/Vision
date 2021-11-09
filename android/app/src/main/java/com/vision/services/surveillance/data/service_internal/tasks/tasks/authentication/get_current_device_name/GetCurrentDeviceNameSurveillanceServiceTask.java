package com.vision.services.surveillance.data.service_internal.tasks.tasks.authentication.get_current_device_name;

import com.vision.services.auth.AuthService;
import com.vision.services.surveillance.data.service_internal.interfaces.internal_task.InternalTask;

import java.util.Map;

public class GetCurrentDeviceNameSurveillanceServiceTask implements InternalTask {
    @Override
    public Object run(Map<String, Object> params) {
        return AuthService.get().currentDeviceName();
    }
}
