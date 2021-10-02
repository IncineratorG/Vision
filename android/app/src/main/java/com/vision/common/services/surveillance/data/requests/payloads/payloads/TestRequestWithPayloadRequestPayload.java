package com.vision.common.services.surveillance.data.requests.payloads.payloads;


import com.vision.common.interfaces.service_request_payload.ServiceRequestPayload;

import org.json.JSONException;
import org.json.JSONObject;

public class TestRequestWithPayloadRequestPayload implements ServiceRequestPayload {
    private final String VALUE_ONE_FIELD = "valueOne";
    private final String VALUE_TWO_FIELD = "valueTwo";

    private JSONObject mJsonObject;

    public TestRequestWithPayloadRequestPayload(String valueOne, String valueTwo) {
        mJsonObject = new JSONObject();

        try {
            mJsonObject.put(VALUE_ONE_FIELD, valueOne);
            mJsonObject.put(VALUE_TWO_FIELD, valueTwo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject jsonObject() {
        return mJsonObject;
    }
}
