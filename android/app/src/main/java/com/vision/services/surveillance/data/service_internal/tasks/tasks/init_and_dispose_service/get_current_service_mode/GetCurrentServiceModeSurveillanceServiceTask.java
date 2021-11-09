package com.vision.services.surveillance.data.service_internal.tasks.tasks.init_and_dispose_service.get_current_service_mode;

import com.vision.services.surveillance.data.service_internal.data.internal_data.SurveillanceServiceInternalData;
import com.vision.services.surveillance.data.service_internal.interfaces.internal_task.InternalTask;

import java.util.Map;

public class GetCurrentServiceModeSurveillanceServiceTask implements InternalTask {
    @Override
    public Object run(Map<String, Object> params) {
        SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();

        return mInternalData.mCurrentServiceMode;
    }
}
