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

    public static WritableMap isDeviceAliveResponseEventPayload(String requestId, boolean isAlive) {
        WritableMap jsPayload = new WritableNativeMap();
        jsPayload.putString("requestId", requestId);

        WritableMap payload = new WritableNativeMap();
        payload.putBoolean("isAlive", isAlive);

        jsPayload.putMap("payload", payload);

        return jsPayload;
    }
}
