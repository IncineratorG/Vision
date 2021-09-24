package com.vision.common.services.surveillance.data.requests.payloads.payloads;

import com.vision.common.data.service_request_payload.ServiceRequestPayload;

import org.json.JSONObject;

public class IsDeviceAliveRequestPayload implements ServiceRequestPayload {
    private JSONObject mJsonObject;

    public IsDeviceAliveRequestPayload() {

    }

    public JSONObject jsonObject() {
        return mJsonObject;
    }
}
