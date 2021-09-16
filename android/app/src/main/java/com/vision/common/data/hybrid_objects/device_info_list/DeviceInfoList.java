package com.vision.common.data.hybrid_objects.device_info_list;


import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.vision.common.data.hybrid_service_objects.device_info.DeviceInfo;
import com.vision.modules.modules_common.interfaces.hybrid_object.HybridObject;

import java.util.ArrayList;
import java.util.List;

public class DeviceInfoList implements HybridObject {
    public static final String ARRAY_OBJECT_KEY = "notesArray";

    private List<DeviceInfo> mDeviceInfoList;

    public DeviceInfoList() {
        mDeviceInfoList = new ArrayList<>();
    }

    public void add(DeviceInfo deviceInfo) {
        mDeviceInfoList.add(deviceInfo);
    }

    public DeviceInfo get(int index) {
        if (index < 0 || index >= mDeviceInfoList.size()) {
            return null;
        }

        return mDeviceInfoList.get(index);
    }

    public int size() {
        return mDeviceInfoList.size();
    }

    public List<DeviceInfo> deviceInfoList() {
        return mDeviceInfoList;
    }

    @Override
    public WritableMap toWritableMap() {
        WritableMap writableMap = new WritableNativeMap();
        writableMap.putArray(ARRAY_OBJECT_KEY, toWritableArray());

        return writableMap;
    }

    @Override
    public WritableArray toWritableArray() {
        WritableArray writableArray = new WritableNativeArray();
        for (DeviceInfo deviceInfo : mDeviceInfoList) {
            writableArray.pushMap(deviceInfo.toWritableMap());
        }

        return writableArray;
    }

    @Override
    public String stringify() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
