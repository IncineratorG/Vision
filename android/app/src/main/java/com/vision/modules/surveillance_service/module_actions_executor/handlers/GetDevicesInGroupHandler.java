package com.vision.modules.surveillance_service.module_actions_executor.handlers;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.modules.modules_common.data.error.ModuleError;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.modules.surveillance_service.module_actions.payloads.SurveillanceServiceJSActionsPayloads;
import com.vision.modules.surveillance_service.module_actions.payloads.payloads.GetDevicesInGroupPayload;
import com.vision.modules.surveillance_service.module_errors.SurveillanceServiceModuleErrors;

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

        result.resolve(true);
    }
}
