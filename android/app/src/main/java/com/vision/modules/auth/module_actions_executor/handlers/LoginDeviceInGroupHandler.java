package com.vision.modules.auth.module_actions_executor.handlers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.vision.common.data.hybrid_service_objects.device_info.DeviceInfo;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.modules.auth.module_actions.payloads.AuthJSActionsPayloads;
import com.vision.modules.auth.module_actions.payloads.payloads.LoginDeviceInGroupPayload;
import com.vision.modules.auth.module_actions.payloads.payloads.RegisterDeviceInGroupPayload;
import com.vision.modules.auth.module_errors.AuthModuleErrors;
import com.vision.modules.modules_common.data.error.ModuleError;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;

import java.util.List;

public class LoginDeviceInGroupHandler implements JSActionHandler {
    private final String ACTION_PAYLOAD = "payload";

    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "LoginDeviceInGroupHandler");

        ReadableMap payloadMap = action.getMap(ACTION_PAYLOAD);
        if (payloadMap == null) {
            ModuleError error = AuthModuleErrors.badPayload();
            result.reject(error.code(), error.message());
            return;
        }

        LoginDeviceInGroupPayload payload = AuthJSActionsPayloads
                .loginDeviceInGroupPayload(payloadMap);
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

        Log.d("tag", "LoginDeviceInGroupHandler->handle(): " + groupName + " - " + groupPassword + " - " + deviceName);

        checkIfGroupExist(payload, result);
    }

    private void checkIfGroupExist(LoginDeviceInGroupPayload handlerPayload,
                                   Promise handlerResult) {
        List<String> groupNamePath = FBSPathsService.get().groupNamePath(handlerPayload.groupName());

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("tag", "LoginDeviceInGroupHandler->checkIfGroupExist(): GROUP_EXIST");

                    checkIfPasswordCorrect(handlerPayload, handlerResult);
                } else {
                    Log.d("tag", "LoginDeviceInGroupHandler->checkIfGroupExist(): GROUP_NOT_EXIST");

                    ModuleError error = AuthModuleErrors.groupNotExist();
                    handlerResult.reject(error.code(), error.message());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ModuleError moduleError = AuthModuleErrors.loginDeviceInGroupFirebaseFailure();
                handlerResult.reject(moduleError.code(), moduleError.message());
            }
        };

        FBSService.get().getValue(groupNamePath, listener);
    }

    private void checkIfPasswordCorrect(LoginDeviceInGroupPayload handlerPayload,
                                        Promise handlerResult) {
        String groupName = handlerPayload.groupName();
        String groupPassword = handlerPayload.groupPassword();

        List<String> groupPasswordPath = FBSPathsService.get().groupPasswordPath(groupName, groupPassword);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("tag", "LoginDeviceInGroupHandler->checkIfPasswordCorrect(): PASSWORD_CORRECT");

                    checkDeviceName(handlerPayload, handlerResult);
                } else {
                    Log.d("tag", "LoginDeviceInGroupHandler->checkIfPasswordCorrect(): BAD_PASSWORD");

                    ModuleError error = AuthModuleErrors.incorrectGroupPassword();
                    handlerResult.reject(error.code(), error.message());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ModuleError moduleError = AuthModuleErrors.loginDeviceInGroupFirebaseFailure();
                handlerResult.reject(moduleError.code(), moduleError.message());
            }
        };

        FBSService.get().getValue(groupPasswordPath, listener);
    }

    private void checkDeviceName(LoginDeviceInGroupPayload handlerPayload,
                                 Promise handlerResult) {
        String groupName = handlerPayload.groupName();
        String groupPassword = handlerPayload.groupPassword();
        String deviceName = handlerPayload.deviceName();

        List<String> deviceInfoPath = FBSPathsService.get().deviceInfoPath(groupName, groupPassword, deviceName);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("tag", "LoginDeviceInGroupHandler->checkDeviceName(): DEVICE_NAME_EXIST");

                    Object value = snapshot.getValue();
                    if (value != null) {
                        Log.d("tag", "LoginDeviceInGroupHandler->checkDeviceName()->VALUE: " + value.toString());
                        DeviceInfo deviceInfo = new DeviceInfo(value);
                        deviceInfo.setLastLoginTimestamp(System.currentTimeMillis());
                        deviceInfo.setDeviceName(deviceName);

                        updateDeviceInfo(deviceInfoPath, deviceInfo, groupName, groupPassword, deviceName);
                    } else {
                        Log.d("tag", "LoginDeviceInGroupHandler->checkDeviceName(): VALUE_IS_NULL");

                        DeviceInfo deviceInfo = new DeviceInfo();
                        deviceInfo.setLastLoginTimestamp(System.currentTimeMillis());
                        deviceInfo.setDeviceName(deviceName);

                        updateDeviceInfo(deviceInfoPath, deviceInfo, groupName, groupPassword, deviceName);
                    }

                    handlerResult.resolve(true);
                } else {
                    Log.d("tag", "LoginDeviceInGroupHandler->checkDeviceName(): DEVICE_NAME_NOT_EXIST");

                    ModuleError moduleError = AuthModuleErrors.loginDeviceInGroupFirebaseFailure();
                    handlerResult.reject(moduleError.code(), moduleError.message());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ModuleError moduleError = AuthModuleErrors.loginDeviceInGroupFirebaseFailure();
                handlerResult.reject(moduleError.code(), moduleError.message());
            }
        };

        FBSService.get().getValue(deviceInfoPath, listener);
    }

    private void updateDeviceInfo(List<String> deviceInfoPath,
                                  DeviceInfo updatedDeviceInfo,
                                  String groupName,
                                  String groupPassword,
                                  String deviceName) {
        SurveillanceService.get().setCurrentUserData(groupName, groupPassword, deviceName);

        FBSService.get().setMapValue(deviceInfoPath, updatedDeviceInfo.toServiceObject());
    }
}
