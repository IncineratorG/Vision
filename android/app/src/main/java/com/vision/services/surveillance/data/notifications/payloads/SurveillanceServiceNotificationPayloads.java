package com.vision.services.surveillance.data.notifications.payloads;

import com.vision.services.surveillance.data.notifications.payloads.payloads.TestNotificationPayload;

import org.json.JSONObject;

public class SurveillanceServiceNotificationPayloads {
    public static TestNotificationPayload testNotificationPayload(String testValue) {
        return new TestNotificationPayload(testValue);
    }

    public static TestNotificationPayload testNotificationPayload(JSONObject jsonObject) {
        return new TestNotificationPayload(jsonObject);
    }
}
