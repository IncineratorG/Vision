package com.vision.common.services.device_info;

import android.content.Context;
import android.hardware.Camera;

import com.vision.common.data.hybrid_service_objects.device_info.DeviceInfo;
import com.vision.common.services.camera.CameraService_V2;
import com.vision.common.services.device_movement.DeviceMovementService;

public class DeviceInfoService {
    private static DeviceInfoService sInstance;

    private DeviceInfoService() {

    }

    public static synchronized DeviceInfoService get() {
        if (sInstance == null) {
            sInstance = new DeviceInfoService();
        }

        return sInstance;
    }

    public DeviceInfo currentDeviceInfo(Context context,
                                        String deviceName,
                                        String deviceMode) {
        long timestamp = System.currentTimeMillis();

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setLastLoginTimestamp(timestamp);
        deviceInfo.setLastUpdateTimestamp(timestamp);
        deviceInfo.setDeviceName(deviceName);
        deviceInfo.setDeviceMode(deviceMode);
        deviceInfo.setHasFrontCamera(hasFrontCamera());
        deviceInfo.setHasBackCamera(hasBackCamera());
        deviceInfo.setCanDetectDeviceMovement(canDetectDeviceMovement(context));

        return deviceInfo;
    }

    public DeviceInfo updateDeviceInfo(Context context, DeviceInfo deviceInfo) {
        long timestamp = System.currentTimeMillis();

        DeviceInfo updatedDeviceInfo = new DeviceInfo(deviceInfo);
        updatedDeviceInfo.setLastLoginTimestamp(timestamp);
        updatedDeviceInfo.setLastUpdateTimestamp(timestamp);
        updatedDeviceInfo.setHasFrontCamera(hasFrontCamera());
        updatedDeviceInfo.setHasBackCamera(hasBackCamera());
        updatedDeviceInfo.setCanDetectDeviceMovement(canDetectDeviceMovement(context));

        return updatedDeviceInfo;
    }

    public DeviceInfo changeDeviceMode(String mode, DeviceInfo currentDeviceInfo) {
        long timestamp = System.currentTimeMillis();

        DeviceInfo updatedDeviceInfo = new DeviceInfo(currentDeviceInfo);
        updatedDeviceInfo.setLastUpdateTimestamp(timestamp);
        updatedDeviceInfo.setDeviceMode(mode);

        return updatedDeviceInfo;
    }

    private boolean hasFrontCamera() {
        return CameraService_V2.get().hasFrontCamera();
    }

    private boolean hasBackCamera() {
        return CameraService_V2.get().hasBackCamera();
    }

    private boolean canDetectDeviceMovement(Context context) {
        return DeviceMovementService.get().canDetectDeviceMovement(context);
    }
}
