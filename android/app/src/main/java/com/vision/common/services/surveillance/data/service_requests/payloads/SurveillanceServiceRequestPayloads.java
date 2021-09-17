package com.vision.common.services.surveillance.data.service_requests.payloads;


import com.vision.common.services.surveillance.data.service_requests.payloads.payloads.TestRequestWithPayloadRequestPayload;

public class SurveillanceServiceRequestPayloads {
    public static TestRequestWithPayloadRequestPayload testRequestWithPayloadRequestPayload(String valueOne,
                                                                                            String valueTwo) {
        return new TestRequestWithPayloadRequestPayload(valueOne, valueTwo);
    }
}
