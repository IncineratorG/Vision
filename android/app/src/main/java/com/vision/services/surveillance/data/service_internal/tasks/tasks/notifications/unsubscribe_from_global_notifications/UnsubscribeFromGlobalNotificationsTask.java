package com.vision.services.surveillance.data.service_internal.tasks.tasks.notifications.unsubscribe_from_global_notifications;

import android.content.Context;

import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.app_storages.AppStorages;
import com.vision.services.app_storages.surveillance.SurveillanceStorage;
import com.vision.services.firebase_messaging.FBSMessagingService;
import com.vision.services.surveillance.data.service_errors.external_service_errors_mapper.ExternalServiceErrorsMapper;
import com.vision.services.surveillance.data.service_internal.data.internal_data.SurveillanceServiceInternalData;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;

import java.util.Map;

public class UnsubscribeFromGlobalNotificationsTask implements ServiceSyncTask {
    private Context mContext;
    private String mCurrentGroupName;
    private String mCurrentGroupPassword;
    private OnTaskSuccess<Void> mOnSuccess;
    private OnTaskError<ServiceError> mOnError;

    public UnsubscribeFromGlobalNotificationsTask(Context context,
                                                  String currentGroupName,
                                                  String currentGroupPassword,
                                                  OnTaskSuccess<Void> onSuccess,
                                                  OnTaskError<ServiceError> onError) {
        mContext = context;
        mCurrentGroupName = currentGroupName;
        mCurrentGroupPassword = currentGroupPassword;
        mOnSuccess = onSuccess;
        mOnError = onError;
    }

    @Override
    public Object run(Map<String, Object> params) {
        SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();

        OnTaskSuccess<Void> successCallback = (data) -> mOnSuccess.onSuccess(null);
        OnTaskError<ServiceError> errorCallback = (error) -> {
            mOnError.onError(
                    mInternalData.errorsMapper.mapToSurveillanceServiceError(
                            ExternalServiceErrorsMapper.FBS_MESSAGING_SERVICE_TYPE,
                            error
                    )
            );
        };

        SurveillanceStorage storage = AppStorages.get().surveillanceStorage();
        storage.removeGlobalNotificationsTopic(mContext);

        FBSMessagingService messagingService = FBSMessagingService.get();
        String globalNotificationsTopic = messagingService.globalTopic(mCurrentGroupName, mCurrentGroupPassword);
        messagingService.unsubscribeFromTopic(globalNotificationsTopic, successCallback, errorCallback);

        return null;
    }
}
