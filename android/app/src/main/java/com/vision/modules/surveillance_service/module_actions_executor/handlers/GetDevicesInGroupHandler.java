package com.vision.modules.surveillance_service.module_actions_executor.handlers;

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
import com.vision.modules.modules_common.data.error.ModuleError;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.modules.surveillance_service.module_actions.payloads.SurveillanceServiceJSActionsPayloads;
import com.vision.modules.surveillance_service.module_actions.payloads.payloads.GetDevicesInGroupPayload;
import com.vision.modules.surveillance_service.module_errors.SurveillanceServiceModuleErrors;

import java.util.List;

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
                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String key = childSnapshot.getKey();
                        Log.d("tag", "GetDevicesInGroupHandler->handle()->DEVICE_IN_GROUP: " + key);
                    }
                } else {
                    Log.d("tag", "GetDevicesInGroupHandler->handle()->ROOT_SNAPSHOT_NOT_EXIST");
                }

                result.resolve(true);
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
