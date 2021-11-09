package com.vision.services.surveillance.data.service_internal.tasks.tasks.authentication.logout_device_from_group;

import android.content.Context;

import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.auth.AuthService;
import com.vision.services.surveillance.data.service_errors.external_service_errors_mapper.ExternalServiceErrorsMapper;
import com.vision.services.surveillance.data.service_internal.data.internal_data.SurveillanceServiceInternalData;
import com.vision.services.surveillance.data.service_internal.interfaces.internal_task.InternalTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.SurveillanceServiceInternalTasks;

import java.util.Map;

public class LogoutDeviceFromGroupSurveillanceServiceTask implements InternalTask {
    private Context mContext;
    private String mCurrentGroupName;
    private String mCurrentGroupPassword;
    private String mCurrentDeviceName;
    private String mCurrentServiceMode;
    private OnTaskSuccess<Void> mOnSuccess;
    private OnTaskError<ServiceError> mOnError;

    public LogoutDeviceFromGroupSurveillanceServiceTask(Context context,
                                                        String currentGroupName,
                                                        String currentGroupPassword,
                                                        String currentDeviceName,
                                                        String currentServiceMode,
                                                        OnTaskSuccess<Void> onSuccess,
                                                        OnTaskError<ServiceError> onError) {
        mContext = context;
        mCurrentGroupName = currentGroupName;
        mCurrentGroupPassword = currentGroupPassword;
        mCurrentDeviceName = currentDeviceName;
        mCurrentServiceMode = currentServiceMode;
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
                    mInternalData.mErrorsMapper.mapToSurveillanceServiceError(
                            ExternalServiceErrorsMapper.AUTH_SERVICE_TYPE,
                            error
                    )
            );
        };

        SurveillanceServiceInternalTasks.disposeSurveillanceServiceTask(
                mContext,
                mCurrentGroupName,
                mCurrentGroupPassword,
                mCurrentDeviceName,
                mCurrentServiceMode
        ).run(null);
        AuthService.get().logoutDeviceFromGroup(successCallback, errorCallback);

        return null;
    }
}
