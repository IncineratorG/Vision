package com.vision.services.surveillance.notifications.payloads.payloads;

import com.vision.common.interfaces.service_notification_payload.ServiceNotificationPayload;

import org.json.JSONException;
import org.json.JSONObject;

public class DetectedPersonsCountInCameraFrameChangedNotificationPayload implements ServiceNotificationPayload {
    private final String PERSONS_COUNT_FIELD = "personsCount";

    private JSONObject mJsonObject;

    public DetectedPersonsCountInCameraFrameChangedNotificationPayload(int personsCount) {
        mJsonObject = new JSONObject();

        try {
            mJsonObject.put(PERSONS_COUNT_FIELD, personsCount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public DetectedPersonsCountInCameraFrameChangedNotificationPayload(JSONObject jsonObject) {
        try {
            mJsonObject = new JSONObject(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int personsCount() {
        if (mJsonObject == null) {
            return -1;
        }

        int personsCount = 0;
        try {
            personsCount = (int) mJsonObject.get(PERSONS_COUNT_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return personsCount;
    }

    @Override
    public JSONObject jsonObject() {
        return mJsonObject;
    }
}
