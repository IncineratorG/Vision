package com.vision.services.surveillance.data.service_internal.tasks.tasks.responses.stop_listen_to_responses;

import android.content.Context;
import android.util.Log;

import com.vision.services.surveillance.data.service_internal.data.internal_data.SurveillanceServiceInternalData;
import com.vision.services.surveillance.data.service_internal.interfaces.internal_task.InternalTask;

import java.util.Map;

public class StopListenToResponsesSurveillanceServiceTask implements InternalTask {
    private Context mContext;

    public StopListenToResponsesSurveillanceServiceTask(Context context) {
        mContext = context;
    }

    @Override
    public Object run(Map<String, Object> params) {
        SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();

        if (mInternalData.mCommunicationManager == null) {
            Log.d("tag", "StopListenToResponsesSurveillanceServiceTask->run(): COMMUNICATION_MANAGER_NOT_INITIALIZED");
            return null;
        }

        mInternalData.mCommunicationManager.stopResponsesListener(mContext);

        return null;
    }
}
