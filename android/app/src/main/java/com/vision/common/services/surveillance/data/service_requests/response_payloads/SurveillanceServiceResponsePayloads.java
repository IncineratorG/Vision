package com.vision.common.services.surveillance.data.service_requests.response_payloads;


import com.vision.common.services.surveillance.data.service_requests.response_payloads.payloads.IsDeviceAliveResponsePayload;
import com.vision.common.services.surveillance.data.service_requests.response_payloads.payloads.TakeBackCameraImageResponsePayload;
import com.vision.common.services.surveillance.data.service_requests.response_payloads.payloads.TestRequestWithPayloadResponsePayload;

import org.json.JSONObject;

public class SurveillanceServiceResponsePayloads {
    public static TestRequestWithPayloadResponsePayload testRequestWithPayloadResponsePayload(String resultOne) {
        return new TestRequestWithPayloadResponsePayload(resultOne);
    }

    public static TestRequestWithPayloadResponsePayload testRequestWithPayloadResponsePayload(JSONObject jsonObject) {
        return new TestRequestWithPayloadResponsePayload(jsonObject);
    }

    public static IsDeviceAliveResponsePayload isDeviceAliveResponsePayload(boolean isAlive) {
        return new IsDeviceAliveResponsePayload(isAlive);
    }

    public static IsDeviceAliveResponsePayload isDeviceAliveResponsePayload(JSONObject jsonObject) {
        return new IsDeviceAliveResponsePayload(jsonObject);
    }

    public static TakeBackCameraImageResponsePayload takeBackCameraImageResponsePayload(String base64Image) {
        return new TakeBackCameraImageResponsePayload(base64Image);
    }

    public static TakeBackCameraImageResponsePayload takeBackCameraImageResponsePayload(JSONObject jsonObject) {
        return new TakeBackCameraImageResponsePayload(jsonObject);
    }
}
