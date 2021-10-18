package com.vision.modules.surveillance.module_events.payloads;


import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

public class SurveillanceEventsJSPayloads {
    public static WritableMap requestDelivered(String requestId) {
        WritableMap jsPayload = new WritableNativeMap();
        jsPayload.putString("requestId", requestId);
        return jsPayload;
    }

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

    public static WritableMap takeBackCameraImageResponseEventPayload(String requestId, String image) {
        WritableMap jsPayload = new WritableNativeMap();
        jsPayload.putString("requestId", requestId);

        WritableMap payload = new WritableNativeMap();
        payload.putString("image", image);

        jsPayload.putMap("payload", payload);

        return jsPayload;
    }

    public static WritableMap takeFrontCameraImageResponseEventPayload(String requestId, String image) {
        WritableMap jsPayload = new WritableNativeMap();
        jsPayload.putString("requestId", requestId);

        WritableMap payload = new WritableNativeMap();
        payload.putString("image", image);

        jsPayload.putMap("payload", payload);

        return jsPayload;
    }

    public static WritableMap toggleDetectDeviceMovementResponseEventPayload(String requestId,
                                                                             boolean detectDeviceMovementServiceRunning) {
        WritableMap jsPayload = new WritableNativeMap();
        jsPayload.putString("requestId", requestId);

        WritableMap payload = new WritableNativeMap();
        payload.putBoolean("detectDeviceMovementServiceRunning", detectDeviceMovementServiceRunning);

        jsPayload.putMap("payload", payload);

        return jsPayload;
    }

    public static WritableMap toggleRecognizePersonResponseEventPayload(String requestId,
                                                                        boolean frontCameraRecognizeServiceRunning,
                                                                        boolean backCameraRecognizeServiceRunning) {
        WritableMap jsPayload = new WritableNativeMap();
        jsPayload.putString("requestId", requestId);

        WritableMap payload = new WritableNativeMap();
        payload.putBoolean("frontCameraRecognizePersonServiceRunning", frontCameraRecognizeServiceRunning);
        payload.putBoolean("backCameraRecognizePersonServiceRunning", backCameraRecognizeServiceRunning);

        jsPayload.putMap("payload", payload);

        return jsPayload;
    }
}
