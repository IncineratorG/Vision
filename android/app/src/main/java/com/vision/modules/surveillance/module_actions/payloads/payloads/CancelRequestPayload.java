package com.vision.modules.surveillance.module_actions.payloads.payloads;


import com.facebook.react.bridge.ReadableMap;
import com.vision.modules.modules_common.interfaces.js_payload.JSPayload;

public class CancelRequestPayload implements JSPayload {
    private final String REQUEST_ID_FIELD = "requestId";

    private String mRequestId;

    public CancelRequestPayload(ReadableMap readableMap) {
        if (readableMap == null) {
            return;
        }

        mRequestId = readableMap.getString(REQUEST_ID_FIELD);
    }

    @Override
    public boolean isValid() {
        if (mRequestId == null) {
            return false;
        }

        return !mRequestId.isEmpty();
    }

    public String requestId() {
        return mRequestId;
    }
}
