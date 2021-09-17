package com.vision.common.services.surveillance.data.service_requests.response_payloads;


import com.vision.common.services.surveillance.data.service_requests.response_payloads.payloads.TestRequestWithPayloadResponsePayload;

public class SurveillanceServiceResponsePayloads {
    public static TestRequestWithPayloadResponsePayload testRequestWithPayloadResponsePayload(String resultOne) {
        return new TestRequestWithPayloadResponsePayload(resultOne);
    }
}
