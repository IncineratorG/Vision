package com.vision.services.surveillance.data.service_internal.tasks.tasks.get_devices_in_group;

import android.content.Context;

import com.vision.common.data.hybrid_objects.device_info_list.DeviceInfoList;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.device_group.DeviceGroupService;
import com.vision.services.surveillance.data.service_errors.external_service_errors_mapper.ExternalServiceErrorsMapper;
import com.vision.services.surveillance.data.service_internal.data.internal_data.SurveillanceServiceInternalData;
import com.vision.services.surveillance.data.service_internal.interfaces.internal_task.InternalTask;

import java.util.Map;

public class GetDevicesInGroupSurveillanceServiceTask implements InternalTask {
    private Context mContext;
    private String mGroupName;
    private String mGroupPassword;
    private OnTaskSuccess<DeviceInfoList> mOnSuccess;
    private OnTaskError<ServiceError> mOnError;

    public GetDevicesInGroupSurveillanceServiceTask(Context context,
                                                    String groupName,
                                                    String groupPassword,
                                                    OnTaskSuccess<DeviceInfoList> onSuccess,
                                                    OnTaskError<ServiceError> onError) {
        mContext = context;
        mGroupName = groupName;
        mGroupPassword = groupPassword;
        mOnSuccess = onSuccess;
        mOnError = onError;
    }

    @Override
    public Object run(Map<String, Object> params) {
        OnTaskError<ServiceError> errorCallback = (error) -> {
            SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();

            mOnError.onError(
                    mInternalData.mErrorsMapper.mapToSurveillanceServiceError(
                            ExternalServiceErrorsMapper.DEVICE_GROUP_SERVICE_TYPE,
                            error
                    )
            );
        };

        DeviceGroupService.get().getDevicesInGroup(
                mContext, mGroupName, mGroupPassword, mOnSuccess, errorCallback
        );

        return null;
    }
}
