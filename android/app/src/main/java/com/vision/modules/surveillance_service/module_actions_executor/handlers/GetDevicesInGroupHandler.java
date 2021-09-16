package com.vision.modules.surveillance_service.module_actions_executor.handlers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.vision.common.data.hybrid_objects.device_info_list.DeviceInfoList;
import com.vision.common.data.hybrid_service_objects.device_info.DeviceInfo;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.modules.modules_common.data.error.ModuleError;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.modules.surveillance_service.module_actions.payloads.SurveillanceServiceJSActionsPayloads;
import com.vision.modules.surveillance_service.module_actions.payloads.payloads.GetDevicesInGroupPayload;
import com.vision.modules.surveillance_service.module_errors.SurveillanceServiceModuleErrors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetDevicesInGroupHandler implements JSActionHandler {
    private final String ACTION_PAYLOAD = "payload";

    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "GetDevicesInGroupHandler->handle()");

        ReadableMap payloadMap = action.getMap(ACTION_PAYLOAD);
        if (payloadMap == null) {
            ModuleError error = SurveillanceServiceModuleErrors.badPayload();
            result.reject(error.code(), error.message());
            return;
        }

        GetDevicesInGroupPayload payload = SurveillanceServiceJSActionsPayloads
                .getDevicesInGroupPayload(payloadMap);
        if (!payload.isValid()) {
            String groupName = payload.groupName();
            if (groupName == null || groupName.isEmpty()) {
                ModuleError error = SurveillanceServiceModuleErrors.emptyGroupName();
                result.reject(error.code(), error.message());
                return;
            }

            String groupPassword = payload.groupPassword();
            if (groupPassword == null || groupPassword.isEmpty()) {
                ModuleError error = SurveillanceServiceModuleErrors.emptyGroupPassword();
                result.reject(error.code(), error.message());
                return;
            }

            String deviceName = payload.deviceName();
            if (deviceName == null || deviceName.isEmpty()) {
                ModuleError error = SurveillanceServiceModuleErrors.emptyDeviceName();
                result.reject(error.code(), error.message());
                return;
            }

            ModuleError error = SurveillanceServiceModuleErrors.badPayload();
            result.reject(error.code(), error.message());
            return;
        }

        String groupName = payload.groupName();
        String groupPassword = payload.groupPassword();
        String deviceName = payload.deviceName();

        Log.d("tag", "GetDevicesInGroupHandler->handle(): " + groupName + " - " + groupPassword + " - " + deviceName);

        List<String> groupRootPath = FBSPathsService.get().groupRootPath(groupName, groupPassword);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DeviceInfoList deviceInfoList = new DeviceInfoList();

                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String key = childSnapshot.getKey();
                        Log.d("tag", "GetDevicesInGroupHandler->handle()->DEVICE_IN_GROUP: " + key);

                        Object deviceDataObject = childSnapshot.getValue();
                        if (deviceDataObject != null) {
                            Map<String, Object> deviceDataMap = (Map<String, Object>) deviceDataObject;
                            if (deviceDataMap != null) {
                                Object deviceInfoObject = deviceDataMap.get(FBSPathsService.get().DEVICE_INFO_PATH);
                                if (deviceInfoObject != null) {
                                    DeviceInfo deviceInfo = new DeviceInfo(deviceInfoObject);

                                    Log.d("tag", "GetDevicesInGroupHandler->handle()->DEVICE_INFO_OBJECT: " + deviceInfo.deviceName() + " - " + deviceInfo.lastLoginTimestamp());

                                    if (!deviceInfo.deviceName().equalsIgnoreCase(deviceName)) {
                                        deviceInfoList.add(deviceInfo);
                                    }
                                } else {
                                    Log.d("tag", "GetDevicesInGroupHandler->handle()->DEVICE_INFO_OBJECT_IS_NULL");
                                }
                            } else {
                                Log.d("tag", "GetDevicesInGroupHandler->handle()->DEVICE_DATA_MAP_IS_NULL");
                            }
                        } else {
                            Log.d("tag", "GetDevicesInGroupHandler->handle()->DEVICE_DATA_OBJECT_IS_NULL");
                        }
                    }
                } else {
                    Log.d("tag", "GetDevicesInGroupHandler->handle()->ROOT_SNAPSHOT_NOT_EXIST");
                }

                result.resolve(deviceInfoList.toWritableArray());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ModuleError moduleError = SurveillanceServiceModuleErrors.getDevicesInGroupFirebaseFailure();
                result.reject(moduleError.code(), moduleError.message());
            }
        };

        FBSService.get().getValue(groupRootPath, listener);
    }
}
