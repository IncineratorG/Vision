package com.vision.modules.auth.module_actions_executor.handlers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.vision.common.constants.AppConstants;
import com.vision.common.data.hybrid_service_objects.device_info.DeviceInfo;
import com.vision.common.services.auth.AuthService;
import com.vision.common.services.device_info.DeviceInfoService;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.modules.auth.module_actions.payloads.AuthJSActionsPayloads;
import com.vision.modules.auth.module_actions.payloads.payloads.LoginDeviceInGroupPayload;
import com.vision.modules.auth.module_errors.AuthModuleErrors;
import com.vision.modules.auth.module_errors.AuthModuleErrorsMapper;
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

        SurveillanceService surveillanceService = SurveillanceService.get();
        surveillanceService.loginDeviceInGroup(
                context,
                payload.groupName(),
                payload.groupPassword(),
                payload.deviceName(),
                (data) -> {
                    result.resolve(true);
                },
                (error) -> {
                    ModuleError moduleError = AuthModuleErrorsMapper.mapToModuleError(
                            AuthModuleErrorsMapper.SURVEILLANCE_SERVICE_TYPE,
                            error
                    );
                    result.reject(moduleError.code(), moduleError.message());
                }
        );
    }
}
