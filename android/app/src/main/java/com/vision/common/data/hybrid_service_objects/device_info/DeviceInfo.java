package com.vision.common.data.hybrid_service_objects.device_info;

import android.util.Log;

import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.vision.common.constants.AppConstants;
import com.vision.common.interfaces.service_object.ServiceObject;
import com.vision.modules.modules_common.interfaces.hybrid_object.HybridObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceInfo implements ServiceObject, HybridObject {
    public final String LAST_LOGIN_TIMESTAMP_KEY = "lastLoginTimestamp";
    public final String LAST_UPDATE_TIMESTAMP_KEY = "lastUpdateTimestamp";
    public final String DEVICE_NAME_KEY = "deviceName";
    public final String DEVICE_MODE_KEY = "deviceMode";
    public final String HAS_FRONT_CAMERA_KEY = "hasFrontCamera";
    public final String HAS_BACK_CAMERA_KEY = "hasBackCamera";

    private final String UNKNOWN_DEVICE_NAME = "Unknown";

    private long mLastLoginTimestamp = -1;
    private long mLastUpdateTimestamp = -1;
    private String mDeviceName = UNKNOWN_DEVICE_NAME;
    private String mDeviceMode = AppConstants.DEVICE_MODE_USER;
    private boolean mHasFrontCamera;
    private boolean mHasBackCamera;

    public DeviceInfo() {
        mLastLoginTimestamp = -1;
        mLastUpdateTimestamp = -1;
        mDeviceName = UNKNOWN_DEVICE_NAME;
        mDeviceMode = AppConstants.DEVICE_MODE_USER;
        mHasFrontCamera = false;
        mHasBackCamera = false;
    }

    public DeviceInfo(DeviceInfo other) {
        mLastLoginTimestamp = other.mLastLoginTimestamp;
        mLastUpdateTimestamp = other.mLastUpdateTimestamp;
        mDeviceName = other.mDeviceName;
        mDeviceMode = other.mDeviceMode;
        mHasFrontCamera = other.mHasFrontCamera;
        mHasBackCamera = other.mHasBackCamera;
    }

    public DeviceInfo(Object object) {
        if (object == null) {
            Log.d("tag", "DeviceInfo->OBJECT_IS_NULL");
            return;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;
        if (map != null) {
            if (map.containsKey(LAST_LOGIN_TIMESTAMP_KEY)) {
                String lastLoginTimestampString = (String) map.get(LAST_LOGIN_TIMESTAMP_KEY);
                if (lastLoginTimestampString != null) {
                    try {
                        mLastLoginTimestamp = Long.parseLong(lastLoginTimestampString);
                    } catch (NumberFormatException e) {
                        Log.d("tag", "DeviceInfo->ERROR_PARSING_LAST_LOGIN_TIMESTAMP");
                    }
                } else {
                    Log.d("tag", "DeviceInfo->LAST_LOGIN_TIMESTAMP_IS_NULL");
                }
            } else {
                Log.d("tag", "DeviceInfo->NO_LAST_LOGIN_TIMESTAMP_KEY");
            }

            if (map.containsKey(LAST_UPDATE_TIMESTAMP_KEY)) {
                String lastUpdateTimestampString = (String) map.get(LAST_UPDATE_TIMESTAMP_KEY);
                if (lastUpdateTimestampString != null) {
                    try {
                        mLastUpdateTimestamp = Long.parseLong(lastUpdateTimestampString);
                    } catch (NumberFormatException e) {
                        Log.d("tag", "DeviceInfo->ERROR_PARSING_LAST_UPDATE_TIMESTAMP");
                    }
                } else {
                    Log.d("tag", "DeviceInfo->LAST_UPDATE_TIMESTAMP_IS_NULL");
                }
            } else {
                Log.d("tag", "DeviceInfo->NO_LAST_UPDATE_TIMESTAMP_KEY");
            }

            if (map.containsKey(DEVICE_NAME_KEY)) {
                String deviceName = (String) map.get(DEVICE_NAME_KEY);
                if (deviceName != null) {
                    mDeviceName = deviceName;
                } else {
                    Log.d("tag", "DeviceInfo->DEVICE_NAME_IS_NULL");
                }
            } else {
                Log.d("tag", "DeviceInfo->NO_DEVICE_NAME_KEY");
            }

            if (map.containsKey(DEVICE_MODE_KEY)) {
                String deviceMode = (String) map.get(DEVICE_MODE_KEY);
                if (deviceMode != null) {
                    mDeviceMode = deviceMode;
                } else {
                    Log.d("tag", "DeviceInfo->DEVICE_MODE_IS_NULL");
                }
            } else {
                Log.d("tag", "DeviceInfo->NO_DEVICE_MODE");
            }

            if (map.containsKey(HAS_FRONT_CAMERA_KEY)) {
                Boolean hasFrontCamera = (Boolean) map.get(HAS_FRONT_CAMERA_KEY);
                if (hasFrontCamera != null) {
                    mHasFrontCamera = hasFrontCamera;
                } else {
                    Log.d("tag", "DeviceInfo->FRONT_CAMERA_VALUE_IS_NULL");
                }
            } else {
                Log.d("tag", "DeviceInfo->NO_FRONT_CAMERA_VALUE");
            }

            if (map.containsKey(HAS_BACK_CAMERA_KEY)) {
                Boolean hasBackCamera = (Boolean) map.get(HAS_BACK_CAMERA_KEY);
                if (hasBackCamera != null) {
                    mHasBackCamera = hasBackCamera;
                } else {
                    Log.d("tag", "DeviceInfo->BACK_CAMERA_VALUE_IS_NULL");
                }
            } else {
                Log.d("tag", "DeviceInfo->NO_BACK_CAMERA_VALUE");
            }
        } else {
            Log.d("tag", "DeviceInfo->BAD_OBJECT_MAP");
        }
    }

    public long lastLoginTimestamp() {
        return mLastLoginTimestamp;
    }

    public long lastUpdateTimestamp() {
        return mLastUpdateTimestamp;
    }

    public String deviceName() {
        return mDeviceName;
    }

    public String deviceMode() {
        return mDeviceMode;
    }

    public boolean hasFrontCamera() {
        return mHasFrontCamera;
    }

    public boolean hasBackCamera() {
        return mHasBackCamera;
    }

    public void setLastLoginTimestamp(long timestamp) {
        mLastLoginTimestamp = timestamp;
    }

    public void setLastUpdateTimestamp(long timestamp) {
        mLastUpdateTimestamp = timestamp;
    }

    public void setDeviceName(String deviceName) {
        mDeviceName = deviceName;
    }

    public void setDeviceMode(String deviceMode) {
        mDeviceMode = deviceMode;
    }

    public void setHasFrontCamera(boolean hasFrontCamera) {
        mHasFrontCamera = hasFrontCamera;
    }

    public void setHasBackCamera(boolean hasBackCamera) {
        mHasBackCamera = hasBackCamera;
    }

    @Override
    public boolean isEmpty() {
        return mLastLoginTimestamp == -1 && mDeviceName.equalsIgnoreCase(UNKNOWN_DEVICE_NAME);
    }

    @Override
    public WritableMap toWritableMap() {
        WritableMap writableMap = new WritableNativeMap();

        writableMap.putDouble(LAST_LOGIN_TIMESTAMP_KEY, mLastLoginTimestamp);
        writableMap.putDouble(LAST_UPDATE_TIMESTAMP_KEY, mLastUpdateTimestamp);
        writableMap.putString(DEVICE_NAME_KEY, mDeviceName);
        writableMap.putString(DEVICE_MODE_KEY, mDeviceMode);
        writableMap.putBoolean(HAS_FRONT_CAMERA_KEY, mHasFrontCamera);
        writableMap.putBoolean(HAS_BACK_CAMERA_KEY, mHasBackCamera);

        return writableMap;
    }

    @Override
    public WritableArray toWritableArray() {
        return null;
    }

    @Override
    public String stringify() {
        return null;
    }

    @Override
    public Map<String, Object> toServiceObject() {
        Map<String, Object> serviceObjectMap = new HashMap<>();
        serviceObjectMap.put(LAST_LOGIN_TIMESTAMP_KEY, String.valueOf(mLastLoginTimestamp));
        serviceObjectMap.put(LAST_UPDATE_TIMESTAMP_KEY, String.valueOf(mLastUpdateTimestamp));
        serviceObjectMap.put(DEVICE_NAME_KEY, mDeviceName);
        serviceObjectMap.put(DEVICE_MODE_KEY, mDeviceMode);
        serviceObjectMap.put(HAS_FRONT_CAMERA_KEY, mHasFrontCamera);
        serviceObjectMap.put(HAS_BACK_CAMERA_KEY, mHasBackCamera);

        return serviceObjectMap;
    }
}
