package com.vision.modules.app_settings.module_actions_executor.handlers;


import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;

public class GetAppSettingsHandler implements JSActionHandler {
    private final String ACTION_PAYLOAD = "payload";

    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "GetAppSettingsHandler->handle()");

//        ReadableMap payloadMap = action.getMap(ACTION_PAYLOAD);
//        if (payloadMap == null) {
//            ModuleError error = AuthModuleErrors.badPayload();
//            result.reject(error.code(), error.message());
//            return;
//        }
//
//        CreateGroupWithDevicePayload payload = AuthJSActionsPayloads
//                .createGroupWithDevicePayload(payloadMap);
//        if (!payload.isValid()) {
//            String groupName = payload.groupName();
//            if (groupName == null || groupName.isEmpty()) {
//                ModuleError error = AuthModuleErrors.emptyGroupName();
//                result.reject(error.code(), error.message());
//                return;
//            }
//
//            String groupPassword = payload.groupPassword();
//            if (groupPassword == null || groupPassword.isEmpty()) {
//                ModuleError error = AuthModuleErrors.emptyGroupPassword();
//                result.reject(error.code(), error.message());
//                return;
//            }
//
//            String deviceName = payload.deviceName();
//            if (deviceName == null || deviceName.isEmpty()) {
//                ModuleError error = AuthModuleErrors.emptyDeviceName();
//                result.reject(error.code(), error.message());
//                return;
//            }
//
//            ModuleError error = AuthModuleErrors.badPayload();
//            result.reject(error.code(), error.message());
//            return;
//        }

        result.resolve(true);
    }
}