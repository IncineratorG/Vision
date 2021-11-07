package com.vision.services.surveillance.data.requests.payloads.payloads;


import com.vision.common.interfaces.service_request_payload.ServiceRequestPayload;

import org.json.JSONException;
import org.json.JSONObject;

public class ToggleRecognizePersonRequestPayload implements ServiceRequestPayload {
    private final String CAMERA_TYPE_FIELD = "cameraType";
    private final String IMAGE_ROTATION_DEG_FIELD = "imageRotationDeg";

    private JSONObject mJsonObject;

    public ToggleRecognizePersonRequestPayload(String cameraType, int imageRotationDeg) {
        mJsonObject = new JSONObject();

        try {
            mJsonObject.put(CAMERA_TYPE_FIELD, cameraType);
            mJsonObject.put(IMAGE_ROTATION_DEG_FIELD, imageRotationDeg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ToggleRecognizePersonRequestPayload(JSONObject jsonObject) {
        try {
            mJsonObject = new JSONObject(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String cameraType() {
        if (mJsonObject == null) {
            return null;
        }

        String cameraType = null;
        try {
            cameraType = (String) mJsonObject.get(CAMERA_TYPE_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cameraType;
    }

    public int imageRotationDeg() {
        if (mJsonObject == null) {
            return -1;
        }

        int imageRotationDeg = -1;
        try {
            imageRotationDeg = (int) mJsonObject.get(IMAGE_ROTATION_DEG_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imageRotationDeg;
    }

    @Override
    public JSONObject jsonObject() {
        return mJsonObject;
    }
}
