package com.vision.services.surveillance.service_internal_tasks.tasks.get_devices_in_group;

import android.content.Context;

import com.vision.common.data.hybrid_objects.device_info_list.DeviceInfoList;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.device_group.DeviceGroupService;
import com.vision.services.surveillance.data.service_errors.external_service_errors_mapper.ExternalServiceErrorsMapper;
import com.vision.services.surveillance.data.internal_data.SurveillanceServiceInternalData;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;

import java.util.Map;

public class GetDevicesInGroupTask implements ServiceSyncTask {
    private Context mContext;
    private String mGroupName;
    private String mGroupPassword;
    private OnTaskSuccess<DeviceInfoList> mOnSuccess;
    private OnTaskError<ServiceError> mOnError;

    public GetDevicesInGroupTask(Context context,
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
                    mInternalData.errorsMapper.mapToSurveillanceServiceError(
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
