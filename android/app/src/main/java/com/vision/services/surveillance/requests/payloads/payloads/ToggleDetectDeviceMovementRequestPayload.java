package com.vision.services.surveillance.requests.payloads.payloads;


import com.vision.common.interfaces.service_request_payload.ServiceRequestPayload;

import org.json.JSONObject;

public class ToggleDetectDeviceMovementRequestPayload implements ServiceRequestPayload {
    private JSONObject mJsonObject;

    public ToggleDetectDeviceMovementRequestPayload() {

    }

    @Override
    public JSONObject jsonObject() {
        return mJsonObject;
    }
}
