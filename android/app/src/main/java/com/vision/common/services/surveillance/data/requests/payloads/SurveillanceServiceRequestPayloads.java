package com.vision.common.services.surveillance.data.requests.payloads;


import com.vision.common.services.surveillance.data.requests.payloads.payloads.IsDeviceAliveRequestPayload;
import com.vision.common.services.surveillance.data.requests.payloads.payloads.TakeBackCameraImageRequestPayload;
import com.vision.common.services.surveillance.data.requests.payloads.payloads.TakeFrontCameraImageRequestPayload;
import com.vision.common.services.surveillance.data.requests.payloads.payloads.TestRequestWithPayloadRequestPayload;
import com.vision.common.services.surveillance.data.requests.payloads.payloads.ToggleDetectDeviceMovementRequestPayload;
import com.vision.common.services.surveillance.data.requests.payloads.payloads.ToggleRecognizePersonRequestPayload;

import org.json.JSONObject;

public class SurveillanceServiceRequestPayloads {
    public static TestRequestWithPayloadRequestPayload testRequestWithPayloadRequestPayload(String valueOne,
                                                                                            String valueTwo) {
        return new TestRequestWithPayloadRequestPayload(valueOne, valueTwo);
    }

    public static IsDeviceAliveRequestPayload isDeviceAliveRequestPayload() {
        return new IsDeviceAliveRequestPayload();
    }

    public static TakeBackCameraImageRequestPayload takeBackCameraImageRequestPayload(String imageQuality) {
        return new TakeBackCameraImageRequestPayload(imageQuality);
    }

    public static TakeBackCameraImageRequestPayload takeBackCameraImageRequestPayload(JSONObject jsonObject) {
        return new TakeBackCameraImageRequestPayload(jsonObject);
    }

    public static TakeFrontCameraImageRequestPayload takeFrontCameraImageRequestPayload(String imageQuality) {
        return new TakeFrontCameraImageRequestPayload(imageQuality);
    }

    public static TakeFrontCameraImageRequestPayload takeFrontCameraImageRequestPayload(JSONObject jsonObject) {
        return new TakeFrontCameraImageRequestPayload(jsonObject);
    }

    public static ToggleDetectDeviceMovementRequestPayload toggleDetectDeviceMovementRequestPayload() {
        return new ToggleDetectDeviceMovementRequestPayload();
    }

    public static ToggleRecognizePersonRequestPayload toggleRecognizePersonRequestPayload(String cameraType) {
        return new ToggleRecognizePersonRequestPayload(cameraType);
    }

    public static ToggleRecognizePersonRequestPayload toggleRecognizePersonRequestPayload(JSONObject jsonObject) {
        return new ToggleRecognizePersonRequestPayload(jsonObject);
    }
}
