package com.vision.modules.surveillance.module_actions_executor.handlers;

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
import com.vision.common.services.device_info.DeviceInfoService;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.modules.modules_common.data.error.ModuleError;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.modules.surveillance.module_errors.SurveillanceModuleErrors;

import java.util.List;

public class StopServiceHandler implements JSActionHandler {
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "StopServiceHandler");

        stopSurveillanceService(context, result);
    }

    private void stopSurveillanceService(Context context, Promise handlerResult) {
        SurveillanceService surveillanceService = SurveillanceService.get();

        String groupName = surveillanceService.currentGroupName();
        String groupPassword = surveillanceService.currentGroupPassword();
        String deviceName = surveillanceService.currentDeviceName();

        List<String> deviceInfoPath = FBSPathsService.get().deviceInfoPath(groupName, groupPassword, deviceName);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("tag", "StopServiceHandler->stopSurveillanceService(): SNAPSHOT_EXIST");

                    Object value = snapshot.getValue();
                    if (value != null) {
                        SurveillanceService.get().stopForegroundService(context);

                        DeviceInfo currentDeviceInfo = new DeviceInfo(value);
                        DeviceInfo updatedDeviceInfo = DeviceInfoService.get().changeDeviceMode(
                                AppConstants.DEVICE_MODE_USER,
                                currentDeviceInfo
                        );

                        updateDeviceInfo(deviceInfoPath, updatedDeviceInfo);

                        handlerResult.resolve(true);
                    } else {
                        Log.d("tag", "StopServiceHandler->stopSurveillanceService(): SNAPSHOT_VALUE_IS_NULL");

                        ModuleError moduleError = SurveillanceModuleErrors.badRemoteDeviceInfo();
                        handlerResult.reject(moduleError.code(), moduleError.message());
                    }
                } else {
                    Log.d("tag", "StopServiceHandler->stopSurveillanceService(): SNAPSHOT_NOT_EXIST");

                    ModuleError moduleError = SurveillanceModuleErrors.stopServiceFirebaseFailure();
                    handlerResult.reject(moduleError.code(), moduleError.message());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ModuleError moduleError = SurveillanceModuleErrors.stopServiceFirebaseFailure();
                handlerResult.reject(moduleError.code(), moduleError.message());
            }
        };

        FBSService.get().getValue(deviceInfoPath, listener);
    }

    private void updateDeviceInfo(List<String> deviceInfoPath, DeviceInfo deviceInfo) {
        FBSService.get().setMapValue(deviceInfoPath, deviceInfo.toServiceObject());
    }
}
