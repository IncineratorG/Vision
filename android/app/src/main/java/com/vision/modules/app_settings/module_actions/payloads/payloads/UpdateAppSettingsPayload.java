package com.vision.modules.app_settings.module_actions.payloads.payloads;


import com.facebook.react.bridge.ReadableMap;
import com.vision.modules.modules_common.interfaces.js_payload.JSPayload;

public class UpdateAppSettingsPayload implements JSPayload {
//    private final String GROUP_NAME_FIELD = "groupName";
//    private final String GROUP_PASSWORD_FIELD = "groupPassword";
//    private final String DEVICE_NAME_FIELD = "deviceName";
//
//    private String mGroupName;
//    private String mGroupPassword;
//    private String mDeviceName;

    public UpdateAppSettingsPayload(ReadableMap readableMap) {
//        if (readableMap == null) {
//            return;
//        }

//        mGroupName = readableMap.getString(GROUP_NAME_FIELD);
//        mGroupPassword = readableMap.getString(GROUP_PASSWORD_FIELD);
//        mDeviceName = readableMap.getString(DEVICE_NAME_FIELD);
    }

    @Override
    public boolean isValid() {
        return true;

//        if (mGroupName == null || mGroupPassword == null || mDeviceName == null) {
//            return false;
//        }
//
//        return !mGroupName.isEmpty() && !mGroupPassword.isEmpty() && !mDeviceName.isEmpty();
    }
}
