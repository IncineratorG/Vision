package com.vision.common.data.service_notification;

import android.util.Log;

import com.vision.common.interfaces.stringifiable.Stringifiable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class ServiceNotification implements Stringifiable {
    public static final String EMPTY = "empty";

    private final String ID_FIELD = "id";
    private final String TIMESTAMP_FIELD = "timestamp";
    private final String TYPE_FIELD = "type";
    private final String SENDER_GROUP_NAME = "senderGroupName";
    private final String SENDER_DEVICE_NAME = "senderDeviceName";
    private final String PAYLOAD_FIELD = "payload";

    private JSONObject mNotification;

    public ServiceNotification() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String id = UUID.randomUUID().toString() + timestamp;
        String type = EMPTY;

        mNotification = new JSONObject();
        try {
            mNotification.put(ID_FIELD, id);
            mNotification.put(TIMESTAMP_FIELD, timestamp);
            mNotification.put(TYPE_FIELD, type);
            mNotification.put(SENDER_GROUP_NAME, "");
            mNotification.put(SENDER_DEVICE_NAME, "");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("tag", "ServiceNotification->ERROR_CREATING_NOTIFICATION");
        }
    }

    public ServiceNotification(String type,
                               String senderGroupName,
                               String senderDeviceName,
                               JSONObject payload) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String id = UUID.randomUUID().toString() + timestamp;

        mNotification = new JSONObject();
        try {
            mNotification.put(ID_FIELD, id);
            mNotification.put(TIMESTAMP_FIELD, timestamp);
            mNotification.put(TYPE_FIELD, type);
            mNotification.put(SENDER_GROUP_NAME, senderGroupName);
            mNotification.put(SENDER_DEVICE_NAME, senderDeviceName);
            if (payload != null) {
                mNotification.put(PAYLOAD_FIELD, payload);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("tag", "ServiceNotification->ERROR_CREATING_NOTIFICATION");
        }
    }

    public ServiceNotification(String stringifiedNotification) {
        try {
            mNotification = new JSONObject(stringifiedNotification);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("tag", "ServiceNotification->ERROR_CREATING_NOTIFICATION");
        }
    }

    public boolean empty() {
        if (mNotification == null) {
            return true;
        }

        String id = null;
        String type = null;
        try {
            id = mNotification.getString(ID_FIELD);
            type = mNotification.getString(TYPE_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return id == null || type == null || type.equalsIgnoreCase(EMPTY);
    }

    public String id() {
        if (mNotification == null) {
            Log.d("tag", "ServiceNotification->id(): NOTIFICATION_IS_NULL");
            return null;
        }

        String id = null;
        try {
            id = mNotification.getString(ID_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String timestamp() {
        if (mNotification == null) {
            Log.d("tag", "ServiceNotification->timestamp(): NOTIFICATION_IS_NULL");
            return null;
        }

        String timestamp = null;
        try {
            timestamp = mNotification.getString(TIMESTAMP_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    public String type() {
        if (mNotification == null) {
            Log.d("tag", "ServiceNotification->type(): NOTIFICATION_IS_NULL");
            return null;
        }

        String type = null;
        try {
            type = mNotification.getString(TYPE_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return type;
    }

    public String senderGroupName() {
        if (mNotification == null) {
            Log.d("tag", "ServiceNotification->senderGroupName(): NOTIFICATION_IS_NULL");
            return null;
        }

        String senderGroupName = null;
        try {
            senderGroupName = mNotification.getString(SENDER_GROUP_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return senderGroupName;
    }

    public String senderDeviceName() {
        if (mNotification == null) {
            Log.d("tag", "ServiceNotification->senderDeviceName(): NOTIFICATION_IS_NULL");
            return null;
        }

        String senderDeviceName = null;
        try {
            senderDeviceName = mNotification.getString(SENDER_DEVICE_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return senderDeviceName;
    }

    public JSONObject payload() {
        if (mNotification == null) {
            Log.d("tag", "ServiceNotification->payload(): NOTIFICATION_IS_NULL");
            return null;
        }

        JSONObject payload = null;
        try {
            if (mNotification.has(PAYLOAD_FIELD)) {
                payload = mNotification.getJSONObject(PAYLOAD_FIELD);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return payload;
    }

    @Override
    public String stringify() {
        if (mNotification == null) {
            Log.d("tag", "ServiceNotification->stringify(): NOTIFICATION_IS_NULL");
            return null;
        }

        return mNotification.toString();
    }
}
