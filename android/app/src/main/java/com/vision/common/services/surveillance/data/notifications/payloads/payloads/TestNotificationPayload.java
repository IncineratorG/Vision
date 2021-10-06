package com.vision.common.services.surveillance.data.notifications.payloads.payloads;

import com.vision.common.interfaces.service_notification_payload.ServiceNotificationPayload;

import org.json.JSONException;
import org.json.JSONObject;

public class TestNotificationPayload implements ServiceNotificationPayload {
    private final String TEST_VALUE_FIELD = "testValue";

    private JSONObject mJsonObject;

    public TestNotificationPayload(String testValue) {
        mJsonObject = new JSONObject();

        try {
            mJsonObject.put(TEST_VALUE_FIELD, testValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public TestNotificationPayload(JSONObject jsonObject) {
        try {
            mJsonObject = new JSONObject(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String testValue() {
        if (mJsonObject == null) {
            return null;
        }

        String testValue = null;
        try {
            testValue = (String) mJsonObject.get(TEST_VALUE_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return testValue;
    }

    @Override
    public JSONObject jsonObject() {
        return mJsonObject;
    }
}
