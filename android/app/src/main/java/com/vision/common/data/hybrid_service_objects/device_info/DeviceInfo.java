package com.vision.common.data.hybrid_service_objects.device_info;

import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.vision.common.interfaces.service_object.ServiceObject;
import com.vision.modules.modules_common.interfaces.hybrid_object.HybridObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceInfo implements ServiceObject, HybridObject {
    private String mStringValue = "String Value";
    private List<DeviceInfoListObject> mListObject;

    public DeviceInfo() {
        mListObject = new ArrayList<>();
        mListObject.add(new DeviceInfoListObject());
        mListObject.add(new DeviceInfoListObject());
    }

    public String stringValue() {
        return mStringValue;
    }

    public List<DeviceInfoListObject> listValue() {
        return mListObject;
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
        Map<String, Object> map = new HashMap<>();
        map.put("stringValue", mStringValue);

        List<Map<String, Object>> listObjects = new ArrayList<>();
        for (int i = 0; i < mListObject.size(); ++i) {
            Map<String, Object> listItem = mListObject.get(i).toServiceObject();
            listObjects.add(listItem);
        }
        map.put("listObjects", listObjects);

        return map;
    }
}
