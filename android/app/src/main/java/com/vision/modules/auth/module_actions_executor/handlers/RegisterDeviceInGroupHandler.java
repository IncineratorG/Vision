package com.vision.modules.auth.module_actions_executor.handlers;


import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;
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

        // Проверяем, существует ли данная группа.
        checkIfGroupExist(payload, result);

        // Проверяем корректность пароля.

        // Проверяем, доступно ли такое имя устройства в данной группе.

//        result.resolve(true);
    }

    private void checkIfGroupExist(RegisterDeviceInGroupPayload handlerPayload,
                                   Promise handlerResult) {
        List<String> groupNamePath = FBSPathsService.get().groupNamePath(handlerPayload.groupName());

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("tag", "SNAPSHOT_EXISTS");
                } else {
                    Log.d("tag", "SNAPSHOT_NOT_EXIST");

                    ModuleError error = AuthModuleErrors.groupNotExist();
                    handlerResult.reject(error.code(), error.message());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        FBSService.get().getValue(groupNamePath, listener);
    }
}
