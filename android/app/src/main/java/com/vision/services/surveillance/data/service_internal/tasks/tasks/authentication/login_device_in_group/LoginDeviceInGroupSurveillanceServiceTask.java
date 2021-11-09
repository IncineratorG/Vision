package com.vision.services.surveillance.data.service_internal.tasks.tasks.authentication.login_device_in_group;

import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.auth.AuthService;
import com.vision.services.surveillance.data.service_errors.external_service_errors_mapper.ExternalServiceErrorsMapper;
import com.vision.services.surveillance.data.service_internal.data.internal_data.SurveillanceServiceInternalData;
import com.vision.services.surveillance.data.service_internal.interfaces.internal_task.InternalTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.SurveillanceServiceInternalTasks;

import java.util.Map;

public class LoginDeviceInGroupSurveillanceServiceTask implements InternalTask {
    private Context mContext;
    private String mGroupName;
    private String mGroupPassword;
    private String mDeviceName;
    private OnTaskSuccess<Void> mOnSuccess;
    private OnTaskError<ServiceError> mOnError;

    public LoginDeviceInGroupSurveillanceServiceTask(Context context,
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
            OnTaskSuccess<Void> initSuccessCallback = (initData) -> {
                Log.d("tag", "SurveillanceService->loginDeviceInGroup(): 1");

                mOnSuccess.onSuccess(null);
            };
            OnTaskError<ServiceError> initErrorCallback = (errorData) -> mOnError.onError(errorData);

            SurveillanceServiceInternalTasks.initSurveillanceServiceTask(
                    mContext,
                    mGroupName,
                    mGroupPassword,
                    mDeviceName,
                    initSuccessCallback,
                    initErrorCallback
            ).run(null);
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

        AuthService.get().loginDeviceInGroup(
                mContext, mGroupName, mGroupPassword, mDeviceName, successCallback, errorCallback
        );

        return null;
    }
}
