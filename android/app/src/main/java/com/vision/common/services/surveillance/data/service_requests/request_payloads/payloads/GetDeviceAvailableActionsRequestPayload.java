package com.vision.common.services.surveillance.data.service_requests.request_payloads.payloads;

import com.vision.common.data.service_request_payload.ServiceRequestPayload;

import org.json.JSONException;
import org.json.JSONObject;

public class GetDeviceAvailableActionsRequestPayload implements ServiceRequestPayload {
    private final String VALUE_ONE_FIELD = "valueOne";
    private final String VALUE_TWO_FIELD = "valueTwo";

    private JSONObject mJsonObject;

    public GetDeviceAvailableActionsRequestPayload() {
//        mJsonObject = new JSONObject();
    }

    public JSONObject jsonObject() {
        return mJsonObject;
    }
}
