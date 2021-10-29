package com.vision.services.device_group;


import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.vision.common.data.hybrid_objects.device_info_list.DeviceInfoList;
import com.vision.common.data.hybrid_service_objects.device_info.DeviceInfo;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.device_group.data.service_errors.DeviceGroupServiceErrors;
import com.vision.services.firebase_communication.FBSCommunicationService;
import com.vision.services.firebase_paths.FBSPathsService;

import java.util.List;
import java.util.Map;

public class DeviceGroupService {
    public static final String NAME = "DeviceGroupService";

    private static DeviceGroupService sInstance;

    public DeviceGroupService() {

    }

    public static DeviceGroupService get() {
        if (sInstance == null) {
            sInstance = new DeviceGroupService();
        }

        return sInstance;
    }

    public void getDevicesInGroup(Context context,
                                  String groupName,
                                  String groupPassword,
                                  OnTaskSuccess<DeviceInfoList> onSuccess,
                                  OnTaskError<ServiceError> onError) {
        List<String> groupRootPath = FBSPathsService.get().groupRootPath(groupName, groupPassword);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DeviceInfoList deviceInfoList = new DeviceInfoList();

                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        Object deviceDataObject = childSnapshot.getValue();
                        if (deviceDataObject != null) {
                            Map<String, Object> deviceDataMap = (Map<String, Object>) deviceDataObject;
                            if (deviceDataMap != null) {
                                Object deviceInfoObject = deviceDataMap.get(FBSPathsService.get().DEVICE_INFO_PATH);
                                if (deviceInfoObject != null) {
                                    DeviceInfo deviceInfo = new DeviceInfo(deviceInfoObject);
                                    deviceInfoList.add(deviceInfo);
                                }
                            }
                        }
                    }
                }

                onSuccess.onSuccess(deviceInfoList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onError.onError(DeviceGroupServiceErrors.firebaseFailure());
            }
        };

        FBSCommunicationService.get().getValue(groupRootPath, listener);
    }
}
