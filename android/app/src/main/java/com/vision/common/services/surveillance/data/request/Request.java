package com.vision.common.services.surveillance.data.request;


import android.util.Log;

import com.vision.common.services.surveillance.data.stringifiable.Stringifiable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class Request implements Stringifiable {
    public static final String EMPTY = "empty";

    private final String ID_FIELD = "id";
    private final String TYPE_FIELD = "type";
    private final String TIMESTAMP_FIELD = "timestamp";
    private final String KEY_FIELD = "key";
    private final String PAYLOAD_FIELD = "payload";

    private JSONObject mRequest;

    public Request() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String id = UUID.randomUUID().toString() + timestamp;
        String type = EMPTY;

        mRequest = new JSONObject();
        try {
            mRequest.put(ID_FIELD, id);
            mRequest.put(TYPE_FIELD, type);
            mRequest.put(TIMESTAMP_FIELD, timestamp);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("tag", "Request->ERROR_CREATING_REQUEST");
        }
    }

    public Request(String type, JSONObject payload) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String id = UUID.randomUUID().toString() + timestamp;

        mRequest = new JSONObject();
        try {
            mRequest.put(ID_FIELD, id);
            mRequest.put(TYPE_FIELD, type);
            mRequest.put(TIMESTAMP_FIELD, timestamp);
            if (payload != null) {
                mRequest.put(PAYLOAD_FIELD, payload);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("tag", "Request->ERROR_CREATING_REQUEST");
        }
    }

    public Request(String stringifiedRequest) {
        try {
            mRequest = new JSONObject(stringifiedRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("tag", "Request->ERROR_CREATING_REQUEST_FROM_STRING");
        }
    }

    public boolean empty() {
        if (mRequest == null) {
            return true;
        }

        String id = null;
        String type = null;
        try {
            id = mRequest.getString(ID_FIELD);
            type = mRequest.getString(TYPE_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return id == null || type == null || type.equalsIgnoreCase(Request.EMPTY);
    }

    public String key() {
        if (mRequest == null) {
            Log.d("tag", "Request->key(): REQUEST_IS_NULL");
            return null;
        }

        String key = null;
        try {
            if (mRequest.has(KEY_FIELD)) {
                key = mRequest.getString(KEY_FIELD);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return key;
    }

    public String timestamp() {
        if (mRequest == null) {
            Log.d("tag", "Request->timestamp(): REQUEST_IS_NULL");
            return null;
        }

        String timestamp = null;
        try {
            timestamp = mRequest.getString(TIMESTAMP_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    public String id() {
        if (mRequest == null) {
            Log.d("tag", "Request->id(): REQUEST_IS_NULL");
            return null;
        }

        String id = null;
        try {
            id = mRequest.getString(ID_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String type() {
        if (mRequest == null) {
            Log.d("tag", "Request->type(): REQUEST_IS_NULL");
            return null;
        }

        String type = null;
        try {
            type = mRequest.getString(TYPE_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return type;
    }

    public JSONObject payload() {
        if (mRequest == null) {
            Log.d("tag", "Request->payload(): REQUEST_IS_NULL");
            return null;
        }

        JSONObject payload = null;
        try {
            if (mRequest.has(PAYLOAD_FIELD)) {
                payload = mRequest.getJSONObject(PAYLOAD_FIELD);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return payload;
    }

    public void setKey(String key) {
        if (mRequest == null) {
            Log.d("tag", "Request->setKey(): REQUEST_IS_NULL");
            return;
        }

        try {
            mRequest.put(KEY_FIELD, key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String stringify() {
        if (mRequest == null) {
            Log.d("tag", "Request->stringify(): REQUEST_IS_NULL");
            return null;
        }

        return mRequest.toString();
    }
}
