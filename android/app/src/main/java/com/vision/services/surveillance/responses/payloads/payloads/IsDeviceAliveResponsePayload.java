package com.vision.services.surveillance.responses.payloads.payloads;

import com.vision.common.interfaces.service_response_payload.ServiceResponsePayload;

import org.json.JSONException;
import org.json.JSONObject;

public class IsDeviceAliveResponsePayload implements ServiceResponsePayload {
    private final String IS_ALIVE_FIELD = "isAlive";

    private JSONObject mJsonObject;

    public IsDeviceAliveResponsePayload(boolean isAlive) {
        mJsonObject = new JSONObject();

        try {
            mJsonObject.put(IS_ALIVE_FIELD, isAlive);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public IsDeviceAliveResponsePayload(JSONObject jsonObject) {
        try {
            mJsonObject = new JSONObject(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isAlive() {
        if (mJsonObject == null) {
            return false;
        }

        boolean isAlive = false;
        try {
            isAlive = (boolean) mJsonObject.get(IS_ALIVE_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isAlive;
    }

    @Override
    public JSONObject jsonObject() {
        return mJsonObject;
    }
}
