package com.vision.modules.auth.module_actions.payloads;


import com.facebook.react.bridge.ReadableMap;
import com.vision.modules.auth.module_actions.payloads.payloads.RegisterDeviceInGroupPayload;

public class AuthJSActionsPayloads {
    public static RegisterDeviceInGroupPayload registerDeviceInGroupPayload(ReadableMap payloadMap) {
        return new RegisterDeviceInGroupPayload(payloadMap);
    }
}
