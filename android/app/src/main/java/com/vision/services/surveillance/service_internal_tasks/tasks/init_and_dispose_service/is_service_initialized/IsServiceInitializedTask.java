package com.vision.services.surveillance.service_internal_tasks.tasks.init_and_dispose_service.is_service_initialized;

import com.vision.services.auth.AuthService;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;

import java.util.Map;

public class IsServiceInitializedTask implements ServiceSyncTask {
    public IsServiceInitializedTask() {

    }

    @Override
    public Object run(Map<String, Object> params) {
        return AuthService.get().isLoggedIn();
    }
}
