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

    public TestRequestWithPayloadResponsePayload(JSONObject jsonObject) {
        try {
            mJsonObject = new JSONObject(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String resultOne() {
        if (mJsonObject == null) {
            return null;
        }

        String resultOne = null;
        try {
            resultOne = mJsonObject.getString(RESULT_ONE_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultOne;
    }

    @Override
    public JSONObject jsonObject() {
        return mJsonObject;
    }
}
