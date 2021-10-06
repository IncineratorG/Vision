package com.vision.common.services.surveillance.data.responses.payloads.payloads;


import com.vision.common.interfaces.service_response_payload.ServiceResponsePayload;

import org.json.JSONException;
import org.json.JSONObject;

public class ToggleDetectDeviceMovementResponsePayload implements ServiceResponsePayload {
    private final String DETECT_DEVICE_MOVEMENT_SERVICE_RUNNING_FIELD = "detectDeviceMovementServiceRunning";

    private JSONObject mJsonObject;

    public ToggleDetectDeviceMovementResponsePayload(boolean detectDeviceMovementServiceRunning) {
        mJsonObject = new JSONObject();

        try {
            mJsonObject.put(DETECT_DEVICE_MOVEMENT_SERVICE_RUNNING_FIELD, detectDeviceMovementServiceRunning);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ToggleDetectDeviceMovementResponsePayload(JSONObject jsonObject) {
        try {
            mJsonObject = new JSONObject(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean detectDeviceMovementServiceRunning() {
        if (mJsonObject == null) {
            return false;
        }

        boolean detectDeviceMovementServiceRunning = false;
        try {
            detectDeviceMovementServiceRunning = (boolean) mJsonObject.get(DETECT_DEVICE_MOVEMENT_SERVICE_RUNNING_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return detectDeviceMovementServiceRunning;
    }

    @Override
    public JSONObject jsonObject() {
        return mJsonObject;
    }
}
