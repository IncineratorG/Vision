package com.vision.services.surveillance.data.service_internal.tasks.tasks.responses.start_listen_to_responses;

import android.content.Context;
import android.util.Log;

import com.vision.services.surveillance.data.service_internal.data.internal_data.SurveillanceServiceInternalData;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;

import java.util.Map;

public class StartListenToResponsesTask implements ServiceSyncTask {
    private Context mContext;

    public StartListenToResponsesTask(Context context) {
        mContext = context;
    }

    @Override
    public Object run(Map<String, Object> params) {
        SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();

        if (mInternalData.mCommunicationManager == null) {
            Log.d("tag", "StartListenToResponsesSurveillanceServiceTask->run(): COMMUNICATION_MANAGER_NOT_INITIALIZED");
            return null;
        }

        mInternalData.mCommunicationManager.startResponsesListener(mContext);

        return null;
    }
}
