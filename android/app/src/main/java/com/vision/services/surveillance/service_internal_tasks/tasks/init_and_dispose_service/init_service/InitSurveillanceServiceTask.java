package com.vision.services.surveillance.service_internal_tasks.tasks.init_and_dispose_service.init_service;

import android.content.Context;
import android.util.Log;

import com.vision.common.constants.AppConstants;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.firebase_paths.FBSPathsService;
import com.vision.services.surveillance.communication_manager.firebase.FBSCommunicationManager;
import com.vision.services.surveillance.requests.executor.firebase.FBSRequestsExecutor;
import com.vision.services.surveillance.requests.sender.firebase.FBSRequestSender;
import com.vision.services.surveillance.responses.executor.firebase.FBSResponsesExecutor;
import com.vision.services.surveillance.responses.sender.firebase.FBSResponseSender;
import com.vision.services.surveillance.data.internal_data.SurveillanceServiceInternalData;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.SurveillanceServiceInternalTasks;

import java.util.List;
import java.util.Map;

public class InitSurveillanceServiceTask implements ServiceSyncTask {
    private Context mContext;
    private String mGroupName;
    private String mGroupPassword;
    private String mDeviceName;
    private OnTaskSuccess<Void> mOnSuccess;
    private OnTaskError<ServiceError> mOnError;

    public InitSurveillanceServiceTask(Context context,
                                       String groupName,
                                       String groupPassword,
                                       String deviceName,
                                       OnTaskSuccess<Void> onSuccess,
                                       OnTaskError<ServiceError> onError) {
        mContext = context;
        mGroupName = groupName;
        mGroupPassword = groupPassword;
        mDeviceName = deviceName;
        mOnSuccess = onSuccess;
        mOnError = onError;
    }

    @Override
    public Object run(Map<String, Object> params) {
        OnTaskSuccess<Void> successCallback = (data) -> {
            Log.d("tag", "InitSurveillanceServiceTask->run(): 1");

            SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();

            mInternalData.currentServiceMode = AppConstants.DEVICE_MODE_USER;

            List<String> currentRequestsPath = FBSPathsService.get().requestsPath(
                    mGroupName,
                    mGroupPassword,
                    mDeviceName
            );
            List<String> currentResponsesPath = FBSPathsService.get().responsesPath(
                    mGroupName,
                    mGroupPassword,
                    mDeviceName
            );
            List<String> currentUpdateFieldPath = FBSPathsService.get().updateFieldPath(
                    mGroupName,
                    mGroupPassword,
                    mDeviceName
            );

            mInternalData.requestsExecutor = new FBSRequestsExecutor();
            mInternalData.requestsSender = new FBSRequestSender();

            mInternalData.responsesExecutor = new FBSResponsesExecutor();
            mInternalData.responseSender = new FBSResponseSender();

            mInternalData.communicationManager = new FBSCommunicationManager(
                    mInternalData.requestsExecutor,
                    mInternalData.responsesExecutor,
                    mInternalData.requestsSender,
                    mInternalData.responseSender,
                    currentRequestsPath,
                    currentResponsesPath,
                    currentUpdateFieldPath
            );

            mInternalData.communicationManager.startIsAliveSignaling(mContext);

            SurveillanceServiceInternalTasks.startListenToResponsesTask(mContext).run(null);
//            mStartListenRoResponsesTask.run(null);
//            startListenToResponses(mContext);

            mOnSuccess.onSuccess(null);
        };

        SurveillanceServiceInternalTasks.subscribeToGlobalNotificationsTask(
                mContext,
                mGroupName,
                mGroupPassword,
                successCallback,
                mOnError
        ).run(null);
//        mSubscribeToGlobalNotificationsTask.run(null);
//        subscribeToGlobalNotifications(mContext, successCallback, mOnError);

        return null;
    }
}
