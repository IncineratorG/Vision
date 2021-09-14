package com.vision.modules.auth.module_actions.payloads.payloads;

import com.facebook.react.bridge.ReadableMap;
import com.vision.modules.modules_common.interfaces.js_payload.JSPayload;

public class LoginDeviceInGroupPayload implements JSPayload {
    private final String GROUP_NAME_FIELD = "groupName";
    private final String GROUP_PASSWORD_FIELD = "groupPassword";
    private final String DEVICE_NAME_FIELD = "deviceName";

    private String mGroupName;
    private String mGroupPassword;
    private String mDeviceName;

    public LoginDeviceInGroupPayload(ReadableMap readableMap) {
        if (readableMap == null) {
            return;
        }

        mGroupName = readableMap.getString(GROUP_NAME_FIELD);
        mGroupPassword = readableMap.getString(GROUP_PASSWORD_FIELD);
        mDeviceName = readableMap.getString(DEVICE_NAME_FIELD);
    }

    @Override
    public boolean isValid() {
        if (mGroupName == null || mGroupPassword == null || mDeviceName == null) {
            return false;
        }

        return !mGroupName.isEmpty() && !mGroupPassword.isEmpty() && !mDeviceName.isEmpty();
    }

    public String groupName() {
        return mGroupName;
    }

    public String groupPassword() {
        return mGroupPassword;
    }

    public String deviceName() {
        return mDeviceName;
    }
}
