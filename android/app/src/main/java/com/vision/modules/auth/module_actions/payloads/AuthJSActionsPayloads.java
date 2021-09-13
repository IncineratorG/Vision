package com.vision.modules.auth.module_actions.payloads;


import com.facebook.react.bridge.ReadableMap;
import com.vision.modules.auth.module_actions.payloads.payloads.CreateGroupWithDevicePayload;
import com.vision.modules.auth.module_actions.payloads.payloads.RegisterDeviceInGroupPayload;

public class AuthJSActionsPayloads {
    public static RegisterDeviceInGroupPayload registerDeviceInGroupPayload(ReadableMap payloadMap) {
        return new RegisterDeviceInGroupPayload(payloadMap);
    }

    public static CreateGroupWithDevicePayload createGroupWithDevicePayload(ReadableMap payloadMap) {
        return new CreateGroupWithDevicePayload(payloadMap);
    }
}
