package com.vision.modules.surveillance.module_actions.payloads.payloads;


import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.vision.common.services.surveillance.data.service_requests.request_payloads.SurveillanceServiceRequestPayloads;
import com.vision.common.services.surveillance.data.service_requests.request_payloads.payloads.IsDeviceAliveRequestPayload;
import com.vision.common.services.surveillance.data.service_requests.request_payloads.payloads.TakeBackCameraImageRequestPayload;
import com.vision.common.services.surveillance.data.service_requests.request_payloads.payloads.TestRequestWithPayloadRequestPayload;
import com.vision.common.services.surveillance.data.service_requests.types.SurveillanceServiceRequestTypes;
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

        switch (mRequestType) {
            case (SurveillanceServiceRequestTypes.TEST_REQUEST): {
                return null;
            }

            case (SurveillanceServiceRequestTypes.TEST_REQUEST_WITH_PAYLOAD): {
                if (requestPayloadMap == null) {
                    mRequestPayloadIsValid = false;
                    return null;
                }

                String valueOne = requestPayloadMap.getString("valueOne");
                String valueTwo = requestPayloadMap.getString("valueTwo");

                TestRequestWithPayloadRequestPayload requestPayload =
                        SurveillanceServiceRequestPayloads.testRequestWithPayloadRequestPayload(valueOne, valueTwo);

                return requestPayload.jsonObject();
            }

            case (SurveillanceServiceRequestTypes.IS_DEVICE_ALIVE): {
                IsDeviceAliveRequestPayload requestPayload =
                        SurveillanceServiceRequestPayloads.isDeviceAliveRequestPayload();

                return requestPayload.jsonObject();
            }

            case (SurveillanceServiceRequestTypes.TAKE_BACK_CAMERA_IMAGE): {
                TakeBackCameraImageRequestPayload requestPayload =
                        SurveillanceServiceRequestPayloads.takeBackCameraImageRequestPayload();

                return requestPayload.jsonObject();
            }

            default: {
                return null;
            }
        }
    }
}
