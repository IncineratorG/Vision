package com.vision.modules.surveillance_service.module_actions.payloads;

import com.facebook.react.bridge.ReadableMap;
import com.vision.modules.surveillance_service.module_actions.payloads.payloads.GetDevicesInGroupPayload;
import com.vision.modules.surveillance_service.module_actions.payloads.payloads.SendRequestPayload;

public class SurveillanceServiceJSActionsPayloads {
    public static GetDevicesInGroupPayload getDevicesInGroupPayload(ReadableMap payloadMap) {
        return new GetDevicesInGroupPayload(payloadMap);
    }

    public static SendRequestPayload sendRequestPayload(ReadableMap payloadMap) {
        return new SendRequestPayload(payloadMap);
    }
}
