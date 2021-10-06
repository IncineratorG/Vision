package com.vision.modules.surveillance.module_actions.payloads.payloads.send_request;


import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.vision.common.interfaces.service_request_payload.ServiceRequestPayload;
import com.vision.modules.modules_common.interfaces.js_payload.JSPayload;

import org.json.JSONObject;

public class SendRequestPayload implements JSPayload {
    private final String REQUEST_TYPE_FIELD = "requestType";
    private final String RECEIVER_DEVICE_NAME_FIELD = "receiverDeviceName";
    private final String REQUEST_PAYLOAD_FIELD = "requestPayload";

    private String mRequestType;
    private String mReceiverDeviceName;
    private JSONObject mRequestPayload;
    private boolean mRequestPayloadIsValid = true;

    public SendRequestPayload(ReadableMap readableMap) {
        if (readableMap == null) {
            return;
        }

        mRequestType = readableMap.getString(REQUEST_TYPE_FIELD);
        if (mRequestType == null || mRequestType.isEmpty()) {
            Log.d("tag", "SendRequestPayload->BAD_REQUEST_TYPE");
            return;
        }

        mReceiverDeviceName = readableMap.getString(RECEIVER_DEVICE_NAME_FIELD);
        if (mReceiverDeviceName == null || mReceiverDeviceName.isEmpty()) {
            Log.d("tag", "SendRequestPayload->BAD_RECEIVER_DEVICE_NAME");
            return;
        }

        mRequestPayload = getPayloadFromMap(readableMap);
    }

    @Override
    public boolean isValid() {
        return mRequestPayloadIsValid &&
                mRequestType != null &&
                !mRequestType.isEmpty() &&
                mReceiverDeviceName != null &&
                !mReceiverDeviceName.isEmpty();
    }

    public String receiverDeviceName() {
        return mReceiverDeviceName;
    }

    public String requestType() {
        return mRequestType;
    }

    public JSONObject requestPayload() {
        return mRequestPayload;
    }

    private JSONObject getPayloadFromMap(ReadableMap payloadMap) {
        ReadableMap requestPayloadMap = payloadMap.getMap(REQUEST_PAYLOAD_FIELD);

        ServiceRequestPayload serviceRequestPayload = PayloadsConverter.toServiceRequestPayload(mRequestType, requestPayloadMap);
        if (serviceRequestPayload != null) {
            return serviceRequestPayload.jsonObject();
        } else {
            Log.d("tag", "SendRequestPayload->getPayloadFromMap(): BAD_SERVICE_REQUEST_PAYLOAD");

            mRequestPayloadIsValid = false;
            return null;
        }
    }
}
