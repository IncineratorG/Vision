package com.vision.services.surveillance.data.service_internal.tasks.tasks.update_and_publish_device_info;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.vision.common.data.hybrid_service_objects.device_info.DeviceInfo;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.device_info.DeviceInfoService;
import com.vision.services.firebase_communication.FBSCommunicationService;
import com.vision.services.firebase_paths.FBSPathsService;
import com.vision.services.surveillance.data.service_errors.SurveillanceServiceErrors;
import com.vision.services.surveillance.data.service_internal.data.internal_data.SurveillanceServiceInternalData;
import com.vision.services.surveillance.data.service_internal.interfaces.internal_task.InternalTask;

import java.util.List;
import java.util.Map;

public class UpdateAndPublishDeviceInfoSurveillanceServiceTask implements InternalTask {
    private Context mContext;
    private boolean mNeedUpdateDeviceMode;
    private String mCurrentGroupName;
    private String mCurrentGroupPassword;
    private String mCurrentDeviceName;
    private String mCurrentServiceMode;
    private OnTaskSuccess<Void> mOnSuccess;
    private OnTaskError<ServiceError> mOnError;

    public UpdateAndPublishDeviceInfoSurveillanceServiceTask(Context context,
                                                             boolean needUpdateDeviceMode,
                                                             String currentGroupName,
                                                             String currentGroupPassword,
                                                             String currentDeviceName,
                                                             String currentServiceMode,
                                                             OnTaskSuccess<Void> onSuccess,
                                                             OnTaskError<ServiceError> onError) {
        mContext = context;
        mNeedUpdateDeviceMode = needUpdateDeviceMode;
        mCurrentGroupName = currentGroupName;
        mCurrentGroupPassword = currentGroupPassword;
        mCurrentDeviceName = currentDeviceName;
        mCurrentServiceMode = currentServiceMode;
        mOnSuccess = onSuccess;
        mOnError = onError;
    }

    @Override
    public Object run(Map<String, Object> params) {
        SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();

        List<String> deviceInfoPath = FBSPathsService.get().deviceInfoPath(
                mCurrentGroupName,mCurrentGroupPassword, mCurrentDeviceName
        );
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Object value = snapshot.getValue();
                    DeviceInfo currentDeviceInfo;

                    if (value != null) {
                        currentDeviceInfo = new DeviceInfo(value);
                    } else {
                        currentDeviceInfo = DeviceInfoService.get().currentDeviceInfo(
                                mContext,
                                mCurrentDeviceName,
                                mCurrentServiceMode
                        );
                    }
                    DeviceInfo updatedDeviceInfo = DeviceInfoService.get().updateDeviceInfo(mContext, currentDeviceInfo, false);
                    if (mNeedUpdateDeviceMode) {
                        updatedDeviceInfo = DeviceInfoService.get().changeDeviceMode(
                                mCurrentServiceMode,
                                updatedDeviceInfo
                        );
                    }
                    FBSCommunicationService.get().setMapValue(
                            deviceInfoPath,
                            updatedDeviceInfo.toServiceObject(),
                            (data) -> mOnSuccess.onSuccess(null),
                            (error) -> mOnError.onError(SurveillanceServiceErrors.firebaseFailure())
                    );
                } else {
                    mOnError.onError(SurveillanceServiceErrors.firebaseFailure());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mOnError.onError(SurveillanceServiceErrors.firebaseFailure());
            }
        };
        FBSCommunicationService.get().getValue(deviceInfoPath, listener);

        return null;
    }
}
