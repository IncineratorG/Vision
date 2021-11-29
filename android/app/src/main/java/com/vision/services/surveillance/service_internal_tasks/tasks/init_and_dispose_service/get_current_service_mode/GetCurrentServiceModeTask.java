package com.vision.services.surveillance.service_internal_tasks.tasks.init_and_dispose_service.get_current_service_mode;

import com.vision.services.surveillance.data.internal_data.SurveillanceServiceInternalData;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;

import java.util.Map;

public class GetCurrentServiceModeTask implements ServiceSyncTask {
    @Override
    public Object run(Map<String, Object> params) {
        SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();

        return mInternalData.currentServiceMode;
    }
}
