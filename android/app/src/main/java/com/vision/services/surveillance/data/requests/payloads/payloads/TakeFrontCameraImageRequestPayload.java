package com.vision.services.surveillance.data.requests.payloads.payloads;


import com.vision.common.interfaces.service_request_payload.ServiceRequestPayload;

import org.json.JSONException;
import org.json.JSONObject;

public class TakeFrontCameraImageRequestPayload implements ServiceRequestPayload {
    private final String IMAGE_QUALITY_FIELD = "imageQuality";

    private JSONObject mJsonObject;

    public TakeFrontCameraImageRequestPayload(String imageQuality) {
        mJsonObject = new JSONObject();

        try {
            mJsonObject.put(IMAGE_QUALITY_FIELD, imageQuality);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public TakeFrontCameraImageRequestPayload(JSONObject jsonObject) {
        try {
            mJsonObject = new JSONObject(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String imageQuality() {
        if (mJsonObject == null) {
            return null;
        }

        String imageQuality = null;
        try {
            imageQuality = (String) mJsonObject.get(IMAGE_QUALITY_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imageQuality;
    }

    @Override
    public JSONObject jsonObject() {
        return mJsonObject;
    }
}
