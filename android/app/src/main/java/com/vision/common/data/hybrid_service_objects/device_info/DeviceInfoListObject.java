package com.vision.common.data.hybrid_service_objects.device_info;

import com.vision.common.interfaces.service_object.ServiceObject;

import java.util.HashMap;
import java.util.Map;

public class DeviceInfoListObject implements ServiceObject {
    private String mValue_1 = "DILO_val1";
    private String mValue_2 = "DILO_val2";

    public DeviceInfoListObject() {

    }

    @Override
    public Map<String, Object> toServiceObject() {
        Map<String, Object> map = new HashMap<>();
        map.put("innerObjectKey1", mValue_1);
        map.put("innerObjectKey2", mValue_2);

        return map;
    }
}
