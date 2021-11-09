package com.vision.services.surveillance.data.service_internal.tasks.tasks.notifications.subscribe_to_global_notifications;

import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.app_storages.AppStorages;
import com.vision.services.app_storages.surveillance.SurveillanceStorage;
import com.vision.services.firebase_messaging.FBSMessagingService;
import com.vision.services.surveillance.data.service_errors.external_service_errors_mapper.ExternalServiceErrorsMapper;
import com.vision.services.surveillance.data.service_internal.data.internal_data.SurveillanceServiceInternalData;
import com.vision.services.surveillance.data.service_internal.interfaces.internal_task.InternalTask;

import java.util.Map;

public class SubscribeToGlobalNotificationsSurveillanceServiceTask implements InternalTask {
    private Context mContext;
    private String mCurrentGroupName;
    private String mCurrentGroupPassword;
    private OnTaskSuccess<Void> mOnSuccess;
    private OnTaskError<ServiceError> mOnError;

    public SubscribeToGlobalNotificationsSurveillanceServiceTask(Context context,
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

        FBSMessagingService messagingService = FBSMessagingService.get();
        SurveillanceStorage storage = AppStorages.get().surveillanceStorage();

        String savedGlobalTopic = storage.getGlobalNotificationsTopic(mContext);
        String currentGlobalTopic = messagingService.globalTopic(mCurrentGroupName, mCurrentGroupPassword);

        OnTaskSuccess<Void> successCallback = (data) -> {
            Log.d("tag", "SubscribeToGlobalNotificationsSurveillanceServiceTask->run()->successCallback");

            storage.saveGlobalNotificationsTopic(mContext, currentGlobalTopic);
            mOnSuccess.onSuccess(null);
        };
        OnTaskError<ServiceError> errorCallback = (error) -> {
            mOnError.onError(
                    mInternalData.mErrorsMapper.mapToSurveillanceServiceError(
                            ExternalServiceErrorsMapper.FBS_MESSAGING_SERVICE_TYPE,
                            error
                    )
            );
        };

        if (savedGlobalTopic == null) {
            Log.d("tag", "SubscribeToGlobalNotificationsSurveillanceServiceTask->run(): 1");

            messagingService.subscribeToTopic(currentGlobalTopic, successCallback, errorCallback);
        } else if (savedGlobalTopic.equals(currentGlobalTopic)) {
            Log.d("tag", "SubscribeToGlobalNotificationsSurveillanceServiceTask->run(): 2");

//            successCallback.onSuccess(null);
            messagingService.subscribeToTopic(currentGlobalTopic, successCallback, errorCallback);
        } else {
            Log.d("tag", "SubscribeToGlobalNotificationsSurveillanceServiceTask->run(): 3");

            messagingService.unsubscribeFromTopic(
                    savedGlobalTopic,
                    (resultUnsubscribe) -> {
                        Log.d("tag", "SubscribeToGlobalNotificationsSurveillanceServiceTask->run(): 4");

                        messagingService.subscribeToTopic(
                                currentGlobalTopic,
                                successCallback,
                                errorCallback
                        );
                    },
                    errorCallback
            );
        }

        return null;
    }
}
