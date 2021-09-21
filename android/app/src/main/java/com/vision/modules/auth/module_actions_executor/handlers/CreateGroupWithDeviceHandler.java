package com.vision.modules.auth.module_actions_executor.handlers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.vision.common.constants.AppConstants;
import com.vision.common.data.hybrid_service_objects.device_info.DeviceInfo;
import com.vision.common.services.device_info.DeviceInfoService;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.modules.auth.module_actions.payloads.AuthJSActionsPayloads;
import com.vision.modules.auth.module_actions.payloads.payloads.CreateGroupWithDevicePayload;
import com.vision.modules.auth.module_errors.AuthModuleErrors;
import com.vision.modules.modules_common.data.error.ModuleError;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;

import java.util.List;

public class CreateGroupWithDeviceHandler implements JSActionHandler {
    private final String ACTION_PAYLOAD = "payload";

    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "CreateGroupWithDeviceHandler");

        ReadableMap payloadMap = action.getMap(ACTION_PAYLOAD);
        if (payloadMap == null) {
            ModuleError error = AuthModuleErrors.badPayload();
            result.reject(error.code(), error.message());
            return;
        }

        CreateGroupWithDevicePayload payload = AuthJSActionsPayloads
                .createGroupWithDevicePayload(payloadMap);
        if (!payload.isValid()) {
            String groupName = payload.groupName();
            if (groupName == null || groupName.isEmpty()) {
                ModuleError error = AuthModuleErrors.emptyGroupName();
                result.reject(error.code(), error.message());
                return;
            }

            String groupPassword = payload.groupPassword();
            if (groupPassword == null || groupPassword.isEmpty()) {
                ModuleError error = AuthModuleErrors.emptyGroupPassword();
                result.reject(error.code(), error.message());
                return;
            }

            String deviceName = payload.deviceName();
            if (deviceName == null || deviceName.isEmpty()) {
                ModuleError error = AuthModuleErrors.emptyDeviceName();
                result.reject(error.code(), error.message());
                return;
            }

            ModuleError error = AuthModuleErrors.badPayload();
            result.reject(error.code(), error.message());
            return;
        }

        String groupName = payload.groupName();
        String groupPassword = payload.groupPassword();
        String deviceName = payload.deviceName();

        Log.d("tag", "CreateGroupWithDeviceHandler->handle(): " + groupName + " - " + groupPassword + " - " + deviceName);

        checkIfGroupExist(context, payload, result);

        result.resolve(true);
    }

    private void checkIfGroupExist(Context context,
                                   CreateGroupWithDevicePayload handlerPayload,
                                   Promise handlerResult) {
        List<String> groupNamePath = FBSPathsService.get().groupNamePath(handlerPayload.groupName());

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("tag", "CreateGroupWithDeviceHandler->checkIfGroupExist(): GROUP_ALREADY_EXIST");

                    ModuleError error = AuthModuleErrors.groupAlreadyExist();
                    handlerResult.reject(error.code(), error.message());
                } else {
                    Log.d("tag", "CreateGroupWithDeviceHandler->checkIfGroupExist(): GROUP_NOT_EXIST");

                    createGroupWithDevice(context, handlerPayload, handlerResult);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ModuleError moduleError = AuthModuleErrors.createGroupWithDeviceFirebaseFailure();
                handlerResult.reject(moduleError.code(), moduleError.message());
            }
        };

        FBSService.get().getValue(groupNamePath, listener);
    }

    private void createGroupWithDevice(Context context,
                                       CreateGroupWithDevicePayload handlerPayload,
                                       Promise handlerResult) {
        Log.d("tag", "CreateGroupWithDeviceHandler->createGroupWithDevice()");

        String groupName = handlerPayload.groupName();
        String groupPassword = handlerPayload.groupPassword();
        String deviceName = handlerPayload.deviceName();

        List<String> deviceInfoPath = FBSPathsService.get().deviceInfoPath(groupName, groupPassword, deviceName);

        OnCompleteListener<Void> onCompleteListener = task -> {
            Log.d("tag", "CreateGroupWithDeviceHandler->createGroupWithDevice()->onComplete");

            SurveillanceService.get().init(context, groupName, groupPassword, deviceName);

            handlerResult.resolve(true);
        };

        OnFailureListener onFailureListener = e -> {
            Log.d("tag", "CreateGroupWithDeviceHandler->createGroupWithDevice()->onFailure: " + e.toString());

            ModuleError error = AuthModuleErrors.createGroupWithDeviceFirebaseFailure();
            handlerResult.reject(error.code(), error.message());
        };

        DeviceInfo deviceInfo = DeviceInfoService.get().currentDeviceInfo(
                context,
                deviceName,
                AppConstants.DEVICE_MODE_USER
        );

        FBSService.get().setMapValue(
                deviceInfoPath,
                deviceInfo.toServiceObject(),
                onCompleteListener,
                onFailureListener
        );
    }
}
