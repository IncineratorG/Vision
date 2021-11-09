package com.vision.services.surveillance.data.service_internal.tasks.tasks.init_and_dispose_service.is_service_initialized;

import com.vision.services.auth.AuthService;
import com.vision.services.surveillance.data.service_internal.interfaces.internal_task.InternalTask;

import java.util.Map;

public class IsServiceInitializedSurveillanceServiceTask implements InternalTask {
    public IsServiceInitializedSurveillanceServiceTask() {

    }

    @Override
    public Object run(Map<String, Object> params) {
        return AuthService.get().isLoggedIn();
    }
}
