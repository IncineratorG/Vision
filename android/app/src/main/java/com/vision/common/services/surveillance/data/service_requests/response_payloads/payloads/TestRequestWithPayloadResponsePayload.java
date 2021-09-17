package com.vision.common.services.surveillance.data.service_requests.response_payloads.payloads;


import com.vision.common.data.service_response_payload.ServiceResponsePayload;

import org.json.JSONException;
import org.json.JSONObject;

public class TestRequestWithPayloadResponsePayload implements ServiceResponsePayload {
    private final String RESULT_ONE_FIELD = "resultOne";

    private JSONObject mJsonObject;

    public TestRequestWithPayloadResponsePayload(String resultOne) {
        mJsonObject = new JSONObject();

        try {
            mJsonObject.put(RESULT_ONE_FIELD, resultOne);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject jsonObject() {
        return mJsonObject;
    }
}
