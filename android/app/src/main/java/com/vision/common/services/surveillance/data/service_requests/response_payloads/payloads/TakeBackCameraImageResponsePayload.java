package com.vision.common.services.surveillance.data.service_requests.response_payloads.payloads;

import com.vision.common.data.service_response_payload.ServiceResponsePayload;

import org.json.JSONException;
import org.json.JSONObject;

public class TakeBackCameraImageResponsePayload implements ServiceResponsePayload {
    private final String IMAGE_FIELD = "image";

    private JSONObject mJsonObject;

    public TakeBackCameraImageResponsePayload(String base64Image) {
        mJsonObject = new JSONObject();

        try {
            mJsonObject.put(IMAGE_FIELD, base64Image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public TakeBackCameraImageResponsePayload(JSONObject jsonObject) {
        try {
            mJsonObject = new JSONObject(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String image() {
        if (mJsonObject == null) {
            return null;
        }

        String image = null;
        try {
            image = (String) mJsonObject.get(IMAGE_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public JSONObject jsonObject() {
        return mJsonObject;
    }
}
