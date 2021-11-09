package com.vision.services.surveillance.data.service_internal.tasks.tasks.authentication.get_current_group_password;

import com.vision.services.auth.AuthService;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;

import java.util.Map;

public class GetCurrentGroupPasswordTask implements ServiceSyncTask {
    @Override
    public Object run(Map<String, Object> params) {
        return AuthService.get().currentGroupPassword();
    }
}
