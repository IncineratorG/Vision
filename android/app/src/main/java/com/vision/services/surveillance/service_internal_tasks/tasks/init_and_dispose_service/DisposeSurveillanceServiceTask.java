package com.vision.services.surveillance.service_internal_tasks.tasks.init_and_dispose_service;

import android.content.Context;

import com.vision.services.firebase_communication.FBSCommunicationService;
import com.vision.services.firebase_paths.FBSPathsService;
import com.vision.services.surveillance.data.internal_data.SurveillanceServiceInternalData;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.SurveillanceServiceInternalTasks;

import java.util.List;
import java.util.Map;

public class DisposeSurveillanceServiceTask implements ServiceSyncTask {
    private Context mContext;
    private String mCurrentGroupName;
    private String mCurrentGroupPassword;
    private String mCurrentDeviceName;

    public DisposeSurveillanceServiceTask(Context context,
                                          String currentGroupName,
                                          String currentGroupPassword,
                                          String currentDeviceName) {
        mContext = context;
        mCurrentGroupName = currentGroupName;
        mCurrentGroupPassword = currentGroupPassword;
        mCurrentDeviceName = currentDeviceName;
    }

    @Override
    public Object run(Map<String, Object> params) {
        List<String> currentUpdateFieldPath = FBSPathsService.get().updateFieldPath(
                mCurrentGroupName,
                mCurrentGroupPassword,
                mCurrentDeviceName
        );
        FBSCommunicationService.get().setStringValue(
                currentUpdateFieldPath,
                String.valueOf(-1)
        );

        SurveillanceServiceInternalTasks.stopListenToResponsesTask(mContext).run(null);
        SurveillanceServiceInternalTasks.stopForegroundServiceTask(
                mContext,
                true,
                mCurrentGroupName,
                mCurrentGroupPassword,
                mCurrentDeviceName,
                (data) -> {},
                (error) -> {}
        ).run(null);

        SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();
        if (mInternalData.communicationManager != null) {
            mInternalData.communicationManager.stopIsAliveSignaling(mContext);
        }

        return null;
    }
}
