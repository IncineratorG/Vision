package com.vision.common.services.device_info;

import android.content.Context;
import android.hardware.Camera;

import com.vision.common.data.hybrid_service_objects.device_info.DeviceInfo;

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

        return deviceInfo;
    }

    public DeviceInfo updateDeviceInfo(DeviceInfo deviceInfo) {
        long timestamp = System.currentTimeMillis();

        DeviceInfo updatedDeviceInfo = new DeviceInfo(deviceInfo);
        updatedDeviceInfo.setLastLoginTimestamp(timestamp);
        updatedDeviceInfo.setLastUpdateTimestamp(timestamp);
        updatedDeviceInfo.setHasFrontCamera(hasFrontCamera());
        updatedDeviceInfo.setHasBackCamera(hasBackCamera());

        return updatedDeviceInfo;
    }

    public DeviceInfo changeDeviceMode(String mode, DeviceInfo currentDeviceInfo) {
        long timestamp = System.currentTimeMillis();

        DeviceInfo updatedDeviceInfo = new DeviceInfo(currentDeviceInfo);
        updatedDeviceInfo.setLastUpdateTimestamp(timestamp);
        updatedDeviceInfo.setDeviceMode(mode);

        return updatedDeviceInfo;
    }

    public boolean hasFrontCamera() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                return true;
            }
        }
        return false;
    }

    public boolean hasBackCamera() {
        int backCameraId = -1;
        for(int i=0;i<Camera.getNumberOfCameras();i++){
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i,cameraInfo);
            if(cameraInfo.facing== Camera.CameraInfo.CAMERA_FACING_BACK) {
                backCameraId = i;
                break;
            }
        }

        return backCameraId >= 0;
    }
}
