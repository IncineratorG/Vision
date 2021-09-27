package com.vision.modules.surveillance.module_actions.payloads;

import com.facebook.react.bridge.ReadableMap;
import com.vision.modules.surveillance.module_actions.payloads.payloads.CancelRequestPayload;
import com.vision.modules.surveillance.module_actions.payloads.payloads.GetDevicesInGroupPayload;
import com.vision.modules.surveillance.module_actions.payloads.payloads.send_request.SendRequestPayload;

public class SurveillanceJSActionsPayloads {
    public static GetDevicesInGroupPayload getDevicesInGroupPayload(ReadableMap payloadMap) {
        return new GetDevicesInGroupPayload(payloadMap);
    }

    public static SendRequestPayload sendRequestPayload(ReadableMap payloadMap) {
        return new SendRequestPayload(payloadMap);
    }

    public static CancelRequestPayload cancelRequestPayload(ReadableMap payloadMap) {
        return new CancelRequestPayload(payloadMap);
    }
}
