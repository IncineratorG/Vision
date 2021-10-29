package com.vision.services.surveillance.data.requests.payloads.payloads;

import com.vision.common.interfaces.service_request_payload.ServiceRequestPayload;

import org.json.JSONObject;

public class IsDeviceAliveRequestPayload implements ServiceRequestPayload {
    private JSONObject mJsonObject;

    public IsDeviceAliveRequestPayload() {

    }

    @Override
    public JSONObject jsonObject() {
        return mJsonObject;
    }
}
