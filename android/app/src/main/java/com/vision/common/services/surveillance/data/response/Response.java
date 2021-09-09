package com.vision.common.services.surveillance.data.response;


import android.util.Log;

import com.vision.common.services.surveillance.data.stringifiable.Stringifiable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class Response implements Stringifiable {
    private final String ID_FIELD = "id";
    private final String TIMESTAMP_FIELD = "timestamp";
    private final String REQUEST_ID_FIELD = "requestId";
    private final String PAYLOAD_FIELD = "payload";

    private JSONObject mResponse;

    public Response() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String id = UUID.randomUUID().toString() + timestamp;

        mResponse = new JSONObject();
        try {
            mResponse.put(ID_FIELD, id);
            mResponse.put(TIMESTAMP_FIELD, timestamp);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("tag", "Response->ERROR_CREATING_RESPONSE");
        }
    }

    public Response(String requestId, JSONObject payload) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String id = UUID.randomUUID().toString() + timestamp;

        mResponse = new JSONObject();
        try {
            mResponse.put(ID_FIELD, id);
            mResponse.put(TIMESTAMP_FIELD, timestamp);
            mResponse.put(REQUEST_ID_FIELD, requestId);
            if (payload != null) {
                mResponse.put(PAYLOAD_FIELD, payload);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("tag", "Response->ERROR_CREATING_RESPONSE");
        }
    }

    public Response(String stringifiedResponse) {
        try {
            mResponse = new JSONObject(stringifiedResponse);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("tag", "Response->ERROR_CREATING_RESPONSE_FROM_STRING");
        }
    }

    public boolean empty() {
        if (mResponse == null) {
            return true;
        }

        String requestId = null;
        try {
            requestId = mResponse.getString(REQUEST_ID_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestId == null;
    }

    public String timestamp() {
        if (mResponse == null) {
            Log.d("tag", "Response->timestamp(): RESPONSE_IS_NULL");
            return null;
        }

        String timestamp = null;
        try {
            timestamp = mResponse.getString(TIMESTAMP_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    public String id() {
        if (mResponse == null) {
            Log.d("tag", "Response->id(): RESPONSE_IS_NULL");
            return null;
        }

        String id = null;
        try {
            id = mResponse.getString(ID_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String requestId() {
        if (mResponse == null) {
            Log.d("tag", "Response->requestId(): RESPONSE_IS_NULL");
            return null;
        }

        String requestId = null;
        try {
            requestId = mResponse.getString(REQUEST_ID_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestId;
    }

    public JSONObject payload() {
        if (mResponse == null) {
            Log.d("tag", "Response->payload(): RESPONSE_IS_NULL");
            return null;
        }

        JSONObject payload = null;
        try {
            if (mResponse.has(PAYLOAD_FIELD)) {
                payload = mResponse.getJSONObject(PAYLOAD_FIELD);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return payload;
    }

    @Override
    public String stringify() {
        if (mResponse == null) {
            Log.d("tag", "Response->stringify(): RESPONSE_IS_NULL");
            return null;
        }

        return mResponse.toString();
    }
}
