package com.vision.services.surveillance.requests.payloads.payloads;

import com.vision.common.interfaces.service_request_payload.ServiceRequestPayload;

import org.json.JSONException;
import org.json.JSONObject;

public class GetCameraRecognizePersonSettingsRequestPayload implements ServiceRequestPayload {
    private final String CAMERA_TYPE_FIELD = "cameraType";

    private JSONObject mJsonObject;

    public GetCameraRecognizePersonSettingsRequestPayload(String cameraType) {
        mJsonObject = new JSONObject();

        try {
            mJsonObject.put(CAMERA_TYPE_FIELD, cameraType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public GetCameraRecognizePersonSettingsRequestPayload(JSONObject jsonObject) {
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

    @Override
    public JSONObject jsonObject() {
        return mJsonObject;
    }
}
