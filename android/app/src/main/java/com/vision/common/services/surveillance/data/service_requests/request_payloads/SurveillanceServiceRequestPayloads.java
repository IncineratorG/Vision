package com.vision.common.services.surveillance.data.service_requests.request_payloads;


import com.vision.common.services.surveillance.data.service_requests.request_payloads.payloads.IsDeviceAliveRequestPayload;
import com.vision.common.services.surveillance.data.service_requests.request_payloads.payloads.TestRequestWithPayloadRequestPayload;

public class SurveillanceServiceRequestPayloads {
    public static TestRequestWithPayloadRequestPayload testRequestWithPayloadRequestPayload(String valueOne,
                                                                                                                                                                                    String valueTwo) {
        return new TestRequestWithPayloadRequestPayload(valueOne, valueTwo);
    }

    public static IsDeviceAliveRequestPayload isDeviceAliveRequestPayload() {
        return new IsDeviceAliveRequestPayload();
    }
}
