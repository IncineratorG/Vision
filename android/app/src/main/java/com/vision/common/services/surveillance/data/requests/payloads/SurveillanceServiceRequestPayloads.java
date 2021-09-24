package com.vision.common.services.surveillance.data.requests.payloads;


import com.vision.common.services.surveillance.data.requests.payloads.payloads.IsDeviceAliveRequestPayload;
import com.vision.common.services.surveillance.data.requests.payloads.payloads.TakeBackCameraImageRequestPayload;
import com.vision.common.services.surveillance.data.requests.payloads.payloads.TestRequestWithPayloadRequestPayload;

public class SurveillanceServiceRequestPayloads {
    public static TestRequestWithPayloadRequestPayload testRequestWithPayloadRequestPayload(String valueOne,
                                                                                                                                                                                    String valueTwo) {
        return new TestRequestWithPayloadRequestPayload(valueOne, valueTwo);
    }

    public static IsDeviceAliveRequestPayload isDeviceAliveRequestPayload() {
        return new IsDeviceAliveRequestPayload();
    }

    public static TakeBackCameraImageRequestPayload takeBackCameraImageRequestPayload() {
        return new TakeBackCameraImageRequestPayload();
    }
}
