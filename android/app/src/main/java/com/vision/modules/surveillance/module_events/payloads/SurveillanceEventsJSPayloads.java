package com.vision.modules.surveillance.module_events.payloads;


import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.List;

public class SurveillanceEventsJSPayloads {
    public static WritableMap requestError(String requestId, String errorCode, String errorMessage) {
        WritableMap jsPayload = new WritableNativeMap();
        jsPayload.putString("requestId", requestId);
        jsPayload.putString("code", errorCode);
        jsPayload.putString("message", errorMessage);
        return jsPayload;
    }

    public static WritableMap testRequestResponseEventPayload(String requestId, String resultOne) {
        WritableMap jsPayload = new WritableNativeMap();
        jsPayload.putString("requestId", requestId);

        WritableMap payload = new WritableNativeMap();
        payload.putString("resultOne", resultOne);

        jsPayload.putMap("payload", payload);

        return jsPayload;
    }

    public static WritableMap getDeviceAvailableActionsResponseEventPayload(List<String> actions) {
        WritableMap jsPayload = new WritableNativeMap();

        WritableArray actionsArray = new WritableNativeArray();
        for (int i = 0; i < actions.size(); ++i) {
            actionsArray.pushString(actions.get(i));
        }
        jsPayload.putArray("actionsArray", actionsArray);

        return jsPayload;
    }
}
