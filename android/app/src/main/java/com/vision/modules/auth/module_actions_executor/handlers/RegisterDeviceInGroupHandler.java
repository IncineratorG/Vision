package com.vision.modules.auth.module_actions_executor.handlers;


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
import com.vision.common.data.hybrid_service_objects.device_info.DeviceInfo;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.modules.auth.module_actions.payloads.AuthJSActionsPayloads;
import com.vision.modules.auth.module_actions.payloads.payloads.RegisterDeviceInGroupPayload;
import com.vision.modules.auth.module_errors.AuthModuleErrors;
import com.vision.modules.modules_common.data.error.ModuleError;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;

import java.util.List;

public class RegisterDeviceInGroupHandler implements JSActionHandler {
    private final String ACTION_PAYLOAD = "payload";

    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "RegisterDeviceInGroupHandler");

        ReadableMap payloadMap = action.getMap(ACTION_PAYLOAD);
        if (payloadMap == null) {
            ModuleError error = AuthModuleErrors.badPayload();
            result.reject(error.code(), error.message());
            return;
        }

        RegisterDeviceInGroupPayload payload = AuthJSActionsPayloads
                .registerDeviceInGroupPayload(payloadMap);
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

        Log.d("tag", "RegisterDeviceInGroupHandler->handle(): " + groupName + " - " + groupPassword + " - " + deviceName);

        checkIfGroupExist(payload, result);
    }

    private void checkIfGroupExist(RegisterDeviceInGroupPayload handlerPayload,
                                   Promise handlerResult) {
        List<String> groupNamePath = FBSPathsService.get().groupNamePath(handlerPayload.groupName());

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("tag", "RegisterDeviceInGroupHandler->checkIfGroupExist(): GROUP_EXIST");

                    checkIfPasswordCorrect(handlerPayload, handlerResult);
                } else {
                    Log.d("tag", "RegisterDeviceInGroupHandler->checkIfGroupExist(): GROUP_NOT_EXIST");

                    ModuleError error = AuthModuleErrors.groupNotExist();
                    handlerResult.reject(error.code(), error.message());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ModuleError moduleError = AuthModuleErrors.registerDeviceInGroupFirebaseFailure();
                handlerResult.reject(moduleError.code(), moduleError.message());
            }
        };

        FBSService.get().getValue(groupNamePath, listener);
    }

    private void checkIfPasswordCorrect(RegisterDeviceInGroupPayload handlerPayload,
                                        Promise handlerResult) {
        String groupName = handlerPayload.groupName();
        String groupPassword = handlerPayload.groupPassword();

        List<String> groupPasswordPath = FBSPathsService.get().groupPasswordPath(groupName, groupPassword);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("tag", "RegisterDeviceInGroupHandler->checkIfPasswordCorrect(): PASSWORD_CORRECT");

                    checkDeviceName(handlerPayload, handlerResult);
                } else {
                    Log.d("tag", "RegisterDeviceInGroupHandler->checkIfPasswordCorrect(): BAD_PASSWORD");

                    ModuleError error = AuthModuleErrors.incorrectGroupPassword();
                    handlerResult.reject(error.code(), error.message());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ModuleError moduleError = AuthModuleErrors.registerDeviceInGroupFirebaseFailure();
                handlerResult.reject(moduleError.code(), moduleError.message());
            }
        };

        FBSService.get().getValue(groupPasswordPath, listener);
    }

    private void checkDeviceName(RegisterDeviceInGroupPayload handlerPayload,
                                 Promise handlerResult) {
        String groupName = handlerPayload.groupName();
        String groupPassword = handlerPayload.groupPassword();
        String deviceName = handlerPayload.deviceName();

        List<String> devicePath = FBSPathsService.get().devicePath(groupName, groupPassword, deviceName);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("tag", "RegisterDeviceInGroupHandler->checkDeviceName(): DEVICE_NAME_ALREADY_EXIST");

                    ModuleError error = AuthModuleErrors.deviceNameAlreadyExist();
                    handlerResult.reject(error.code(), error.message());
                } else {
                    Log.d("tag", "RegisterDeviceInGroupHandler->checkDeviceName(): DEVICE_NAME_NOT_EXIST");

                    registerDeviceInGroup(handlerPayload, handlerResult);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ModuleError moduleError = AuthModuleErrors.registerDeviceInGroupFirebaseFailure();
                handlerResult.reject(moduleError.code(), moduleError.message());
            }
        };

        FBSService.get().getValue(devicePath, listener);
    }

    private void registerDeviceInGroup(RegisterDeviceInGroupPayload handlerPayload,
                                       Promise handlerResult) {
        String groupName = handlerPayload.groupName();
        String groupPassword = handlerPayload.groupPassword();
        String deviceName = handlerPayload.deviceName();

        List<String> deviceInfoPath = FBSPathsService.get().deviceInfoPath(groupName, groupPassword, deviceName);

        OnCompleteListener<Void> onCompleteListener = task -> {
            Log.d("tag", "RegisterDeviceInGroupHandler->registerDeviceInGroup()->onComplete");

            SurveillanceService.get().init(groupName, groupPassword, deviceName);

            handlerResult.resolve(true);
        };

        OnFailureListener onFailureListener = e -> {
            Log.d("tag", "RegisterDeviceInGroupHandler->registerDeviceInGroup()->onFailure: " + e.toString());

            ModuleError error = AuthModuleErrors.registerDeviceInGroupFirebaseFailure();
            handlerResult.reject(error.code(), error.message());
        };

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setLastLoginTimestamp(System.currentTimeMillis());
        deviceInfo.setDeviceName(deviceName);

        FBSService.get().setMapValue(
                deviceInfoPath,
                deviceInfo.toServiceObject(),
                onCompleteListener,
                onFailureListener
        );
    }
}
