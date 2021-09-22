package com.vision.common.services.surveillance.data.service_requests.request_payloads.payloads;

import com.vision.common.data.service_request_payload.ServiceRequestPayload;

import org.json.JSONObject;

public class TakeBackCameraImageRequestPayload implements ServiceRequestPayload {
    private JSONObject mJsonObject;

    public TakeBackCameraImageRequestPayload() {

    }

    public JSONObject jsonObject() {
        return mJsonObject;
    }
}