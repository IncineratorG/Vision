package com.vision.common.services.surveillance.data.responses.payloads.payloads;

import com.vision.common.interfaces.service_response_payload.ServiceResponsePayload;

import org.json.JSONException;
import org.json.JSONObject;

public class StopRecognizePersonResponsePayload implements ServiceResponsePayload {
    private final String FRONT_CAMERA_RECOGNIZE_PERSON_SERVICE_RUNNING_FIELD = "frontCameraRecognizePersonServiceRunning";
    private final String BACK_CAMERA_RECOGNIZE_PERSON_SERVICE_RUNNING_FIELD = "backCameraRecognizePersonServiceRunning";

    private JSONObject mJsonObject;

    public StopRecognizePersonResponsePayload(boolean frontCameraRecognizeServiceRunning,
                                              boolean backCameraRecognizeServiceRunning) {
        mJsonObject = new JSONObject();

        try {
            mJsonObject.put(FRONT_CAMERA_RECOGNIZE_PERSON_SERVICE_RUNNING_FIELD, frontCameraRecognizeServiceRunning);
            mJsonObject.put(BACK_CAMERA_RECOGNIZE_PERSON_SERVICE_RUNNING_FIELD, backCameraRecognizeServiceRunning);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public StopRecognizePersonResponsePayload(JSONObject jsonObject) {
        try {
            mJsonObject = new JSONObject(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean frontCameraRecognizePersonServiceRunning() {
        if (mJsonObject == null) {
            return false;
        }

        boolean frontCameraRecognizePersonServiceRunning = false;
        try {
            frontCameraRecognizePersonServiceRunning =
                    (boolean) mJsonObject.get(FRONT_CAMERA_RECOGNIZE_PERSON_SERVICE_RUNNING_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return frontCameraRecognizePersonServiceRunning;
    }

    public boolean backCameraRecognizePersonServiceRunning() {
        if (mJsonObject == null) {
            return false;
        }

        boolean backCameraRecognizePersonServiceRunning = false;
        try {
            backCameraRecognizePersonServiceRunning =
                    (boolean) mJsonObject.get(BACK_CAMERA_RECOGNIZE_PERSON_SERVICE_RUNNING_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return backCameraRecognizePersonServiceRunning;
    }

    @Override
    public JSONObject jsonObject() {
        return mJsonObject;
    }
}

