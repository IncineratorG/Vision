package com.vision.rn_modules.surveillance.module_actions_executor.handlers;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.common.data.hybrid_objects.device_info_list.DeviceInfoList;
import com.vision.common.data.hybrid_service_objects.device_info.DeviceInfo;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.rn_modules.modules_common.data.error.ModuleError;
import com.vision.rn_modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.rn_modules.surveillance.module_actions.payloads.SurveillanceJSActionsPayloads;
import com.vision.rn_modules.surveillance.module_actions.payloads.payloads.GetDevicesInGroupPayload;
import com.vision.rn_modules.surveillance.module_errors.SurveillanceModuleErrors;
import com.vision.rn_modules.surveillance.module_errors.mapper.SurveillanceModuleErrorsMapper;

public class GetDevicesInGroupHandler implements JSActionHandler {
    private final String ACTION_PAYLOAD = "payload";

    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "GetDevicesInGroupHandler->handle()");

        ReadableMap payloadMap = action.getMap(ACTION_PAYLOAD);
        if (payloadMap == null) {
            ModuleError error = SurveillanceModuleErrors.badPayload();
            result.reject(error.code(), error.message());
            return;
        }

        GetDevicesInGroupPayload payload = SurveillanceJSActionsPayloads
                .getDevicesInGroupPayload(payloadMap);
        if (!payload.isValid()) {
            String groupName = payload.groupName();
            if (groupName == null || groupName.isEmpty()) {
                ModuleError error = SurveillanceModuleErrors.emptyGroupName();
                result.reject(error.code(), error.message());
                return;
            }

            String groupPassword = payload.groupPassword();
            if (groupPassword == null || groupPassword.isEmpty()) {
                ModuleError error = SurveillanceModuleErrors.emptyGroupPassword();
                result.reject(error.code(), error.message());
                return;
            }

            String deviceName = payload.deviceName();
            if (deviceName == null || deviceName.isEmpty()) {
                ModuleError error = SurveillanceModuleErrors.emptyDeviceName();
                result.reject(error.code(), error.message());
                return;
            }

            ModuleError error = SurveillanceModuleErrors.badPayload();
            result.reject(error.code(), error.message());
            return;
        }

        SurveillanceService surveillanceService = SurveillanceService.get();
        surveillanceService.getDevicesInGroup(
                context,
                payload.groupName(),
                payload.groupPassword(),
                (devicesInGroupList) -> {
                    DeviceInfoList resultList = new DeviceInfoList();
                    for (int i = 0; i < devicesInGroupList.size(); ++i) {
                        DeviceInfo deviceInfo = devicesInGroupList.get(i);
                        if (!deviceInfo.deviceName().equals(payload.deviceName())) {
                            resultList.add(deviceInfo);
                        }
                    }
                    result.resolve(resultList.toWritableArray());
                },
                (error) -> {
                    ModuleError moduleError = SurveillanceModuleErrorsMapper.mapToModuleError(
                            SurveillanceModuleErrorsMapper.SURVEILLANCE_SERVICE_TYPE,
                            error
                    );
                    result.reject(moduleError.code(), moduleError.message());
                }
        );
    }
}
