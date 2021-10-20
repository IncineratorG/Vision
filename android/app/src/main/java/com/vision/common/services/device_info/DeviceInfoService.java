package com.vision.common.services.device_info;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.vision.common.constants.AppConstants;
import com.vision.common.data.hybrid_service_objects.device_info.DeviceInfo;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.common.services.camera.CameraService_V4;
import com.vision.common.services.device_movement.DeviceMovementService;
import com.vision.common.services.firebase_communication.FBSCommunicationService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.common.services.surveillance.data.service_errors.SurveillanceServiceErrors;

import java.util.List;

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
        return CameraService_V4.get().hasFrontCamera();
    }

    private boolean hasBackCamera() {
        return CameraService_V4.get().hasBackCamera();
    }

    private boolean canDetectDeviceMovement(Context context) {
        return DeviceMovementService.get().canDetectDeviceMovement(context);
    }

    private boolean canRecognizePerson() {
        return CameraService_V4.get().canRecognizePerson();
    }

    private boolean deviceMovementServiceRunning() {
        return DeviceMovementService.get().isRunning();
    }

    private boolean frontCameraRecognizePersonServiceRunning() {
        return CameraService_V4.get().isFrontCameraRecognizePersonRunning();
    }

    private boolean backCameraRecognizePersonServiceRunning() {
        return CameraService_V4.get().isBackCameraRecognizePersonRunning();
    }
}
