package com.vision.services.surveillance.service_internal_tasks.tasks.authentication.login_device_in_group;

import android.content.Context;

import com.vision.common.data.hybrid_objects.authentication_data.AuthenticationData;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.app_storages.AppStorages;
import com.vision.services.auth.AuthService;
import com.vision.services.surveillance.data.service_errors.external_service_errors_mapper.ExternalServiceErrorsMapper;
import com.vision.services.surveillance.data.internal_data.SurveillanceServiceInternalData;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.SurveillanceServiceInternalTasks;

import java.util.Map;

public class LoginDeviceInGroupTask implements ServiceSyncTask {
    private Context mContext;
    private String mGroupName;
    private String mGroupPassword;
    private String mDeviceName;
    private OnTaskSuccess<Void> mOnSuccess;
    private OnTaskError<ServiceError> mOnError;

    public LoginDeviceInGroupTask(Context context,
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
                AuthenticationData authenticationData = new AuthenticationData(mGroupName, mGroupPassword, mDeviceName);
                AppStorages.get().surveillanceStorage().saveLastAuthenticationData(mContext, authenticationData);

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
                    mInternalData.errorsMapper.mapToSurveillanceServiceError(
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
