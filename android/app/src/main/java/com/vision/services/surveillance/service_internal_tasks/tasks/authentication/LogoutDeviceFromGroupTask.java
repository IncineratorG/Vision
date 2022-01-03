package com.vision.services.surveillance.service_internal_tasks.tasks.authentication;

import android.content.Context;

import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.auth.AuthService;
import com.vision.services.surveillance.data.service_errors.external_service_errors_mapper.ExternalServiceErrorsMapper;
import com.vision.services.surveillance.data.internal_data.SurveillanceServiceInternalData;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.SurveillanceServiceInternalTasks;

import java.util.Map;

public class LogoutDeviceFromGroupTask implements ServiceSyncTask {
    private Context mContext;
    private String mCurrentGroupName;
    private String mCurrentGroupPassword;
    private String mCurrentDeviceName;
    private OnTaskSuccess<Void> mOnSuccess;
    private OnTaskError<ServiceError> mOnError;

    public LogoutDeviceFromGroupTask(Context context,
                                     String currentGroupName,
                                     String currentGroupPassword,
                                     String currentDeviceName,
                                     OnTaskSuccess<Void> onSuccess,
                                     OnTaskError<ServiceError> onError) {
        mContext = context;
        mCurrentGroupName = currentGroupName;
        mCurrentGroupPassword = currentGroupPassword;
        mCurrentDeviceName = currentDeviceName;
        mOnSuccess = onSuccess;
        mOnError = onError;
    }

    @Override
    public Object run(Map<String, Object> params) {
        OnTaskSuccess<Void> successCallback = (data) -> {
            mOnSuccess.onSuccess(null);
        };

        OnTaskError<ServiceError> errorCallback = (error) -> {
            SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();

            mOnError.onError(
                    mInternalData.errorsMapper.mapToSurveillanceServiceError(
                            ExternalServiceErrorsMapper.AUTH_SERVICE_TYPE,
                            error
                    )
            );
        };

        SurveillanceServiceInternalTasks.disposeSurveillanceServiceTask(
                mContext,
                mCurrentGroupName,
                mCurrentGroupPassword,
                mCurrentDeviceName
        ).run(null);
        AuthService.get().logoutDeviceFromGroup(successCallback, errorCallback);

        return null;
    }
}
