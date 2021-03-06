package com.vision.services.device_info;

import android.content.Context;

import com.vision.common.data.hybrid_service_objects.device_info.DeviceInfo;
import com.vision.services.camera.CameraService;
import com.vision.services.device_movement.DeviceMovementService;

public class DeviceInfoService {
    public static final String NAME = "DeviceInfoService";

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
        deviceInfo.setCanRecognizePerson(canRecognizePerson());

        deviceInfo.setDeviceMovementServiceRunning(deviceMovementServiceRunning());
        deviceInfo.setFrontCameraRecognizePersonServiceRunning(frontCameraRecognizePersonServiceRunning());
        deviceInfo.setBackCameraRecognizePersonServiceRunning(backCameraRecognizePersonServiceRunning());

        return deviceInfo;
    }

    public DeviceInfo updateDeviceInfo(Context context, DeviceInfo deviceInfo, boolean needUpdateLoginTimestamp) {
        long timestamp = System.currentTimeMillis();

        DeviceInfo updatedDeviceInfo = new DeviceInfo(deviceInfo);
        if (needUpdateLoginTimestamp) {
            updatedDeviceInfo.setLastLoginTimestamp(timestamp);
        }
        updatedDeviceInfo.setLastUpdateTimestamp(timestamp);

        updatedDeviceInfo.setHasFrontCamera(hasFrontCamera());
        updatedDeviceInfo.setHasBackCamera(hasBackCamera());
        updatedDeviceInfo.setCanDetectDeviceMovement(canDetectDeviceMovement(context));
        updatedDeviceInfo.setCanRecognizePerson(canRecognizePerson());

        updatedDeviceInfo.setDeviceMovementServiceRunning(deviceMovementServiceRunning());
        updatedDeviceInfo.setFrontCameraRecognizePersonServiceRunning(frontCameraRecognizePersonServiceRunning());
        updatedDeviceInfo.setBackCameraRecognizePersonServiceRunning(backCameraRecognizePersonServiceRunning());

        return updatedDeviceInfo;
    }

    public DeviceInfo changeDeviceMode(String mode, DeviceInfo currentDeviceInfo) {
        long timestamp = System.currentTimeMillis();

        DeviceInfo updatedDeviceInfo = new DeviceInfo(currentDeviceInfo);
        updatedDeviceInfo.setLastUpdateTimestamp(timestamp);
        updatedDeviceInfo.setDeviceMode(mode);

        updatedDeviceInfo.setDeviceMovementServiceRunning(deviceMovementServiceRunning());
        updatedDeviceInfo.setFrontCameraRecognizePersonServiceRunning(frontCameraRecognizePersonServiceRunning());
        updatedDeviceInfo.setBackCameraRecognizePersonServiceRunning(backCameraRecognizePersonServiceRunning());

        return updatedDeviceInfo;
    }

    private boolean hasFrontCamera() {
        return CameraService.get().hasFrontCamera();
    }

    private boolean hasBackCamera() {
        return CameraService.get().hasBackCamera();
    }

    private boolean canDetectDeviceMovement(Context context) {
        return DeviceMovementService.get().canDetectDeviceMovement(context);
    }

    private boolean canRecognizePerson() {
        return CameraService.get().canRecognizePerson();
    }

    private boolean deviceMovementServiceRunning() {
        return DeviceMovementService.get().isRunning();
    }

    private boolean frontCameraRecognizePersonServiceRunning() {
        return CameraService.get().isFrontCameraRecognizePersonRunning();
    }

    private boolean backCameraRecognizePersonServiceRunning() {
        return CameraService.get().isBackCameraRecognizePersonRunning();
    }
}
