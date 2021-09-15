package com.vision.common.data.hybrid_service_objects.device_info;

import android.util.Log;

import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.vision.common.interfaces.service_object.ServiceObject;
import com.vision.modules.modules_common.interfaces.hybrid_object.HybridObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceInfo implements ServiceObject, HybridObject {
    private final String LAST_LOGIN_TIMESTAMP_KEY = "lastLoginTimestamp";

    private long mLastLoginTimestamp;

    public DeviceInfo() {
        mLastLoginTimestamp = -1;
    }

    public DeviceInfo(DeviceInfo other) {
        mLastLoginTimestamp = other.mLastLoginTimestamp;
    }

    public DeviceInfo(Object object) {
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
        } else {
            Log.d("tag", "DeviceInfo->BAD_OBJECT_MAP");
        }
    }

    public long lastLoginTimestamp() {
        return mLastLoginTimestamp;
    }

    public void setLastLoginTimestamp(long timestamp) {
        mLastLoginTimestamp = timestamp;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public WritableMap toWritableMap() {
        return null;
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

        return serviceObjectMap;

//        Map<String, Object> map = new HashMap<>();
//        map.put("stringValue", mStringValue);
//
//        List<Map<String, Object>> listObjects = new ArrayList<>();
//        for (int i = 0; i < mListObject.size(); ++i) {
//            Map<String, Object> listItem = mListObject.get(i).toServiceObject();
//            listObjects.add(listItem);
//        }
//        map.put("listObjects", listObjects);
//
//        return map;
    }
}
